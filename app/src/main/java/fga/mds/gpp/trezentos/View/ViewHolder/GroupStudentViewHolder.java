package fga.mds.gpp.trezentos.View.ViewHolder;

import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;

import fga.mds.gpp.trezentos.R;

public class GroupStudentViewHolder extends RecyclerView.ViewHolder {

    public TextView studentName;
    public TextView grade1;
    public TextView grade2;
    public TextView finalGrade;

    public GroupStudentViewHolder (View view) {
        super(view);

        studentName = view.findViewById(R.id.student_name);
        grade1 = view.findViewById(R.id.grade1);
        grade2 = view.findViewById(R.id.grade2);
        finalGrade = view.findViewById(R.id.final_grade);

    }
}
