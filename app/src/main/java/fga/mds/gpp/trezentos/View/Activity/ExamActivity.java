package fga.mds.gpp.trezentos.View.Activity;

import android.app.FragmentTransaction;
import android.content.Intent;
import android.content.SearchRecentSuggestionsProvider;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

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

        UserExamControl userExamControl = UserExamControl.getInstance(getApplicationContext());

        switch (item.getItemId()){
            case R.id.action_update_grades:{
                students = studentsFragment.getStudents();

                HashMap<String, String> firstGrades = userExamControl.getGradesFromStudents(students, 1);

                userExamControl.createGrades(1, firstGrades, exam, userClass);

                break;
            }

            case R.id.action_update_trezentos_grades: {
                students = studentsFragment.getStudents();

                HashMap<String,String> secondGrades = userExamControl.getGradesFromStudents(students, 2);

                userExamControl.createGrades(2, secondGrades, exam, userClass);

                //TODO COLOCAR AS NOTAS NO FORMATO DESEJADO PRA ENVIAR - ARRAY LIST DE STRING PROVISÓRIO
                //TODO VALIDAR RESPOSTA DA API
                //exam.setSecondGrades(firstGrades);
                //response = userExamControl.createGrades(2);
                //validateServerResponse(response);

                break;
            }

            case R.id.action_sort_groups:{
//                Bundle bundle = new Bundle();
//                bundle = buildBundleToSortGroups(bundle);
//                initFragmentTransation(bundle);
                break;
            }

            case R.id.action_send_evaluation: {

                break;
            }

        }
        return super.onOptionsItemSelected(item);

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
