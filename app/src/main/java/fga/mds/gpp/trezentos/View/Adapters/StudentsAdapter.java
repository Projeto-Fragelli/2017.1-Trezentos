package fga.mds.gpp.trezentos.View.Adapters;
import android.content.Context;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserAccount;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Fragment.StudentsFragment;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.View.ViewHolder.ClassViewHolder;
import fga.mds.gpp.trezentos.View.ViewHolder.StudentsViewHolder;


public class StudentsAdapter extends RecyclerView.Adapter implements View.OnClickListener {
    private ArrayList<Student> students;
    private Context context;
    private StudentsViewHolder.OnItemClickListener listener;

    public void setOnItemClickListener(StudentsViewHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    public StudentsAdapter (ArrayList<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.student_item, parent, false);
        StudentsViewHolder holder = new StudentsViewHolder(view);
        holder.setOnItemClickListener(listener);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Student student = students.get(position);
        StudentsViewHolder studentsViewHolder = (StudentsViewHolder) holder;

        String name = student.getFisrtName() + " " + student.getLastName();

        studentsViewHolder.userAccountName.setText(name);
        studentsViewHolder.userAccountEmail.setText(student.getEmail());
        studentsViewHolder.gradeTextView.setText(String.valueOf(student.getFirstGrade()));
        studentsViewHolder.secondGradeTextView.setText(String.valueOf(student.getSecondGrade()));
    }

    @Override
    public long getItemId(int position) {
        return super.getItemId(position);
    }


    @Override
    public int getItemCount() {
        if(students == null){
            return 0;
        }else{
            return students.size();
        }
    }

    @Override
    public void onClick(View v) {

    }

//    public void setFilteredList (ArrayList<UserClass> filteredList){
//        this.students = filteredList;
//        notifyDataSetChanged();
//    }
}


