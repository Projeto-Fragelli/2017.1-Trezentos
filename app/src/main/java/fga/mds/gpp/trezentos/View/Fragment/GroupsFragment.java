package fga.mds.gpp.trezentos.View.Fragment;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import fga.mds.gpp.trezentos.Controller.GroupController;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Group;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Adapters.GroupAdapter;
import fga.mds.gpp.trezentos.View.RecyclerViewOnClickListener;

public class GroupsFragment  extends Fragment {
    private ArrayList<Group> groups;
    private Exam exam;
    private UserClass userClass;
    private GroupAdapter groupAdapter;

    public void setGroups(ArrayList<Group> groups) {
        this.groups = groups;
        groupAdapter.updateGroups(groups);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container
            , Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_groups, container, false);
        Intent intent = getActivity().getIntent();
        exam = (Exam) intent.getSerializableExtra("Exam");
        userClass = (UserClass) intent.getSerializableExtra("Class");


        groupAdapter = new GroupAdapter(groups, userClass, getContext());

        RecyclerView recyclerView = view.findViewById(R.id.recycler_groups);
        LinearLayoutManager layoutManager = new LinearLayoutManager(getActivity());

        layoutManager.setAutoMeasureEnabled(true);
        layoutManager.setOrientation(LinearLayoutManager.VERTICAL);

        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(groupAdapter);

        return view;
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        super.onCreateOptionsMenu(menu, inflater);
        if(userClass.getStudents().size() < userClass.getSizeGroups()) {
            menu.findItem(R.id.action_sort_groups).setVisible(false);
        } else {
            menu.findItem(R.id.action_sort_groups).setVisible(true);
        }
    }




}