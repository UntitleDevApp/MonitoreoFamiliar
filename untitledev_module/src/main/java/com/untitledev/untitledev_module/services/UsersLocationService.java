package com.untitledev.untitledev_module.services;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.entities.UserLocation;
import com.untitledev.untitledev_module.httpmethods.*;
import com.untitledev.untitledev_module.httpmethods.Response;
import com.untitledev.untitledev_module.httpmethods.methods.MethodGET;
import com.untitledev.untitledev_module.httpmethods.methods.MethodPOST;
import com.untitledev.untitledev_module.utilities.Conf;

import org.json.JSONException;

/**
 * Created by Cipriano on 2/23/2018.
 */

public class UsersLocationService implements MethodPOST.MethodPOSTCallback, MethodGET.MethodGETCallback {
    private static String URL_BASE = Conf.http_users_locations.getPropiedad();
    private final  UsersLocationServiceMethods uLocationServiceMethods;
    private final Context context;
    private String method;
    private MethodPOST mPOST;
    private MethodGET mGET;

    @Override
    public void onMethodGETCallback(Response response) {
        switch (method){
            case "deleteUserLocation":
                uLocationServiceMethods.deleteUserLocation(response);
                break;
            default:
        }
    }

    @Override
    public void onMethodPOSTCallback(Response response) {
        switch (method){
            case "createUserLocation":
                uLocationServiceMethods.createUserLocation(response);
                break;
            case "updateUserLocation":
                uLocationServiceMethods.updateUserLocation(response);
                break;
            default:
        }
    }

    public interface UsersLocationServiceMethods{
        void createUserLocation(Response response);
        void readUserLocation(Response response);
        void updateUserLocation(Response response);
        void deleteUserLocation(Response response);
    }

    public UsersLocationService(Context context, UsersLocationService.UsersLocationServiceMethods uLocationServiceMethods) {
        this.uLocationServiceMethods = uLocationServiceMethods;
        this.context = context;
    }

    public void createUserLocation(UserLocation userLocation) throws JSONException {
        method = "createUserLocation";
        String URL = URL_BASE + Conf.http_users_locations_create.getPropiedad();
        mPOST = new MethodPOST(this);
        Log.i("Status: ", "URL: "+URL);
        Log.i("Status: ", "JSON: "+new Gson().toJson(userLocation));
        mPOST.execute(URL, new Gson().toJson(userLocation));
    }

    public void updateUserLocation(UserLocation userLocation) throws JSONException {
        method = "updateUserLocation";
        String URL = URL_BASE + Conf.http_users_locations_update.getPropiedad() + "/" + userLocation.getTblUserId();
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, new Gson().toJson(userLocation));
    }

    public void deleteUserLocation(UserLocation userLocation) throws JSONException {
        method = "deleteUserLocation";
        String URL = URL_BASE + Conf.http_users_locations_delete.getPropiedad() + "/" + userLocation.getTblUserId();
        mGET = new MethodGET(this);
        mGET.execute(URL);
    }
}
