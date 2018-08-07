package fga.mds.gpp.trezentos.View.Fragment;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import fga.mds.gpp.trezentos.Controller.GroupController;
import fga.mds.gpp.trezentos.Model.Exam;

import fga.mds.gpp.trezentos.Model.Group;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.GroupStudentAdapter;

public class ShowStudentGroupFragment extends Fragment {

    private Exam userExam;
    private UserClass userClass;
    private GroupController groupController;
    private String userId;
    private Group group;
    private GroupStudentAdapter groupStudentAdapterHelped;
    private GroupStudentAdapter groupStudentAdapterHelpers;

    public ShowStudentGroupFragment() {

    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    private void loadRecover() {

        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(getActivity());
        userId = session.getString("userId", "");

        Intent intent = getActivity().getIntent();
        userClass = (UserClass) intent.getSerializableExtra("Class");

        userExam = (Exam) intent.getSerializableExtra("Exam");

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        final View view = inflater.inflate(R.layout.fragment_show_student_group, container, false);
        loadRecover();

        TextView groupNumber = view.findViewById(R.id.group_number);
        RecyclerView helpers = view.findViewById(R.id.helpers_list);
        RecyclerView helped = view.findViewById(R.id.helped_list);

        groupNumber.setText("Meu grupo");

        groupController = new GroupController();
        group = groupController.requestStudentGroup(userClass, userExam, userId);

        groupStudentAdapterHelped = new GroupStudentAdapter(group.getHelped(), userClass, getContext());
        groupStudentAdapterHelpers = new GroupStudentAdapter(group.getHelpers(), userClass, getContext());

        LinearLayoutManager layoutManagerHelpers = new LinearLayoutManager(getContext());
        LinearLayoutManager layoutManagerHelped = new LinearLayoutManager(getContext());

        layoutManagerHelped.setOrientation(LinearLayoutManager.VERTICAL);

        helpers.setLayoutManager(layoutManagerHelpers);
        helped.setLayoutManager(layoutManagerHelped);

        helpers.setAdapter(groupStudentAdapterHelpers);
        helped.setAdapter(groupStudentAdapterHelped);


        return view;
    }


}