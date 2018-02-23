package com.untitledev.untitledev_module.services;

import android.content.Context;

import com.google.gson.Gson;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.utilities.Conf;
import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.httpmethods.methods.MethodGET;
import com.untitledev.untitledev_module.httpmethods.methods.MethodPOST;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.MalformedURLException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by TesistaSDN on 20/02/2018.
 */

public class UsersService implements MethodPOST.MethodPOSTCallback, MethodGET.MethodGETCallback {
    private static String URL_BASE = Conf.http_users.getPropiedad();
    private final UsersServiceMethods uServiceMethods;
    private final Context context;
    private String method;
    private MethodPOST mPOST;
    private MethodGET mGET;

    @Override
    public void onMethodPOSTCallback(Response response) {
        switch (method){
            case "logInUser":
                uServiceMethods.logInUser(response);
                break;
            case "createUser":
                uServiceMethods.createUser(response);
                break;
            case "updateUser":
                uServiceMethods.updateUser(response);
                break;
            case "logOutUser":
                uServiceMethods.logOutUser(response);
                break;
            default:
        }
    }

    @Override
    public void onMethodGETCallback(Response response) {
        switch (method){
            case "readUser":
                uServiceMethods.readUser(response);
                break;
            case "deleteUser":
                uServiceMethods.deleteUser(response);
                break;
            default:

        }
    }


    public interface UsersServiceMethods{
        void createUser(Response response);
        void readUser(Response response);
        void updateUser(Response response);
        void deleteUser(Response response);
        void logInUser(Response response);
        void logOutUser(Response response);
    }

    public UsersService(Context context, UsersServiceMethods uServiceMethods) {
        this.uServiceMethods = uServiceMethods;
        this.context = context;
    }

    public void logInUser(String email, String password) throws JSONException {
        method = "logInUser";
        String URL = URL_BASE + Conf.http_users_login.getPropiedad();
        JSONObject jsonLogInUser = new JSONObject();
        jsonLogInUser.put("email", email);
        jsonLogInUser.put("password", password);
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, jsonLogInUser.toString());
    }

    public void createUser(User user) throws JSONException {
        method = "createUser";
        String URL = URL_BASE + Conf.http_users_create.getPropiedad();
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, new Gson().toJson(user));
    }
    public void readUser(User user, String page, String pagination, String orderBy ) throws UnsupportedEncodingException, JSONException {
        method = "readUser";
        String URL = URL_BASE + Conf.http_users_read.getPropiedad() + getParameters(user);
        if (!page.isEmpty())
            URL += "/page="+page;
        if (!pagination.isEmpty())
            URL += "/pagination="+pagination;
        if (!orderBy.isEmpty())
            URL += "/orderBy="+orderBy;
        mGET = new MethodGET(this);
        mGET.execute(URL);

    }
    public void updateUser(User user) throws JSONException {
        method = "updateUser";
        String URL = URL_BASE + Conf.http_users_update.getPropiedad() + "/" + user.getId();
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, new Gson().toJson(user));
    }
    public void deleteUser(User user) throws JSONException {
        method = "updateUser";
        String URL = URL_BASE + Conf.http_users_delete.getPropiedad() + "/" + user.getId();
        mGET = new MethodGET(this);
        mGET.execute(URL);
    }

    public String getParameters(User user) throws JSONException, UnsupportedEncodingException {
        String parameters = "";
        JSONObject jObject = new JSONObject(new Gson().toJson(user));
        Iterator<?> keysItr = jObject.keys();
        while (keysItr.hasNext()) {
            String key = (String) keysItr.next();
            Object value = jObject.get(key);
            if (!value.toString().isEmpty()){
                parameters += "/" + key + "=" + URLEncoder.encode(value.toString(), "UTF-8");
            }
        }
        return parameters;
    }


}
