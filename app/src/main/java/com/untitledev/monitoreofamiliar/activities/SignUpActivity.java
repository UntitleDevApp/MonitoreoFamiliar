package com.untitledev.monitoreofamiliar.activities;

import android.app.PendingIntent;
import android.content.Intent;
import android.content.IntentSender;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.credentials.Credential;
import com.google.android.gms.auth.api.credentials.CredentialPickerConfig;
import com.google.android.gms.auth.api.credentials.Credentials;
import com.google.android.gms.auth.api.credentials.CredentialsClient;
import com.google.android.gms.auth.api.credentials.HintRequest;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.GoogleApiClient;
import com.untitledev.monitoreofamiliar.R;
import com.untitledev.untitledev_module.controllers.UserController;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.services.UsersService;
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;
import com.untitledev.untitledev_module.utilities.DeviceProperties;
import com.untitledev.untitledev_module.utilities.Functions;

import org.json.JSONException;

public class SignUpActivity extends AppCompatActivity implements View.OnClickListener, GoogleApiClient.OnConnectionFailedListener {
    private static final int RESOLVE_HINT = 12 ;
    private EditText etSignUpName;
    private EditText etSignUpLastName;
    private EditText etSignUpPhone;
    private EditText etSignUpEmail;
    //private EditText etSignUpCountryCode;
    private Button btnSignUp;
    private Button btnSignUpCancel;
    private ImageButton ibPhone;
    private Intent mIntent;
    private GoogleApiClient mGoogleApiClient;
    private ApplicationPreferences appPreferences;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        bindUI();
        appPreferences = new ApplicationPreferences();
        etSignUpPhone.setText(DeviceProperties.getPhoneNumber(getApplicationContext()));
        //etSignUpCountryCode.setText(DeviceProperties.getCountryCode(getApplicationContext()));
        btnSignUp.setOnClickListener(this);
        btnSignUpCancel.setOnClickListener(this);
        ibPhone.setOnClickListener(this);
// Create a GoogleApiClient instance
        mGoogleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this /* FragmentActivity */,
                        this /* OnConnectionFailedListener */)
                .addApi(Auth.CREDENTIALS_API)
                .build();
    }

    private void bindUI(){
        etSignUpName = (EditText) findViewById(R.id.etSignUpName);
        etSignUpLastName = (EditText) findViewById(R.id.etSignUpLastName);
        etSignUpPhone = (EditText) findViewById(R.id.etSignUpPhone);
        etSignUpEmail = (EditText) findViewById(R.id.etSignUpEmail);
        //etSignUpCountryCode = (EditText) findViewById(R.id.etSignUpCountryCode);
        btnSignUp = (Button) findViewById(R.id.btnSignUp);
        btnSignUpCancel = (Button) findViewById(R.id.btnSignUpCancel);
        ibPhone = (ImageButton) findViewById(R.id.ibPhone);
    }

    private boolean isEmptyInputUser(String name, String lastName, String phone, String email){
        if((TextUtils.isEmpty(name) || name.length() == 0) || (TextUtils.isEmpty(lastName) || lastName.length() == 0) || (TextUtils.isEmpty(phone) || phone.length() == 0) || (TextUtils.isEmpty(email) || email.length() == 0)){
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

    private void addUser(String name, String lastName, final String phone, String email){
        if(isEmptyInputUser(name, lastName, phone, email) == true){
            Toast.makeText(getApplicationContext(), R.string.message_empty_input, Toast.LENGTH_SHORT).show();
        }else {
            if (!isValidEmail(email)) {
                Toast.makeText(getApplicationContext(), R.string.message_valid_email, Toast.LENGTH_SHORT).show();
            } else {
                    User user = new User();
                    user.setName(name);
                    user.setLastName(lastName);
                    user.setPhone(phone);
                    user.setEmail(email);
                    user.setStatus(1);
                    UsersService uService = new UsersService(SignUpActivity.this, new UsersService.UsersServiceMethods() {
                        @Override
                        public void createUser(Response response) {
                            switch (response.getHttpCode()){
                                case 200:
                                case 201:
                                    cleanFields();
                                    appPreferences.saveOnPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_PHONE, phone);
                                    Toast.makeText(getApplicationContext(), R.string.message_successful_registration, Toast.LENGTH_SHORT).show();
                                    break;
                                case 400:
                                    Toast.makeText(getApplicationContext(), R.string.message_error_saving, Toast.LENGTH_SHORT).show();
                                    break;
                                case 409:
                                    Toast.makeText(getApplicationContext(), R.string.message_duplicate, Toast.LENGTH_SHORT).show();
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
            }
        }
    }

    private void cleanFields(){
        etSignUpName.setText("");
        etSignUpLastName.setText("");
        //etSignUpPhone.setText(""+DeviceProperties.getPhoneNumber(getApplicationContext()));
        etSignUpEmail.setText("");
    }


    // Construct a request for phone numbers and show the picker
    private void requestHint() {
        CredentialsClient mCredentialsClient;

// ...

        mCredentialsClient = Credentials.getClient(this);


        HintRequest hintRequest = new HintRequest.Builder()
                .setPhoneNumberIdentifierSupported(true)

                .build();

        PendingIntent intent = Auth.CredentialsApi.getHintPickerIntent(mGoogleApiClient, hintRequest);
        try {
            startIntentSenderForResult(intent.getIntentSender(), RESOLVE_HINT, null, 0, 0, 0);
        } catch (IntentSender.SendIntentException e) {
            e.printStackTrace();
        }
    }

    // Obtain the phone number from the result
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RESOLVE_HINT) {
            if (resultCode == RESULT_OK) {
                Bundle extras = data.getExtras();
                Credential credential = data.getParcelableExtra(Credential.EXTRA_KEY);
                Log.i("CustomTag", "ID: " + credential.getId());
                etSignUpPhone.setText(credential.getId());
                // credential.getId();  <-- will need to process phone number string
            }
        }
    }

    @Override
    public void onClick(View v) {


        String phone = etSignUpPhone.getText().toString();
        CharSequence test = "     ";
        Log.i("CustomTag", "Empty: "+ (test.length()==0) );
        if (Functions.isEmptyString(phone)){
            Toast.makeText(getApplicationContext(), R.string.message_empty_input, Toast.LENGTH_SHORT).show();
        }else {
            switch (v.getId()) {
                case R.id.btnSignUp:
                    String name = etSignUpName.getText().toString();
                    String lastName = etSignUpLastName.getText().toString();
                    String email = etSignUpEmail.getText().toString();
                    addUser(name, lastName, phone, email);
                    break;
                case R.id.btnSignUpCancel:
                    mIntent = new Intent(getApplicationContext(), LoginActivity.class);
                    //mIntent.putExtra("phone", phone);
                    startActivity(mIntent);
                    this.finish();
                    break;

                case R.id.ibPhone:
                    requestHint();
                default:
            }
        }
    }

    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
}
