package fga.mds.gpp.trezentos.Controller;

import android.util.Log;

import java.util.ArrayList;
import fga.mds.gpp.trezentos.Model.Group;
import fga.mds.gpp.trezentos.Model.Student;
import fga.mds.gpp.trezentos.Model.UserClass;

public class GroupController {
    private ArrayList<Student> students;
    private UserClass userClass;
    private ArrayList<Student> helpers = new ArrayList<>();
    private ArrayList<Student> helped = new ArrayList<>();
    private ArrayList<Group> examGroups = new ArrayList<>();

    public GroupController (UserClass userClass, ArrayList<Student> students) {
        this.userClass = userClass;
        this.students = students;
    }

    public ArrayList<Group> sortGroups(){
        separateHelpedFromHelpers();

        int studentsPerHelper = helped.size() / helpers.size();
        Log.d("ESTUDANTES POR AJUDANTE", " " + studentsPerHelper);
        int groupNumber = 1;
        int i, helpersCount = 0, helpedCount = 0;


        if(helped.size() >= helpers.size()){
            sortWtihMoreHelped();
        } else {
            sortWithMoreHelpers();
        }

        for (Group g: examGroups){
            for (Student s: g.getHelped()){
                Log.d("ESTUDANTE AJUDADO", s.getFisrtName() + " " + g.getNumber() + " AJUDADO");
            }
            for (Student s: g.getHelpers()){
                Log.d("ESTUDANTE AJUDANTE", s.getFisrtName() + " " + g.getNumber() + " AJUDANTE");
            }
        }

        return examGroups;


    }

    private void separateHelpedFromHelpers(){
        for (Student s: students){
            if (s.getFirstGrade() > userClass.getCutOff()){
                helpers.add(s);

            }else{
                helped.add(s);

            }
        }

        Log.d("AJUDANTE", String.valueOf(helpers.size()));
        Log.d("AJUDADO", String.valueOf(helped.size()));
    }

    private void sortWithMoreHelpers(){
        int helperPerHelped = helpers.size() / helped.size();
        Log.d("AJUDANTES POR AJUDADO", " " + helperPerHelped);
        int groupNumber = 1;
        int i, helpersCount = 0, helpedCount = 0;


        while (helpersCount < helpers.size() || helpedCount < helped.size()){
            Group group = new Group(groupNumber);

            for (i = 0; i < userClass.getSizeGroups(); i++){

                if (i%helperPerHelped == 0 && helpedCount < helped.size()){
                    group.addHelped(helped.get(helpedCount));
                    helpedCount++;
                } else if (helpersCount < helpers.size()) {
                    group.addHelper(helpers.get(helpersCount));
                    helpersCount++;
                }
            }

            examGroups.add(group);

            groupNumber++;
        }
    }

    private void sortWtihMoreHelped(){
        int helpedPerHelper = helped.size() / helpers.size();
        Log.d("AJUDADOS POR AJUDANTE", " " + helpedPerHelper);
        int groupNumber = 1;
        int i, helpersCount = 0, helpedCount = 0;


        while (helpersCount < helpers.size() || helpedCount < helped.size()){
            Group group = new Group(groupNumber);

            for (i = 0; i < userClass.getSizeGroups(); i++){

                if (i%helpedPerHelper == 0 && helpersCount < helpers.size()){
                    group.addHelper(helpers.get(helpersCount));
                    helpersCount++;
                } else if (helpedCount < helped.size()) {
                    group.addHelped(helped.get(helpedCount));
                    helpedCount++;
                }
            }

            examGroups.add(group);

            groupNumber++;
        }
    }



}