package fga.mds.gpp.trezentos.View.Activity;

import android.app.AlertDialog;
import android.app.FragmentTransaction;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.design.widget.TabLayout;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fga.mds.gpp.trezentos.Controller.GroupController;
import fga.mds.gpp.trezentos.Controller.UserAccountControl;
import fga.mds.gpp.trezentos.Controller.UserExamControl;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Group;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Fragment.GroupsFragment;
import fga.mds.gpp.trezentos.View.Fragment.StudentsFragment;
import fga.mds.gpp.trezentos.View.Adapters.ViewPagerAdapter;

public class ExamActivity extends AppCompatActivity {

    private UserClass userClass;
    private Toolbar toolbar;
    private Exam exam;
    private StudentsFragment studentsFragment;
    private GroupsFragment groupsFragment;
    private ArrayList<Student> students;
    private GroupController groupController;
    private ArrayList<Group> examGroups;
    private ViewPager activity;

    @Override
    protected void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam);
        initToolbar();
        initViewPager();
        initRecover();

        if(exam != null){
            setTitle(exam.getNameExam());
        }
    }

    public void initToolbar(){
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
    }

    public void initViewPager(){
        studentsFragment = new StudentsFragment();
        groupsFragment = new GroupsFragment();

        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());

        adapter.addFragment(studentsFragment, "ESTUDANTES");
        adapter.addFragment(groupsFragment, "GRUPOS");

        activity = findViewById(R.id.view_pager);
        activity.setAdapter(adapter);

        TabLayout tabLayout =  findViewById(R.id.tab_layout);
        tabLayout.setupWithViewPager(activity);

    }


    public void initRecover(){
        Intent intent = getIntent();
        Bundle extras = intent.getExtras();
        exam = (Exam) extras.getSerializable("Exam");
        userClass = (UserClass) extras.getSerializable("Class");
    }


    @Override
    public boolean onSupportNavigateUp(){
        onBackPressed();
        return true;
    }
    // Inflate the menu; this adds items to the action bar if it is present.
    @Override
    public boolean onCreateOptionsMenu(Menu menu){
        getMenuInflater().inflate(R.menu.menu_exam, menu);
        return true;
    }

    public boolean onOptionsItemSelected(MenuItem item) {

        String serverResponse = null;

        switch (item.getItemId()){
            case R.id.action_update_grades:{
                serverResponse = saveGrades(1);
                break;
            }
            case R.id.action_update_trezentos_grades: {
                serverResponse = saveGrades(2);
                break;
            }
            case R.id.action_sort_groups:{
                students = studentsFragment.getStudents();
                if(students != null){
                    confirmGroupsSortingDialog();
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Não há estudantes cadastrados", Toast.LENGTH_LONG).show();
                }

                break;
            }
            case R.id.action_save_groups: {
                if(examGroups != null){
                    groupController.sendGroups(examGroups);
                } else {
                    Toast.makeText(getApplicationContext(),
                            "Não há grupos ordenados para enviar", Toast.LENGTH_LONG).show();
                }

                break;
            }

        }

        if (serverResponse  != null){
            validateServerResponse(serverResponse);
        }


        return super.onOptionsItemSelected(item);

    }

    private String saveGrades(int gradeType){

        String serverResponse = null;
        UserExamControl userExamControl = UserExamControl.getInstance(getApplicationContext());
        students = studentsFragment.getStudents();

        if(students != null){
            HashMap<String, String> grades = userExamControl.getGradesFromStudents(students, gradeType);
            serverResponse = userExamControl.createGrades(gradeType, grades, exam, userClass);
        } else {
            Toast.makeText(getApplicationContext(), "Não há estudantes cadastrados", Toast.LENGTH_LONG).show();
        }

        return serverResponse;
    }

    private void validateServerResponse(String serverResponse){

        JSONObject object;
        boolean error = false;
        String message = "";

        try {
            object = new JSONObject(serverResponse);
            error = object.getBoolean("error");
            message = object.getString("message");
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if(error) {
            Toast.makeText(getApplicationContext(),message,
                    Toast.LENGTH_SHORT).show();
        }else {
            Toast.makeText(getApplicationContext(), "Notas salvas",
                    Toast.LENGTH_SHORT).show();
        }

    }

    private void confirmGroupsSortingDialog(){
        AlertDialog myQuittingDialogBox = new AlertDialog.Builder(this)
                //set message, title, and icon
                .setTitle("Montar grupos")
                .setMessage("Os grupos só estarão visíveis para você. " +
                        "Para salvá-los, ordene e em seguida " +
                        " selecione salvar grupos.")

                .setPositiveButton("Ordenar", new DialogInterface.OnClickListener() {

                    public void onClick(DialogInterface dialog, int whichButton) {
                        groupController = new GroupController(userClass, students);
                        examGroups = groupController.sortGroups();
                        groupsFragment.setGroups(examGroups);
                        activity.setCurrentItem(1);
                    }

                })

                .setNegativeButton("Cancelar", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                })
                .create();

        myQuittingDialogBox.show();
    }


}
