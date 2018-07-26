package fga.mds.gpp.trezentos.View.ViewHolder;

import android.app.Dialog;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.support.annotation.RequiresApi;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.ContextThemeWrapper;
import android.view.Gravity;
import android.view.MenuInflater;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.NumberPicker;
import android.widget.PopupMenu;
import android.widget.TextView;

import fga.mds.gpp.trezentos.R;

import static fga.mds.gpp.trezentos.R.id.student_email;
import static fga.mds.gpp.trezentos.R.id.student_name;

public class StudentsViewHolder  extends RecyclerView.ViewHolder implements View.OnClickListener {

    public TextView userAccountName;
    public TextView userAccountEmail;
    public TextView gradeTextView;
    public TextView secondGradeTextView;
    public LinearLayout gradeLayout;
    public LinearLayout secondGradeLayout;
    public ImageButton imageButtonMore;


    public StudentsViewHolder(View view) {
        super(view);

        userAccountName = view.findViewById(student_name);
        userAccountEmail = view.findViewById(student_email);
        gradeLayout = view.findViewById(R.id.gradeLayout);
        secondGradeLayout = view.findViewById(R.id.second_grade_layout);
        gradeTextView = view.findViewById(R.id.text_view_grade);
        secondGradeTextView = view.findViewById(R.id.text_view_second_grade);
        imageButtonMore = view.findViewById(R.id.more_options);

        view.setOnClickListener(this);
    }

    // Define listener member variable
    private StudentsViewHolder.OnItemClickListener listener;

    // Define the listener interface
    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    // Define the method that allows the parent activity or fragment to define the listener
    public void setOnItemClickListener(StudentsViewHolder.OnItemClickListener listener) {
        this.listener = listener;
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


//    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
//    public void showPopup(View v) {
//
//        ContextThemeWrapper contextThemeWrapper = new ContextThemeWrapper(getActivity(), R.style.PopupMenuOverlapAnchor);
////            PopupMenu popupMenu = new PopupMenu(contextThemeWrapper, this);
//
//        PopupMenu popup = new PopupMenu(contextThemeWrapper, getView(), Gravity.RIGHT);
//
//        MenuInflater inflater = popup.getMenuInflater();
//        inflater.inflate(R.menu.menu_student, popup.getMenu());
//        popup.show();
//    }
//
//    @Override
//    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
//
//    }
//
//    int showGradePicker(final int CLICK) {
//        final Dialog d = new Dialog(getContext());
//        d.getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
//        d.setContentView(R.layout.dialog);
//
//        final NumberPicker np1 = d.findViewById(R.id.numberPicker1);
//        final NumberPicker np2 = d.findViewById(R.id.numberPicker2);
//
//        Button b1 = d.findViewById(R.id.button1);
//        Button b2 = d.findViewById(R.id.button2);
//        Button buttonOK300 = d.findViewById(R.id.button1);
//
//        np1.setMinValue(0);  // min value 0
//        np1.setMaxValue(10); // max value 100
//        np1.setWrapSelectorWheel(false);
//        np1.setOnValueChangedListener(this);
//
//        np2.setMinValue(0); // min value 0
//        np2.setMaxValue(99); // max value 100
//        np2.setWrapSelectorWheel(false);
//        np2.setOnValueChangedListener(this);
//
//        if(CLICK == 1) {
//            b1.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    gradeTextView.setText(String.valueOf(np1.getValue()) + "." +
//                            String.valueOf(String.format("%02d", np2.getValue()))); //set the value to textview
//
//                    Double grade = Double.valueOf(gradeTextView.getText().toString());
//                    String email = userAccountName.getText().toString();
//
//                    mapEmailAndGrade.put(email, grade);
//                    userExam.setFirstGrades(mapEmailAndGrade.toString());
//
//                    grade = Double.valueOf(gradeTextView.getText().toString());
//                    email = userAccountName.getText().toString();
//
//                    Log.i("MAP", email);
//                    Log.d("TAMANHOMAPA", Integer.toString(mapEmailAndGrade.size()));
//
//                    mapEmailAndGrade.put(email, grade);
//
//                    userExam.setFirstGrades(mapEmailAndGrade.toString());
//                    userExam.setSecondGrades(mapEmailAndGrade.toString());
//
//                    Log.d("TAMANHOMAPA", userExam.getFirstGrades());
//
//                    d.dismiss();
//                }
//            });
//        }else {
//
//            secondGradeTextView.setText(userExam.getSecondGrades());
//
//            buttonOK300.setOnClickListener(new View.OnClickListener() {
//                @Override
//                public void onClick(View v) {
//                    String EMAIL;
//                    Double GRADE;
//                    secondGradeTextView.setText(String.valueOf(np1.getValue()) + "." +
//                            String.valueOf(String.format
//                                    ("%02d", np2.getValue())));
//
//                    GRADE = Double.valueOf(secondGradeTextView.getText().toString());
//
//                    EMAIL = userAccountName.getText().toString();
//
//                    Log.d("TAMANHOMAPA", Integer.toString(mapEmailAndGrade.size()));
//
//                    mapEmailAndGrade.put(EMAIL, GRADE);
//
//                    userExam.setSecondGrades(mapEmailAndGrade.toString());
//
//                    Log.d("TAMANHOMAPA", userExam.getSecondGrades());
//
//                    d.dismiss();
//                }
//            });
//        }
//
//        b2.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                d.dismiss(); // dismiss the dialog
//            }
//        });
//        d.show();
//
//        return CLICK;
//    }

}
