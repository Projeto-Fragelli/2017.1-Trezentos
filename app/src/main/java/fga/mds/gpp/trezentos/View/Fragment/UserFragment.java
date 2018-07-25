package fga.mds.gpp.trezentos.View.Fragment;

import android.app.AlertDialog;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import org.json.JSONException;
import org.json.JSONObject;

import fga.mds.gpp.trezentos.Controller.UserAccountControl;
import fga.mds.gpp.trezentos.Exception.UserException;
import fga.mds.gpp.trezentos.R;
import fga.mds.gpp.trezentos.View.Activity.ForgotPasswordActivity;
import fga.mds.gpp.trezentos.View.Activity.SignInActivity;

import static com.facebook.FacebookSdk.getApplicationContext;

public class UserFragment extends Fragment implements View.OnClickListener{

    private ImageView exitButton;
    private Button changePasswordButton;
    private Button deleteAccountButton;

    private String userEmail;
    private String userId;
    private String userName;
    private String userTelephoneDDI;
    private String userTelephoneDDD;
    private String userTelephoneNumber;

    private TextView profileName;
    private TextView profileEmail;
    private TextView profileTelephoneNumber;
    private TextView profileTelephoneDDD;
    private TextView profileTelephoneDDI;

    private UserAccountControl userAccountControl;
    private OnFragmentInteractionListener mListener;
    private static UserFragment fragment;

    public static UserFragment getInstance() {
        if(fragment == null){
            fragment = new UserFragment();
        }
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState){

        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user, container, false);

        userAccountControl = UserAccountControl.getInstance(getApplicationContext());
        initSharedPreference();

        exitButton = view.findViewById(R.id.exit_button);
        changePasswordButton = view.findViewById(R.id.button_change_password);
        deleteAccountButton = view.findViewById(R.id.button_delete_user);

        exitButton.setOnClickListener(this);
        changePasswordButton.setOnClickListener(this);
        deleteAccountButton.setOnClickListener(this);

        profileName = view.findViewById(R.id.profile_name);
        profileEmail = view.findViewById(R.id.profile_email);
        profileTelephoneDDI = view.findViewById(R.id.profile_telephone_ddi);
        profileTelephoneDDD = view.findViewById(R.id.profile_telephone_ddd);
        profileTelephoneNumber = view.findViewById(R.id.profile_telephone_number);

        profileName.setText(userName);
        profileEmail.setText(userEmail);
        profileTelephoneDDD.setText(userTelephoneDDD);
        profileTelephoneDDI.setText(userTelephoneDDI);
        profileTelephoneNumber.setText(userTelephoneNumber);

        return view;
    }

    @Override
    public void onClick(View view){
        switch (view.getId()){
            case R.id.exit_button: {
                userAccountControl.logOutUser();
                userAccountControl.disconnectFromFacebook();
                goLoginScreen();
                break;
            }
            case R.id.button_change_password: {
                goChangePasswordScreen();
                break;
            }
            case R.id.button_delete_user: {
                confirmUserDeletion();
                break;
            }
        }
    }

    private void confirmUserDeletion() {

        new AlertDialog.Builder(getContext())
                .setTitle("Deletar usuário")
                .setMessage("Tem certeza que deseja excluir sua conta de usuário?")
                .setPositiveButton(
                        "Sim",
                        new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialogInterface, int i) {
                                String response = userAccountControl.deleteUser(userId);
                                verifyResponse(response);
                            }
                        }
                )
                .setNegativeButton(
                        "Não",
                        null
                )
                .show();

    }

    private void verifyResponse(String response){

        try {
            JSONObject obj = new JSONObject(response);

            if (!obj.getBoolean("error")) {
                Toast.makeText(getContext(), "Conta deletada com sucesso.", Toast.LENGTH_LONG).show();
                userAccountControl.logOutUser();
                userAccountControl.disconnectFromFacebook();
                goLoginScreen();

            } else {
                Toast.makeText(getContext(), "Erro ao deletar conta.", Toast.LENGTH_LONG).show();
            }
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }

    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(Uri uri);
    }

    private void goLoginScreen() {
        Intent intent = new Intent(getApplicationContext(), SignInActivity.class);

        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_CLEAR_TASK |
                Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void goChangePasswordScreen() {
        Intent intent = new Intent(getApplicationContext(), ForgotPasswordActivity.class);
        startActivity(intent);
    }


    private void initSharedPreference(){
        SharedPreferences session = PreferenceManager.getDefaultSharedPreferences(getActivity());

        userEmail = session.getString("userEmail", "");
        userName = session.getString("userFirstName", "") + " " + session.getString("userLastName", "");
        userId = session.getString("userId", "");
        userTelephoneDDI = session.getString("userTelephoneDDI", "");
        userTelephoneDDD = session.getString("userTelephoneDDD", "");
        userTelephoneNumber = session.getString("userTelephoneNumber", "");

    }
}