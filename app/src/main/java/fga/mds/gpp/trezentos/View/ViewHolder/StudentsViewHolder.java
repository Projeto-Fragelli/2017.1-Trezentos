package fga.mds.gpp.trezentos.View.ViewHolder;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.ImageButton;
import android.widget.TextView;

import fga.mds.gpp.trezentos.R;

public class StudentsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView userAccountName;
    public TextView userAccountEmail;
    public TextView firstGradeTextView;
    public TextView secondGradeTextView;
    public ImageButton moreOptionsButton;

    // Define listener member variable
    private OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }


    public StudentsViewHolder(View view) {
        super(view);

        userAccountName = view.findViewById(R.id.student_name);
        userAccountEmail = view.findViewById(R.id.student_email);
        firstGradeTextView = view.findViewById(R.id.text_view_first_grade);
        secondGradeTextView = view.findViewById(R.id.text_view_second_grade);
        moreOptionsButton = view.findViewById(R.id.more_options);

        firstGradeTextView.setOnClickListener(this);


    }

    @Override
    public void onClick(View v) {
        // Triggers click upwards to the adapter on click
        if (listener != null) {
            int position = getAdapterPosition();
            if (position != RecyclerView.NO_POSITION) {
                listener.onItemClick(itemView, position);
            }
        }

    }



}
