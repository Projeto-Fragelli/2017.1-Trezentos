package fga.mds.gpp.trezentos.View.Adapters;
import android.app.Dialog;
import android.content.Context;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.NumberPicker;

import java.util.ArrayList;

import fga.mds.gpp.trezentos.Model.Exam;
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
    private StudentsViewHolder studentsViewHolder;
    private StudentsViewHolder.OnItemClickListener listener;

    public void setOnItemClickListener(StudentsViewHolder.OnItemClickListener listener) {
        this.listener = listener;
    }

    public StudentsAdapter (ArrayList<Student> students, Context context) {
        this.students = students;
        this.context = context;
    }

    public ArrayList<Student> getStudents() {
        return students;
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
        String name = student.getFisrtName() + " " + student.getLastName();

        studentsViewHolder = (StudentsViewHolder) holder;

        studentsViewHolder.userAccountName.setText(name);
        studentsViewHolder.userAccountEmail.setText(student.getEmail());
        studentsViewHolder.firstGradeTextView.setText(String.valueOf(student.getFirstGrade()));
        studentsViewHolder.secondGradeTextView.setText(String.valueOf(student.getSecondGrade()));

        studentsViewHolder.firstGradeTextView.setOnClickListener(this);
        studentsViewHolder.secondGradeTextView.setOnClickListener(this);

        studentsViewHolder.firstGradeTextView.setTag(position);
        studentsViewHolder.secondGradeTextView.setTag(position);

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
        switch (v.getId()){
            case R.id.text_view_first_grade:
                showGradePicker((Integer) v.getTag(), 1);
                break;

            case R.id.text_view_second_grade:
                showGradePicker((Integer) v.getTag(), 2);
                break;

        }

    }

    private void showGradePicker(final int position, final int gradeType){
        Log.d("ALUNO INDEX", String.valueOf(position));

        final Dialog d = new Dialog(context);
        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        d.setContentView(R.layout.dialog);

        final NumberPicker np1 = d.findViewById(R.id.numberPicker1);
        final NumberPicker np2 = d.findViewById(R.id.numberPicker2);
        Button buttonOK = d.findViewById(R.id.button_ok);
        Button buttonCancel = d.findViewById(R.id.button_cancel);

        np1.setMinValue(0);
        np1.setMaxValue(9);
        np1.setWrapSelectorWheel(false);

        np2.setMinValue(0);
        np2.setMaxValue(9);
        np2.setWrapSelectorWheel(false);

        buttonOK.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {

                np1.clearFocus();
                np2.clearFocus();

                double np1Value = np1.getValue();
                double np2Value = np2.getValue();
                double value = np1Value + np2Value/10.0;

                Student student = students.get(position);
                if (gradeType == 1){
                    student.setFirstGrade(value);
                } else if (gradeType == 2 ){
                    student.setSecondGrade(value);
                }

                notifyDataSetChanged();
                Log.d("ESTUDANTE", student.getFisrtName());
                d.dismiss();
            }
        });

        buttonCancel.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                d.dismiss();
            }
        });

        d.show();


    }



}


