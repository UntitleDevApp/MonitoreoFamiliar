package com.untitledev.monitoreofamiliar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

public class SplashActivity extends AppCompatActivity {
    private ApplicationPreferences appPreferences;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = new ApplicationPreferences();
        Intent intentHome = new Intent(this, HomeActivity.class);
        Intent intentLogin = new Intent(this, LoginActivity.class);

        if(setCredentialsIfExist() == false){
            startActivity(intentHome);
        }else{
            startActivity(intentLogin);
        }
        finish();
    }

    private boolean setCredentialsIfExist(){
        if(appPreferences.getPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER).equals("")){
            return true;
        }else{
            return false;
        }
    }
}
