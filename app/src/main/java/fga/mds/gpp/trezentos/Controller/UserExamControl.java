package fga.mds.gpp.trezentos.Controller;

import android.content.Context;
import android.util.Log;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.ExecutionException;
import fga.mds.gpp.trezentos.DAO.RequestHandler;
import fga.mds.gpp.trezentos.DAO.URLs;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.Exam;
import fga.mds.gpp.trezentos.Model.Student;


public class UserExamControl{
    private static UserExamControl instance;
    private final Context context;
    private Exam exam;

    public UserExamControl(final Context context){
        this.context = context;
    }

    public static UserExamControl getInstance(final Context context){
        if(instance == null){
            instance = new UserExamControl(context);
        }
        return instance;
    }

    public String validateCreateExam(String examName, String idPerson, String idClassCreator, String idClass)
            throws UserException{
        try{
            exam = new Exam(examName, idPerson, idClassCreator, idClass);
            return "";
        }catch(UserException userException){
            return userException.getMessage();
        }
    }

    public String createExam() {
        RequestHandler requestHandler = new RequestHandler(URLs.URL_CREATE_EXAM, getCreateExamParams());

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", ""+serverResponse);
        return serverResponse;

    }

    private HashMap<String, String> getCreateExamParams() {

        HashMap<String, String> params = new HashMap<>();
        params.put("idClass", exam.getIdClass());
        params.put("idClassCreator", exam.getIdClassCreator());
        params.put("idPerson", exam.getIdPerson());
        params.put("examDescription", exam.getNameExam());

        return params;

    }

    public ArrayList<Exam> getExamsFromUser(String idPerson, String idClass) throws JSONException {
        RequestHandler requestHandler = new RequestHandler(URLs.URL_GET_EXAMS_FROM_USER, getExamsFromUserParams(idPerson, idClass));

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", serverResponse);

        JSONObject object = new JSONObject(serverResponse);
        String erro = object.getString("error");
        String message = object.getString("message");
        JSONArray classArrayJson = object.getJSONArray("exames");

        ArrayList<Exam> userExams = null;
        userExams = getExamArrayList(classArrayJson);

        return userExams;

    }

    //Create Exam
    private HashMap<String, String> getExamsFromUserParams(String idPerson,String idClass) {

        HashMap<String, String> params = new HashMap<>();
        params.put("idClass", idClass);
        params.put("idPerson", idPerson);

        return params;

    }

    public ArrayList<Exam> getExamArrayList(JSONArray array) throws JSONException{


        ArrayList<Exam> exams = new ArrayList<>();
        for(int i = 0; i < array.length(); i++){
            Exam userExam = getUserClassFromJson(array.getJSONObject(i));
            exams.add(userExam);
        }

        return exams;
    }

    private Exam getUserClassFromJson(JSONObject jsonObject) {
        Exam exam = new Exam();

        try{
            exam.setId(jsonObject.getString("idExam"));
            exam.setNameExam(jsonObject.getString("examDescription"));

        }catch(UserException e){
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }

        return exam;
    }

    public ArrayList<String> getGradesFromStudents(ArrayList<Student> students, int gradeType){
        ArrayList<String> grades = new ArrayList<>();


        if (gradeType == 1){
            for (Student s1: students){
                grades.add(String.valueOf(s1.getFirstGrade()));
            }

        } else if (gradeType == 2){
            for (Student s2: students){
                grades.add(String.valueOf(s2.getSecondGrade()));
            }

        }

        return grades;

    }

    public String createGrades(int gradeType) {
        RequestHandler requestHandler = new RequestHandler(URLs.URL_CREATE_USER_GRADES, getCreateGradeParams(gradeType));

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", ""+serverResponse);
        return serverResponse;

    }



    private HashMap<String, String> getCreateGradeParams(int gradeType) {

        //TODO CORRIGIR PARA ENVIAR NOTAS

        HashMap<String, String> params = new HashMap<>();


        params.put("personAndGrade[0][id]", "");
        params.put("personAndGrade[0][grade]", "");
        params.put("idClassCreator", exam.getIdClassCreator());
        params.put("idClass", exam.getIdClass());
        params.put("idExam", exam.getId());
        params.put("personAndGrade[1][id]", "" );
        params.put("personAndGrade[1][grade]", "");
        params.put("idGradeType", String.valueOf(gradeType));


        return params;

    }


}