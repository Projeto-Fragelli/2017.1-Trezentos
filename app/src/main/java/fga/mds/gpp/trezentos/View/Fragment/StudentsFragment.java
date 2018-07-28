package fga.mds.gpp.trezentos.View.Fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;

import fga.mds.gpp.trezentos.Controller.UserClassControl;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.StudentsAdapter;


public class StudentsFragment extends Fragment {

    private UserClass userClass;
    private Exam userExam;
    public UserClassControl userClassControl;
    public ArrayList<Student> students;
    public StudentsAdapter studentsAdapter;


    public View view;

    private static HashMap<String, Double> mapEmailAndGrade = new HashMap<>();

    public StudentsFragment() {
        // Required empty public constructor
    }

    public ArrayList<Student> getStudents() {
        return studentsAdapter.getStudents();
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

        view = inflater.inflate(R.layout.fragment_students, container, false); // Inflate the layout for this fragment

        userClassControl = UserClassControl.getInstance(getActivity());

        try {
            students = userClassControl.getUsersFromClass(userClass.getIdClass(), userExam.getId());
            Log.d("ESTUDANTES ENCONTRADOS", String.valueOf(students.size()));
        } catch (JSONException e) {
            e.printStackTrace();
        }


        studentsAdapter = new StudentsAdapter(students, getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recyclerStudents);
        final LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(studentsAdapter);

        return view;
    }


    public static HashMap<String, Double> getHashEmailAndGrade() {
        Log.d("DEBUGMAP", Integer.toString(mapEmailAndGrade.size()));
        return mapEmailAndGrade;
    }


}