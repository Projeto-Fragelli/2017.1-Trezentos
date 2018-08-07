package fga.mds.gpp.trezentos.Controller;

import android.util.Log;

import java.util.ArrayList;

import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.Exam;
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

    public GroupController () {

    }

    public ArrayList<Group> sortGroups(){
        separateHelpedFromHelpers();

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

        Log.d("AJUDANTES", String.valueOf(helpers.size()));
        Log.d("AJUDADOS", String.valueOf(helped.size()));
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

    public ArrayList<Group> requestGroups(UserClass userClass, Exam exam, String userId) {

        //TODO PEGAR TODOS OS GRUPOS DE UMA PROVA NA API
        //GRUPOS DE TESTE

        ArrayList<Group> groups = new ArrayList<>();
        for (int i=0; i<5; i++){
            Group group = new Group(1);
            double j;

            for (j = 0; j < 5; j++) {

                Student s = new Student();
                s.setFirstGrade(j);
                s.setSecondGrade(j);

                try {
                    s.setFirstName("Teste" + i);
                } catch (UserException e) {
                    e.printStackTrace();
                }

                group.addHelper(s);
                group.addHelped(s);

            }
            groups.add(group);
        }

        return groups;
    }

    public Group requestStudentGroup(UserClass userClass, Exam exam, String userId){
        //TODO PEGAR GRUPO DO ALUNO NA API
        //GRUPO DE TESTE

        Group group = new Group(1);
        double i;

        for(i=0; i<10; i++){

            Student s = new Student();
            s.setFirstGrade(i);
            s.setSecondGrade(i);

            try {
                s.setFirstName("Teste" + i);
            } catch (UserException e) {
                e.printStackTrace();
            }

            group.addHelper(s);
            group.addHelped(s);

        }

        return group;

    }

    public void sendGroups(ArrayList<Group> groups){
        //TODO ENVIAR GRUPOS PARA A API

    }



}