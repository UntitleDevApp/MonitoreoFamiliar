package com.untitledev.untitledev_module.httpmethods;

/**
 * Created by TesistaSDN on 22/02/2018.
 */

import android.util.Log;

import com.google.gson.Gson;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import android.util.Log;

import com.google.gson.Gson;
import com.untitledev.untitledev_module.entities.Contact;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Cipriano on 11/16/2017.
 */

public class Response {

    private String bodyString;
    private int httpCode;
    private Object bodyObject;

    public String getBodyString() {
        return bodyString;
    }

    public void setBodyString(String bodyString) {
        this.bodyString = bodyString;
    }

    public int getHttpCode() {
        return httpCode;
    }

    public void setHttpCode(int httpCode) {
        this.httpCode = httpCode;
    }

    public Object getBodyObject() {
        return bodyObject;
    }

    public void setBodyObject(Object bodyObject) {
        this.bodyObject = bodyObject;
    }


    /**
     * Convierte un json String a un JSONArray
     * @param json en tipo String
     * @return json en un JSONArray
     */
    public JSONArray parseJsonArray(String json){
        JSONArray mJsonArray = null;
        if(!json.isEmpty()) {
            try {
                mJsonArray = new JSONArray(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return mJsonArray;
    }
    public JSONObject parseJsonObject(String json){
        JSONObject jsonObject = null;
        if(!json.isEmpty()) {
            try {
                jsonObject = new JSONObject(json);
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return jsonObject;
    }


    public String convertObjectToJsonTypeString(Object arg){
        Gson gson = new Gson();
        Log.i("JSON_gson: ", gson.toJson(arg));
        return gson.toJson(arg);
    }
    public Object parseToObject(Class<?> mClass, String jsonObjectString){
        Gson gson = new Gson();
        return gson.fromJson(jsonObjectString, mClass);
    }
}

