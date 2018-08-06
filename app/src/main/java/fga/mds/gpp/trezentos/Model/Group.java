package fga.mds.gpp.trezentos.Model;

import java.util.ArrayList;

public class Group {
    private int number;
    private ArrayList<Student> helped = new ArrayList<>();
    private ArrayList<Student> helpers = new ArrayList<>();

    public Group (int number){
        this.number = number;
    }

    public void addHelped(Student helped){
        this.helped.add(helped);
    }

    public void addHelper(Student helper) {
        this.helpers.add(helper);
    }

    public ArrayList<Student> getHelped() {
        return helped;
    }

    public ArrayList<Student> getHelpers() {
        return helpers;
    }

    public int getNumber() {
        return number;
    }
}
