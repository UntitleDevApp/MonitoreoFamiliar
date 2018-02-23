package com.untitledev.monitoreofamiliar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.untitledev_module.controllers.UserController;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.services.UsersService;
import com.untitledev.untitledev_module.utilities.DeviceProperties;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etSignUpName;
    private EditText etSignUpLastName;
    private EditText etSignUpPhone;
    private EditText etSignUpEmail;
    private EditText etSignUpPassword;
    private EditText etSignUpConfirmPassword;
    private EditText etSignUpCountryCode;
    private Button btnSignUp;
    private Button btnSignUpCancel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindUI();
        etSignUpPhone.setText(DeviceProperties.getPhoneNumber(this));
        etSignUpCountryCode.setText(DeviceProperties.getCountryCode(this));
        btnSignUp.setOnClickListener(this);
        btnSignUpCancel.setOnClickListener(this);

    }

    private void bindUI(){
        etSignUpName = (EditText) findViewById(R.id.etSignUpName);
        etSignUpLastName = (EditText) findViewById(R.id.etSignUpLastName);
        etSignUpPhone = (EditText) findViewById(R.id.etSignUpPhone);
        etSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        etSignUpPassword = (EditText) findViewById(R.id.etSignUpPassword);
        etSignUpConfirmPassword = (EditText) findViewById(R.id.etSignUpConfirmPassword);
        etSignUpCountryCode = (EditText) findViewById(R.id.etSignUpCountryCode);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUpCancel = (Button) findViewById(R.id.btnSignUpCancel);
    }

    private boolean isEmptyInputUser(String name, String lastName, String phone, String email, String password, String confirmPassword){
        if((TextUtils.isEmpty(name) || name.length() == 0) || (TextUtils.isEmpty(lastName) || lastName.length() == 0) || (TextUtils.isEmpty(phone) || phone.length() == 0) || (TextUtils.isEmpty(email) || email.length() == 0) || (TextUtils.isEmpty(password) || password.length() == 0) || (TextUtils.isEmpty(confirmPassword) || confirmPassword.length() == 0)){
            Log.i("Status", "true");
            return true;
        }else{
            Log.i("Status", "false");
            return false;
        }
    }
    private boolean isValidEmail(String email){
        return !TextUtils.isEmpty(email) && Patterns.EMAIL_ADDRESS.matcher(email).matches();
    }

    private boolean isValidPassword(String password, String confirmPassword){

        return password.equals(confirmPassword);
    }

    private void addUser(String name, String lastName, String phone, String email, String password, String confirmPassword){
        if(isEmptyInputUser(name, lastName, phone, email, password, confirmPassword) == true){
            Toast.makeText(getApplicationContext(), R.string.message_empty_input, Toast.LENGTH_SHORT).show();
        }else {
            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), R.string.message_valid_email, Toast.LENGTH_SHORT).show();
            } else {
                if (isValidPassword(password, confirmPassword)) {
                    User user = new User();
                    user.setName(name);
                    user.setLastName(lastName);
                    user.setPhone(phone);
                    user.setEmail(email);
                    user.setPassword(password);
                    user.setStatus(1);
                    UsersService uService = new UsersService(SignUpActivity.this, new UsersService.UsersServiceMethods() {
                        @Override
                        public void createUser(Response response) {
                            switch (response.getHttpCode()){
                                case 200:
                                case 201:
                                    cleanFields();
                                    Toast.makeText(getApplicationContext(), R.string.message_successful_registration, Toast.LENGTH_SHORT).show();
                                    break;
                                case 400:
                                    Toast.makeText(getApplicationContext(), R.string.message_error_saving, Toast.LENGTH_SHORT).show();
                                    break;
                                default:
                            }
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

                        }

                        @Override
                        public void logOutUser(Response response) {

                        }
                    });
                    try {
                        uService.createUser(user);
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }

                } else {
                    Toast.makeText(getApplicationContext(), R.string.message_valid_password, Toast.LENGTH_SHORT).show();
                }
            }
        }
    }

    private void cleanFields(){
        etSignUpName.setText("");
        etSignUpLastName.setText("");
        etSignUpPhone.setText(""+DeviceProperties.getPhoneNumber(getApplicationContext()));
        etSignUpEmail.setText("");
        etSignUpPassword.setText("");
        etSignUpConfirmPassword.setText("");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnSignUp:
                String name = etSignUpName.getText().toString();
                String lastName = etSignUpLastName.getText().toString();
                String phone = etSignUpPhone.getText().toString();
                String email= etSignUpEmail.getText().toString();
                String password = etSignUpPassword.getText().toString();
                String confirmPassword = etSignUpConfirmPassword.getText().toString();
                addUser(name, lastName, phone, email, password, confirmPassword);
                break;
            case R.id.btnSignUpCancel:
                Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
                startActivity(intent);
                break;
        }
    }
}
