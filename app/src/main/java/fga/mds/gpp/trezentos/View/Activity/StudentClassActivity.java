package fga.mds.gpp.trezentos.View.Activity;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import fga.mds.gpp.trezentos.Controller.UserAccountControl;
import fga.mds.gpp.trezentos.Controller.UserClassControl;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Fragment.ExamsFragment;
import fga.mds.gpp.trezentos.View.Fragment.InfoClassFragment;
import fga.mds.gpp.trezentos.View.Adapters.ViewPagerAdapter;


public class StudentClassActivity extends AppCompatActivity {

    private UserClassControl userClassControl;
    private UserClass userClass;
    private ViewPager viewPager;
    private Toolbar toolbar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_student_class);

        initToolbar();
        initViewPager();

        initRecover();

        userClassControl = UserClassControl
                .getInstance(getApplicationContext());

        if (userClass != null) {
            setTitle(userClass.getClassName());
        }
    }

    public void initToolbar() {
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void initViewPager() {
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        setupViewPager(viewPager);
    }


    public void initRecover() {
        Intent intent = getIntent();
        userClass = (UserClass) intent.getSerializableExtra("Class");
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(new ExamsFragment(), "EXAMS");
        adapter.addFragment(new InfoClassFragment(), "STUDENTS");
        viewPager.setAdapter(adapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.menu_initial_student, menu);
        return true;
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();

         if(id == R.id.actionOutFromClass){
            AlertDialog diaBox = AskOptionToDelete();
            diaBox.show();
        }
        return super.onOptionsItemSelected(item);
    }

    private AlertDialog AskOptionToDelete() {
        AlertDialog myQuittingDialogBox =new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Aluno sair de sala")
                .setMessage("Você tem certeza que deseja sair dessa sala?")

                .setPositiveButton("Sim", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        UserAccountControl userAccountControl =
                                UserAccountControl.getInstance(getApplicationContext());

                        if(userAccountControl.isNetworkAvailable()) {
                            try {
                                SharedPreferences session = PreferenceManager
                                        .getDefaultSharedPreferences(getApplicationContext());
                                String serverResponse = userClassControl.deleteStudentFromClass(userClass.getIdClass(), session.getString("userId",""));
                                validateServerResponse(serverResponse);
                            } catch (UserException e) {
                                e.printStackTrace();
                            }
                            dialog.dismiss();
                        } else {
                            Toast.makeText(
                                    getApplicationContext(),
                                    "Verifique a conexão com a internet e tente novamente!",
                                    Toast.LENGTH_LONG
                            ).show();
                        }

                    }

                })

                .setNegativeButton("Não", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {

                        dialog.dismiss();

                    }
                })
                .create();
        return myQuittingDialogBox;
    }

    private void validateServerResponse(String response){
        try {
            //converting response to json object
            JSONObject obj = new JSONObject(response);
            //if no error in response
            if (!obj.getBoolean("error")) {
                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();

                finish();

            } else {
                Toast.makeText(getApplicationContext(), obj.getString("message"), Toast.LENGTH_SHORT).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

}
