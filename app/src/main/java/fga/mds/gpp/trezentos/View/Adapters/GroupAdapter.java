package fga.mds.gpp.trezentos.View.Adapters;

import android.content.Context;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import fga.mds.gpp.trezentos.Model.Group;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.ViewHolder.GroupViewHolder;

public class GroupAdapter extends RecyclerView.Adapter  {

    private  Context context;
    private ArrayList<Group> groups;
    private UserClass userClass;
    private GroupViewHolder groupViewHolder;
    private GroupStudentAdapter groupStudentAdapterHelped;
    private GroupStudentAdapter groupStudentAdapterHelpers;


    public GroupAdapter(ArrayList<Group> groups, UserClass userClass, Context context) {
        this.groups = groups;
        this.userClass = userClass;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.groups_item, parent, false);
        GroupViewHolder holder = new GroupViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Group group = groups.get(position);
        groupViewHolder = (GroupViewHolder) holder;

        groupViewHolder.groupNumber.setText("Grupo " + String.valueOf(position + 1));

        groupStudentAdapterHelped = new GroupStudentAdapter(group.getHelped(), userClass, context);
        groupStudentAdapterHelpers = new GroupStudentAdapter(group.getHelpers(), userClass, context);


        LinearLayoutManager layoutManagerHelpers = new LinearLayoutManager(context);
        LinearLayoutManager layoutManagerHelped = new LinearLayoutManager(context);

        layoutManagerHelpers.setAutoMeasureEnabled(true);
        layoutManagerHelped.setOrientation(LinearLayoutManager.VERTICAL);

        groupViewHolder.helpers.setLayoutManager(layoutManagerHelpers);
        groupViewHolder.helped.setLayoutManager(layoutManagerHelped);

        groupViewHolder.helpers.setAdapter(groupStudentAdapterHelpers);
        groupViewHolder.helped.setAdapter(groupStudentAdapterHelped);


    }

    @Override
    public int getItemCount() {
        if(groups == null){
            return 0;
        }else{
            return groups.size();
        }

    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }

    public void updateGroups(ArrayList<Group> groups){
        this.groups = groups;
        notifyDataSetChanged();
    }

}
