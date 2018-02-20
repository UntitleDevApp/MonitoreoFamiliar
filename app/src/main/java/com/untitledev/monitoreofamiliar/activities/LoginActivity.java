package com.untitledev.monitoreofamiliar.activities;

import android.content.Context;
import android.content.Intent;
import android.provider.SyncStateContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Switch;
import android.widget.Toast;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.untitledev_module.controllers.ContactController;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.services.UsersService;
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

import org.json.JSONException;

import java.util.List;


public class LoginActivity extends AppCompatActivity{
    private Intent mIntent;
    private EditText etLoginPhone;
    private EditText etLoginPassword;
    private Switch switchRemember;
    private ApplicationPreferences appPreferences;
    private List<Contact> listContact;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        appPreferences = new ApplicationPreferences();
        bindUI();
        setCredentialsIfExist();
        switchRemember.setChecked(appPreferences.getPreferenceBoolean(this, Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_REMEMBER));
        switchRemember.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                appPreferences.saveOnPreferenceBoolean(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_REMEMBER, isChecked);
                if(isChecked){
                    Log.i("Esta chequeado: ", "true");
                }else{
                    //Se deben eliminar las preferencias Cuando se pongan en false
                    appPreferences.removeSharedPreferences(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL);
                    Log.i("No esta chequeado: ", "false");
                }
            }
        });

    }

    private void bindUI(){
        etLoginPhone = (EditText) findViewById(R.id.etLoginPhone);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
    }

    private boolean logIn(String phone, String password){
        if(!isValidPhone(phone)){
            Toast.makeText(getApplicationContext(), R.string.message_valid_phone, Toast.LENGTH_SHORT).show();
            return false;
        }else if(!isValidPassword(password)){
            Toast.makeText(getApplicationContext(), R.string.message_valid_password, Toast.LENGTH_SHORT).show();
            return false;
        }else{
            return true;
        }
    }

    private boolean isValidPhone(String phone){
        return !TextUtils.isEmpty(phone);
    }

    private boolean isValidPassword(String password){
        return !TextUtils.isEmpty(password);
    }

    private void goToHome(){
        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        startActivity(intent);
    }

    private void saveOnPreferences(String phone, String password){
        if(switchRemember.isChecked()){
            appPreferences.saveOnPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_PHONE, phone);
            appPreferences.saveOnPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_PASSWORD, password);
        }
    }

    private void setCredentialsIfExist(){
        String phone = appPreferences.getPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_PHONE);
        String password = appPreferences.getPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_PASSWORD);
        if(!TextUtils.isEmpty(phone) && !TextUtils.isEmpty(password)){
            etLoginPhone.setText(phone);
            etLoginPassword.setText(password);
        }
    }

    public void onClickLogIn(View v){
        switch(v.getId()){
            case R.id.btnLogin:
                String phone = etLoginPhone.getText().toString();
                String password = etLoginPassword.getText().toString();
                if(logIn(phone, password)){
                    UsersService uService = new UsersService(LoginActivity.this, new UsersService.UsersServiceMethods() {
                        @Override
                        public void createUser(Response response) {

                        }

                        @Override
                        public void readUser(Response response) {

                        }

                        @Override
                        public void updateUser(Response response) {

                        }

                        @Override
                        public void deleteUser(Response response) {

                        }

                        @Override
                        public void logInUser(Response response) {
                            switch (response.getHttpCode()){
                                case 200:
                                case 201:
                                    appPreferences.saveOnPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER, response.getBodyString());
                                    goToHome();
                                    break;
                                case 404:
                                    break;
                                case 400:
                                    break;
                                default:
                            }
                        }

                        @Override
                        public void logOutUser(Response response) {

                        }
                    });
                    try {
                        uService.logInUser(phone, password);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                }
                break;
            case R.id.btnSignUp:
                mIntent = new Intent(this, SignUpActivity.class);
                startActivity(mIntent);
                break;
            default:
        }
    }
}
