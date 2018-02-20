package com.untitledev.untitledev_module.volleymethods;

import android.content.Context;
import android.util.Log;

import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.services.UserService;
import com.untitledev.untitledev_module.services.UsersService;

import org.json.JSONObject;

/**
 * Created by TesistaSDN on 20/02/2018.
 */

public class MethodPOST {
    private Response mResponse;
    private MethodPOSTCallback mPOSTCallback;
    public interface MethodPOSTCallback
    {
        void onMethodPOSTCallback(Response response);
    }
    public MethodPOST(MethodPOSTCallback mPOSTCallback){
        this.mPOSTCallback = mPOSTCallback;
    }

    public void request(Context context, String URL, JSONObject jsonObject){
        Log.i("Method POST", jsonObject.toString());
        mResponse = new Response();
        RequestQueue rQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jObjectRequest = new JsonObjectRequest(
                Request.Method.POST,
                URL,
                jsonObject,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResponse.setHttpCode(201);
                        mResponse.setBodyString(response.toString());
                        mResponse.setBodyObject(response);
                        mPOSTCallback.onMethodPOSTCallback(mResponse);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        NetworkResponse nResponse = error.networkResponse;
                        mResponse.setHttpCode(400);
                        mResponse.setBodyString(null);
                        mResponse.setBodyObject(null);
                        mPOSTCallback.onMethodPOSTCallback(mResponse);
                    }
                }
        );
        rQueue.add(jObjectRequest);
    }

}
