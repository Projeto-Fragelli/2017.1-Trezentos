package fga.mds.gpp.trezentos.View.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import fga.mds.gpp.trezentos.Controller.UserExamControl;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Fragment.AreYouSureFragment;
import fga.mds.gpp.trezentos.View.Fragment.GroupsFragment;
import fga.mds.gpp.trezentos.View.Fragment.InfoClassFragment;
import fga.mds.gpp.trezentos.View.Fragment.StudentsFragment;
import fga.mds.gpp.trezentos.View.Adapters.ViewPagerAdapter;

import static com.facebook.FacebookSdk.getApplicationContext;

public class ExamActivity extends AppCompatActivity {

    private UserClass userClass;
    private Toolbar toolbar;
    private Exam exam;
    private StudentsFragment studentsFragment;
    private GroupsFragment groupsFragment;
    private ArrayList<Student> students;

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

        ViewPager activity = findViewById(R.id.view_pager);
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
//            case R.id.action_sort_groups:{
////                Bundle bundle = new Bundle();
////                bundle = buildBundleToSortGroups(bundle);
////                initFragmentTransation(bundle);
//                break;
//            }
            case R.id.action_send_evaluation: {
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
            serverResponse = userExamControl.createGrades(1, grades, exam, userClass);
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


//    private void initFragmentTransation(Bundle bundle){
//        FragmentTransaction fragmentTransaction = getFragmentManager().beginTransaction();
//        AreYouSureFragment areYouSureFragment = new AreYouSureFragment();
//        areYouSureFragment.setArguments(bundle);
//        areYouSureFragment.show(fragmentTransaction, "areYouSure");
//    }
//
//    private Bundle buildBundleToSortGroups(Bundle bundle){
//        bundle.putSerializable("firstGrades", StudentsFragment.getHashEmailAndGrade());
//        bundle.putSerializable("userClass", userClass);
//        bundle.putSerializable("exam", exam);
//
//        return bundle;
//    }


}
