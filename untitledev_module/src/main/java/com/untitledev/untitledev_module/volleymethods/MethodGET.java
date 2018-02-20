package com.untitledev.untitledev_module.volleymethods;

import android.content.Context;
import android.util.Log;

import com.android.volley.Network;
import com.android.volley.NetworkResponse;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.untitledev.untitledev_module.services.Response;

import org.json.JSONObject;

/**
 * Created by TesistaSDN on 20/02/2018.
 */

public class MethodGET {
    private Response mResponse;
    private MethodGETCallback mGETCallback;
    public interface MethodGETCallback {
        void onMethodGETCallback(Response response);
    }
    public MethodGET(MethodGETCallback mGETCallback){
        this.mGETCallback = mGETCallback;
    }

    public void request(Context context, String URL) {
        Log.i("Method GET: ", URL);
        mResponse = new Response();
        RequestQueue rQueue = Volley.newRequestQueue(context);
        JsonObjectRequest jObjectRequest = new JsonObjectRequest(
                Request.Method.GET,
                URL,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        mResponse.setHttpCode(200);
                        mResponse.setBodyString(response.toString());
                        mResponse.setBodyObject(response);
                        mGETCallback.onMethodGETCallback(mResponse);
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("DATA", "error" + error);
                        NetworkResponse nResponse = error.networkResponse;
                        mResponse.setHttpCode(400);
                        mResponse.setBodyString(null);
                        mResponse.setBodyObject(null);
                        mGETCallback.onMethodGETCallback(mResponse);
                    }
                }
        );
        rQueue.add(jObjectRequest);
    }
}
