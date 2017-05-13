package fga.mds.gpp.trezentos.View;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import java.util.ArrayList;
import java.util.List;

import fga.mds.gpp.trezentos.Controller.UserClassControl;
import fga.mds.gpp.trezentos.Model.UserAccount;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;

public class ClassFragment extends Fragment{

    public ArrayList<UserClass> userClasses;
    private static CustomAdapter adapter;
    private FloatingActionButton floatingActionButton;
    private FragmentTransaction fragmentTransaction;
    public ListView listView;

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public void onResume(){
        super.onResume();

        loadClasses();
        adapter = new CustomAdapter(userClasses,getActivity().getApplicationContext());
        if(listView.getAdapter() == null){ //Adapter not set yet.
            listView.setAdapter(adapter);
        }
        else{ //Already has an adapter
            listView.setAdapter(adapter);
            adapter.notifyDataSetChanged();
            listView.invalidateViews();
            listView.refreshDrawableState();
        }
    }

    private void loadClasses(){
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(getActivity());
        String email = session.getString("userEmail","");
        UserClassControl userClassControl = UserClassControl.getInstance(getActivity());

        userClasses = userClassControl.getClassesFromUser(email);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState){
        final View view = inflater.inflate(R.layout.fragment_class, container, false);
        listView = (ListView) view.findViewById(R.id.class_list_view);

        final UserClass userClass = new UserClass();
        final UserAccount userAccount = new UserAccount();

        loadClasses();

        adapter = new CustomAdapter(userClasses,getActivity().getApplicationContext());

        listView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id){

                Intent goClass = new  Intent(getActivity(), ClassActivity.class);
                UserClass userClassCalled = (UserClass) listView.getItemAtPosition(position);
                goClass.putExtra("Class", userClassCalled);

                startActivity(goClass);
            }
        });

        floatingActionButton = (FloatingActionButton) view.findViewById(R.id.class_image_button);
        floatingActionButton.setOnClickListener(new FloatingActionButton.OnClickListener(){
            @Override
            public void onClick(View v) {

                startActivity(new Intent(getActivity(), CreateClassActivity.class));
            }
        });

        return view;
    }
}