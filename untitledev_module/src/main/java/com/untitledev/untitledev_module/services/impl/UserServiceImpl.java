package com.untitledev.untitledev_module.services.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.services.UserService;
import com.untitledev.untitledev_module.utilities.Conf;

import org.json.JSONObject;

import java.util.List;

/**
 * Created by Cipriano on 2/19/2018.
 */

public class UserServiceImpl implements UserService {

    @Override
    public Response addUser(final Context context, User user) {
        final Response responseAux = new Response();
        String url = Conf.http_host.getPropiedad()+""+Conf.http_users.getPropiedad()+""+Conf.http_users_create.getPropiedad();
        String json = responseAux.convertObjectToJsonTypeString(user);
        JSONObject jsonUser = responseAux.parseJsonObject(json);

        RequestQueue rq = Volley.newRequestQueue(context);
        JsonObjectRequest joRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonUser,
                new com.android.volley.Response.Listener<JSONObject>() { //Exitoso
                    @Override
                    public void onResponse(JSONObject response) {
                        Log.i("Response", ""+response.toString());
                        responseAux.setBodyObject(response);
                    }
                },
                new com.android.volley.Response.ErrorListener() { //Error
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        //error.networkResponse.statusCode;
                        Log.i("Response", ""+error.toString());
                    }
                }
        );
        rq.add(joRequest);
        return responseAux;
    }

    @Override
    public Response updateUser(User User) {
        return null;
    }

    @Override
    public Response findUserById(int id) {
        return null;
    }

    @Override
    public List<User> listAllUsers() {
        return null;
    }

    @Override
    public Response removeUser(int id) {
        return null;
    }
}
