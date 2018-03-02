package com.untitledev.monitoreofamiliar.activities;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

public class SplashActivity extends AppCompatActivity {
    private ApplicationPreferences appPreferences;
    private Intent mIntent;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        appPreferences = new ApplicationPreferences();
        if(setCredentialsIfExist())
            mIntent = new Intent(this, HomeActivity.class);
        else
            mIntent = new Intent(this, SignUpActivity.class);
        startActivity(mIntent);
        this.finish();
    }

    private boolean setCredentialsIfExist(){
        return !appPreferences.getPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER).equals("");
    }
}
