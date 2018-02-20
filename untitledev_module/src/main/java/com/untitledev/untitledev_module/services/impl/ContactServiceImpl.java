package com.untitledev.untitledev_module.services.impl;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.JsonObjectRequest;
import com.android.volley.toolbox.Volley;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.services.ContactService;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.utilities.Conf;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Cipriano on 2/19/2018.
 */

public class ContactServiceImpl implements ContactService {
    List<Contact> listContacts = new ArrayList<Contact>();

    @Override
    public Response addContact(final Context context, Contact contact) {
        final Response responseAux = new Response();
        String url = Conf.http_host.getPropiedad()+""+Conf.http_contacts.getPropiedad()+""+Conf.http_contacts_create.getPropiedad();
        String json = responseAux.convertObjectToJsonTypeString(contact);
        JSONObject jsonContact = responseAux.parseJsonObject(json);

        RequestQueue rq = Volley.newRequestQueue(context);
        JsonObjectRequest joRequest = new JsonObjectRequest(
                Request.Method.POST,
                url,
                jsonContact,
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
    public Response updateContact(Contact contact) {
        return null;
    }

    @Override
    public Response findContactById(int id) {
        return null;
    }

    @Override
    public List<Contact> listAllContacts(Context context, int tblUserId) {
        final Response responseAux = new Response();
        String url = Conf.http_host.getPropiedad()+""+Conf.http_contacts.getPropiedad()+""+Conf.http_contacts_read.getPropiedad()+"/tblUserId="+tblUserId;
        Log.i("URL", url);
        RequestQueue rq = Volley.newRequestQueue(context);
        JsonObjectRequest joRequest = new JsonObjectRequest(
                Request.Method.GET,
                url,
                null,
                new com.android.volley.Response.Listener<JSONObject>() {
                    @Override
                    public void onResponse(JSONObject response) {
                        try {
                            JSONArray jaContacts = response.getJSONArray("data");
                            listContacts = new ArrayList<Contact>();
                            Log.i("DATA: ", ""+response);
                            for (int index = 0; index < jaContacts.length(); index++){
                                JSONObject joContact = jaContacts.getJSONObject(index);
                                Log.i("INDEX: ", ""+joContact);
                                int id = joContact.getInt("id");
                                String name = joContact.getString("name");
                                String lastName = joContact.getString("lastName");
                                String phone = joContact.getString("phone");
                                int tblUserId = joContact.getInt("tblUserId");
                                int status = joContact.getInt("status");
                                int userEnabled = joContact.getInt("userEnabled");
                                Contact contact = new Contact();
                                contact.setId(id);
                                contact.setName(name);
                                contact.setLastName(lastName);
                                contact.setPhone(phone);
                                contact.setTblUserId(tblUserId);
                                contact.setStatus(status);
                                contact.setUserEnable(userEnabled);
                                Log.i("Response", ""+id+" "+name+" "+phone);
                                listContacts.add(contact);
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                },
                new com.android.volley.Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
                        Log.i("Response", ""+error.toString());
                    }
                }
        );
        rq.add(joRequest);
        return listContacts;
    }

    @Override
    public Response removeContact(int id) {
        return null;
    }
}

class JsonDateDeserializer implements JsonDeserializer<Date> {
    public Date deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        String s = json.getAsJsonPrimitive().getAsString();
        long l = Long.parseLong(s.substring(6, s.length() - 2));
        Date d = new Date(l);
        return d;
    }
}
