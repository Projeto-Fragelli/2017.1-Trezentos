package fga.mds.gpp.trezentos.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import fga.mds.gpp.trezentos.R;


public class GroupViewHolder extends RecyclerView.ViewHolder {

    public TextView groupNumber;
    public RecyclerView helpers;
    public RecyclerView helped;

    public GroupViewHolder (View view) {
        super(view);
        groupNumber = view.findViewById(R.id.group_number);
        helpers = view.findViewById(R.id.helpers_list);
        helped = view.findViewById(R.id.helped_list);

    }
}



