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
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

import java.util.List;


public class LoginActivity extends AppCompatActivity implements View.OnClickListener{
    private Intent mIntent;
    private EditText etLoginPhone;
    private EditText etLoginPassword;
    private Switch switchRemember;
    private Button btnLogin;
    private Button btnSignUp;
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
        btnLogin.setOnClickListener(this);

    }

    private void bindUI(){
        etLoginPhone = (EditText) findViewById(R.id.etLoginPhone);
        etLoginPassword = (EditText) findViewById(R.id.etLoginPassword);
        switchRemember = (Switch) findViewById(R.id.switchRemember);
        btnLogin = (Button) findViewById(R.id.btnLogin);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
    }

    public void signUp(View v){
        mIntent = new Intent(this, SignUpActivity.class);
        startActivity(mIntent);
    }

    private boolean login(String phone, String password){
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

    @Override
    public void onClick(View v) {
        switch(v.getId()){
            case R.id.btnLogin:
                String phone = etLoginPhone.getText().toString();
                String password = etLoginPassword.getText().toString();
                if(login(phone, password)){
                    goToHome();
                    saveOnPreferences(phone, password);
                }
                break;
            case R.id.btnSignUp:
                break;
        }
    }
}
