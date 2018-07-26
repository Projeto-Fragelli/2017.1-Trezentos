package fga.mds.gpp.trezentos.View.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.support.annotation.RequiresApi;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;

import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import org.json.JSONException;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import fga.mds.gpp.trezentos.Controller.UserClassControl;
import fga.mds.gpp.trezentos.Controller.UserExamControl;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserAccount;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Activity.ExamActivity;
import fga.mds.gpp.trezentos.View.Adapters.ClassFragmentAdapter;
import fga.mds.gpp.trezentos.View.Adapters.StudentsAdapter;

import static fga.mds.gpp.trezentos.R.id.gradeLayout;
import static fga.mds.gpp.trezentos.R.id.student_email;
import static fga.mds.gpp.trezentos.R.id.student_name;
import static fga.mds.gpp.trezentos.R.id.text_view_grade;


public class StudentsFragment extends Fragment {

    public ArrayList<String> students = new ArrayList<>();
    private UserClass userClass;
    private Exam userExam;
    public UserClassControl userClassControl;
    public ArrayList<Student> userFromClass;
    public StudentsAdapter studentsAdapter;


    public View view;

    private static HashMap<String, Double> mapEmailAndGrade = new HashMap<>();

    public StudentsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        Intent intent = getActivity().getIntent();
        userClass = (UserClass) intent.getSerializableExtra("Class");
        userExam = (Exam) intent.getSerializableExtra("Exam");

        //students = array; // userClass.getStudents();
        //populateMapValues(students); //clear map and populates it
        //arrangeMap(students);//creates a new array of students that are enrolled at this class

        view = inflater.inflate(R.layout.fragment_students, container, false); // Inflate the layout for this fragment

        userClassControl = UserClassControl.getInstance(getActivity());

        try {
            userFromClass = userClassControl.getUsersFromClass(userClass.getIdClass(), userExam.getId());
        } catch (JSONException e) {
            e.printStackTrace();
        }

        if (userFromClass != null){
            for (UserAccount u: userFromClass) {
                students.add(u.getEmail());
            }
        }

        studentsAdapter = new StudentsAdapter(userFromClass, getContext());


        RecyclerView recyclerView = view.findViewById(R.id.recyclerStudents);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentsAdapter);

        return view;
    }


//    public HashMap<String, Double> populateMapValues(ArrayList<String> students) {
//        mapEmailAndGrade.clear();
//
//        for (int i = 0; i < students.size(); i++) {
//            mapEmailAndGrade.put(students.get(i), 0.00);
//        }
//        Log.d("DEBUGMAP", Integer.toString(mapEmailAndGrade.size()));
//        return mapEmailAndGrade;
//    }
//
    public static HashMap<String, Double> getHashEmailAndGrade() {
        Log.d("DEBUGMAP", Integer.toString(mapEmailAndGrade.size()));
        return mapEmailAndGrade;
    }
//
//    public ArrayList<String> arrangeMap(ArrayList<String> students) {
//        //if item is null it will be removed
//        int i = 0;
//        if (students.size() == 0) {
//            return students;
//        }else{
//            do {
//                if (students.get(i).length() == 0) {
//                    students.remove(i);
//                }
//                ++i;
//            } while (i < students.size());
//            Log.d("DEBUGMAP", Integer.toString(students.size()));
//            Log.d("DEBUGMAP", students.toString());
//            return students;
//        }
//    }
}