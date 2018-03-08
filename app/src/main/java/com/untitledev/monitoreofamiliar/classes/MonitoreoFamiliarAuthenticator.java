package com.untitledev.monitoreofamiliar.classes;

import android.accounts.AbstractAccountAuthenticator;
import android.accounts.Account;
import android.accounts.AccountAuthenticatorResponse;
import android.accounts.AccountManager;
import android.accounts.NetworkErrorException;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Space;

import com.untitledev.monitoreofamiliar.activities.SplashActivity;

/**
 * Created by TesistaSDN on 08/03/2018.
 */

public class MonitoreoFamiliarAuthenticator extends AbstractAccountAuthenticator {
    private Context mContext;
    public MonitoreoFamiliarAuthenticator(Context mContext) {
        super(mContext);
        this.mContext = mContext;
    }

    @Override
    public Bundle editProperties(AccountAuthenticatorResponse accountAuthenticatorResponse, String s) {
        return null;
    }

    @Override
    public Bundle addAccount(AccountAuthenticatorResponse accountAuthenticatorResponse, String s, String s1, String[] strings, Bundle bundle) throws NetworkErrorException {
        Intent intent = new Intent(mContext, SplashActivity.class);
        /*intent.putExtra(SplashActivity.ARG_ACCOUNT_TYPE, accountType);
        intent.putExtra(SplashActivity.ARG_AUTH_TYPE, authTokenType);
        intent.putExtra(SplashActivity.ARG_IS_ADDING_NEW_ACCOUNT, true);*/
        intent.putExtra(AccountManager.KEY_ACCOUNT_AUTHENTICATOR_RESPONSE, accountAuthenticatorResponse);
        //Bundle mBundle = new Bundle();
        bundle.putParcelable(AccountManager.KEY_INTENT, intent);
        return bundle;
    }

    @Override
    public Bundle confirmCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle getAuthToken(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public String getAuthTokenLabel(String s) {
        return null;
    }

    @Override
    public Bundle updateCredentials(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String s, Bundle bundle) throws NetworkErrorException {
        return null;
    }

    @Override
    public Bundle hasFeatures(AccountAuthenticatorResponse accountAuthenticatorResponse, Account account, String[] strings) throws NetworkErrorException {
        return null;
    }
}
