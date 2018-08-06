package fga.mds.gpp.trezentos.View.Adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import java.util.ArrayList;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserClass;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.ViewHolder.GroupStudentViewHolder;


public class GroupStudentAdapter extends RecyclerView.Adapter{
    private Context context;
    private ArrayList<Student> students;
    private UserClass userClass;
    private GroupStudentViewHolder groupStudentViewHolder;


    public GroupStudentAdapter(ArrayList<Student> students, UserClass userClass, Context context) {
        this.students = students;
        this.userClass = userClass;
        this.context = context;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View view = LayoutInflater.from(context).inflate(R.layout.group_student_item, parent, false);
        GroupStudentViewHolder holder = new GroupStudentViewHolder(view);

        return holder;
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        Student student = students.get(position);
        groupStudentViewHolder = (GroupStudentViewHolder) holder;

        String name = student.getFisrtName() + " " + student.getLastName();
        Double finalGrade = student.getFirstGrade() + (userClass.getCutOff() * student.getSecondGrade() / 10);

        groupStudentViewHolder.studentName.setText(name);
        groupStudentViewHolder.grade1.setText(String.valueOf(student.getFirstGrade()));
        groupStudentViewHolder.grade2.setText(String.valueOf(student.getSecondGrade()));
        groupStudentViewHolder.finalGrade.setText(String.valueOf(finalGrade));


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
    public long getItemId(int position) {
        return super.getItemId(position);
    }
}
