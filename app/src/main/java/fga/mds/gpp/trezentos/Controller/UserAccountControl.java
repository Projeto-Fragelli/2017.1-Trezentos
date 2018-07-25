package fga.mds.gpp.trezentos.Controller;

import android.content.Context;
import android.content.SharedPreferences;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.preference.PreferenceManager;
import android.util.Log;

import com.facebook.AccessToken;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.HttpMethod;
import com.facebook.login.LoginManager;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.logging.Logger;
import java.util.concurrent.ExecutionException;

import fga.mds.gpp.trezentos.DAO.PostDao;
import fga.mds.gpp.trezentos.DAO.RequestHandler;
import fga.mds.gpp.trezentos.DAO.URLs;
import fga.mds.gpp.trezentos.Model.UserAccount;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.Model.Util.PasswordUtil;
import fga.mds.gpp.trezentos.R;
import okhttp3.HttpUrl;

public class UserAccountControl {
    private static final String TAG = UserAccountControl.class.getSimpleName();

    private static UserAccountControl instance;
    final Context context;
    private UserAccount userAccount;
    private SharedPreferences session;


    private UserAccountControl(final Context context){
        this.context = context;
        session = PreferenceManager.getDefaultSharedPreferences(context);
    }

    public static UserAccountControl getInstance(final Context context){
        if(instance == null){
            instance = new UserAccountControl(context);
        }
        return instance;
    }

    //Sign-up
    public String validateSignUp(String firstName, String lastName, String email, String telephoneDDI,
                                 String telephoneDDD, String telephoneNumber, String password, String passwordConfirmation){

        try{
            userAccount = new UserAccount(  firstName,
                                            lastName,
                                            email,
                                            telephoneDDI,
                                            telephoneDDD,
                                            telephoneNumber,
                                            password,
                                            passwordConfirmation);
        }catch(UserException userException){
            return userException.getMessage();
        }

        return "";
    }

    public String validateSignUpResponse(){

        RequestHandler requestHandler = new RequestHandler(URLs.URL_REGISTER, getSignUpParams( false));

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", serverResponse);
        return serverResponse;
    }

    private HashMap<String, String>  getSignUpParams(Boolean isFromFacebook) {
        HashMap<String, String> params = new HashMap<>();
        if (isFromFacebook){
            params.put("PersonFirstName", userAccount.getFisrtName());
            params.put("PersonLastName", userAccount.getLastName());
            params.put("PersonEmail", userAccount.getEmail());
            params.put("PersonIsFromFacebook", String.valueOf(isFromFacebook));
            params.put("PersonPassword", "null");
            params.put("PersonTelephoneDDI", "null");
            params.put("PersonTelephoneDDD", "null");
            params.put("PersonTelephoneNumber", "null");

        } else {
            params.put("PersonFirstName", userAccount.getFisrtName());
            params.put("PersonLastName", userAccount.getLastName());
            params.put("PersonEmail", userAccount.getEmail());
            params.put("PersonPassword", userAccount.getPassword());
            params.put("PersonIsFromFacebook", isFromFacebook.toString());
            params.put("PersonTelephoneDDI", userAccount.getTelephoneDDI());
            params.put("PersonTelephoneDDD", userAccount.getTelephoneDDD());
            params.put("PersonTelephoneNumber", userAccount.getTelephoneNumber());
        }
        return params;
    }
    //End Sign-up


    //Sign-in
    public String authenticateSignIn(String email, String password){
        try{
            userAccount = new UserAccount();
            userAccount.setEmail(email);
            userAccount.authenticatePassword(password);
        }catch(UserException userException){
            return userException.getMessage();
        }

        return "";
    }

    public String validateSignInResponse(){
        RequestHandler requestHandler = new RequestHandler(URLs.URL_LOGIN, getSignInParams(false));
        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", serverResponse);
        return serverResponse;
    }

    private HashMap<String, String>  getSignInParams(Boolean isFromFacebook) {
        HashMap<String, String> params = new HashMap<>();
        if (isFromFacebook){
            params.put("PersonEmail", userAccount.getEmail());
            params.put("PersonPassword", "null");
        } else {
            params.put("PersonEmail", userAccount.getEmail());
            params.put("PersonPassword", userAccount.getPassword());
        }


        return params;

    }

    public void createPerson(String serverResponse) throws UserException, JSONException, NullPointerException {
        JSONObject object = getObjectFromServerResponse(serverResponse);
        JSONObject userJson = object.getJSONObject("person");

        userAccount.setId(String.valueOf(userJson.getInt("idPerson")));
        userAccount.setFirstName(userJson.getString("PersonFirstName"));
        userAccount.setLastName(userJson.getString("PersonLastName"));
        userAccount.setEmail(userJson.getString("PersonEmail"));
        userAccount.setTelephoneDDI(userJson.getString("PersonTelephoneDDI"));
        userAccount.setTelephoneDDD(userJson.getString("PersonTelephoneDDD"));
        userAccount.setTelephoneNumber(userJson.getString("PersonTelephoneNumber"));
        userAccount.setIsFromFacebook(userJson.getBoolean("PersonIsFromFacebook"));

    }
    //End Sign-in


    //Sign-in Facebook
    public void signInUserFromFacebook(JSONObject object){
        userAccount = new UserAccount();
        String fName = null, email = null, lName = null;

        try {
            fName = object.getString("first_name");
            lName = object.getString("last_name");
            email = object.getString("email");

            userAccount = new UserAccount();
            userAccount.setEmail(email);
            userAccount.setFirstName(fName);
            userAccount.setLastName(lName);

        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UserException e) {
            e.printStackTrace();
        }

    }

    public String validateFacebookAccount(){

        RequestHandler requestHandler = new RequestHandler(URLs.URL_REGISTER, getSignUpParams( true));

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", serverResponse);
        return serverResponse;
    }

    public String validateFacebookLogin(){

        RequestHandler requestHandler = new RequestHandler(URLs.URL_LOGIN, getSignInParams(true));

        String serverResponse = "404";

        try{
            serverResponse = requestHandler.execute().get();
        }catch(InterruptedException e){
            e.printStackTrace();
        }catch(ExecutionException e){
            e.printStackTrace();
        }
        Log.d("RESPONSE", serverResponse);
        return serverResponse;
    }

    public void logInUserFb(){
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        session.edit()
                .putBoolean("IsUserLogged", true)
                .putString("userEmail", userAccount.getEmail())
                .putString("userFirstName", userAccount.getFisrtName())
                .putString("userLastName", userAccount.getLastName())
                .apply();
    }

    //End Sign-in Facebook


    //Log out Facebook
    public void disconnectFromFacebook() {

        if (AccessToken.getCurrentAccessToken() == null) {
            return; // already logged out
        }

        new GraphRequest(AccessToken.getCurrentAccessToken(), "/me/permissions/", null, HttpMethod.DELETE, new GraphRequest
                .Callback() {
            @Override
            public void onCompleted(GraphResponse graphResponse) {

                LoginManager.getInstance().logOut();

            }
        }).executeAsync();
    }
    //End log out Facebook


    //Reset Password
    public String validateResetPasswordResponse(String recoverEmail){

        RequestHandler requestHandler = new RequestHandler(URLs.URL_RESET_PASSWORD, getResetPasswordParams(recoverEmail));

        String serverResponse = "";

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

    public HashMap<String, String>  getResetPasswordParams(String recoverEmail) {

        HashMap<String, String> params = new HashMap<>();

        params.put("PersonEmail", recoverEmail);

        return params;

    }
    //End Reset Password


    //Common
    private JSONObject getObjectFromServerResponse(String serverResponse){
        JSONObject object = null;

        try{
            object = new JSONObject(serverResponse);
        }catch(JSONException e){
            e.printStackTrace();
        }

        return object;
    }

    public void logInUser(){
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        session.edit()
                .putBoolean("IsUserLogged", true)
                .putString("userId", userAccount.getId())
                .putString("userEmail", userAccount.getEmail())
                .putString("userFirstName", userAccount.getFisrtName())
                .putString("userLastName", userAccount.getLastName())
                .putString("userTelephoneDDI", userAccount.getTelephoneDDI())
                .putString("userTelephoneDDD", userAccount.getTelephoneDDD())
                .putString("userTelephoneNumber", userAccount.getTelephoneNumber())
                .apply();

    }

    public void logOutUser(){
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);

        session.edit()
                .putBoolean("IsUserLogged", false)
                .putString("userId", "")
                .putString("userFirstName", "")
                .putString("userLastName", "")
                .putString("userEmail", "")
                .putString("userTelephoneDDI", "")
                .putString("userTelephoneDDD", "")
                .putString("userTelephoneNumber", "")
                .apply();
    }

    public boolean isLoggedUser() {
//        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(context);
//
//        if (session.getString("userId", "").equals("")) {
//            return false;
//        } else {
            return session.getBoolean("IsUserLogged", false);
//        }

    }
    //End Common

    public boolean isNetworkAvailable() {
        ConnectivityManager cm =
                (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo netInfo = cm.getActiveNetworkInfo();

        return netInfo != null && netInfo.isConnected();
    }

    public String deleteUser(String userID){
        HashMap<String, String> params = new HashMap<>();

        Log.d("DELETE USER", userID);

        params.put("PersonID", userID);

        RequestHandler requestHandler = new RequestHandler(URLs.URL_DELETE_USER, params);

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
}