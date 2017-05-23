package fga.mds.gpp.trezentos.Controller;


import android.content.Context;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.concurrent.ExecutionException;

import fga.mds.gpp.trezentos.DAO.CreateClassPost;
import fga.mds.gpp.trezentos.DAO.CreateExamPost;
import fga.mds.gpp.trezentos.DAO.getClassRequest;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.UserClass;

public class UserExamControl {

    private static UserExamControl instance;
    private final Context context;

    private UserExamControl(final Context context){

        this.context = context;

    }

    public static UserExamControl getInstance(final Context context){

        if(instance == null){

            instance = new UserExamControl(context);

        }

        return instance;
    }

    public void validateCreateExam(String examName, String userClassName, String classOwnnerEmail)
            throws UserException {

        try {
            Exam exam = new Exam(examName, userClassName, classOwnnerEmail);

            CreateExamPost createExamPost = new CreateExamPost(exam);
            createExamPost.execute();

        }catch (UserException userException){
            userException.printStackTrace();
        }


    }

    public String validateInformation(String examName, String userClassName, String classOwnerEmail) throws UserException {

        String erro;
        try{
            Exam exam = new Exam(examName, userClassName, classOwnerEmail);

            erro = "Sucesso";
            return erro;
        }catch (UserException userException){

            erro = userException.getMessage();
            return erro;
        }
    }


    //GET FROM API
    public ArrayList<Exam> getExamsFromUser(String examName) {

        getClassRequest classRequest = new getClassRequest(examName);

        String serverResponse = "404";

        try {
            serverResponse = classRequest.execute().get();

        } catch (InterruptedException e) {
            e.printStackTrace();
        } catch (ExecutionException e) {
            e.printStackTrace();
        }

        ArrayList<Exam> userExams = new ArrayList<Exam>();

        try {
            userExams = getArrayList(serverResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return userExams;

    }

    private ArrayList<Exam> getArrayList(String serverResponse) throws JSONException {

        JSONArray array = null;

        try {
            array = new JSONArray(serverResponse);
        } catch (JSONException e) {
            e.printStackTrace();
        }

        ArrayList<Exam> userExams = new ArrayList<>();

        for(int i = 0; i < array.length(); i++){

            Exam exam = getUserClassFromJson(array.getJSONObject(i));

            userExams.add(exam);

        }

        return userExams;
    }

    private Exam getUserClassFromJson(JSONObject jsonObject) {

        Exam exam = new Exam();


        try {
            exam.setNameExam(jsonObject.getString("name"));
            exam.setUserClassName(jsonObject.getString("userClassName"));
            exam.setClassOwnerEmail(jsonObject.getString("classOwnerEmail"));


        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        }


        return exam;
    }
}
