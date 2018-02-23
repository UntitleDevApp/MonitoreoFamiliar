package com.untitledev.untitledev_module.services;

import android.content.Context;

import com.google.gson.Gson;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.utilities.Conf;
import com.untitledev.untitledev_module.httpmethods.methods.MethodGET;
import com.untitledev.untitledev_module.httpmethods.methods.MethodPOST;
import com.untitledev.untitledev_module.httpmethods.Response;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Iterator;

/**
 * Created by TesistaSDN on 20/02/2018.
 */

public class ContactsService implements MethodPOST.MethodPOSTCallback, MethodGET.MethodGETCallback {
    private static String URL_BASE = Conf.http_contacts.getPropiedad();
    private final ContactsServiceMethods cServiceMethods;
    private final Context context;
    private String method;
    private MethodPOST mPOST;
    private MethodGET mGET;

    @Override
    public void onMethodPOSTCallback(Response response) {
        switch (method){
            case "createContact":
                cServiceMethods.createContact(response);
                break;
            case "updateContact":
                cServiceMethods.updateContact(response);
                break;
            default:
        }
    }

    @Override
    public void onMethodGETCallback(Response response) {
        switch (method){
            case "readContact":
                cServiceMethods.readContact(response);
                break;
            case "deleteUser":
                cServiceMethods.deleteContact(response);
                break;
            default:

        }
    }

    public interface ContactsServiceMethods{
        void createContact(Response response);
        void readContact(Response response);
        void updateContact(Response response);
        void deleteContact(Response response);
    }

    public ContactsService(Context context, ContactsServiceMethods cServiceMethods) {
        this.cServiceMethods = cServiceMethods;
        this.context = context;
    }



    public void createContact(Contact contact) throws JSONException {
        method = "createContact";
        String URL = URL_BASE + Conf.http_contacts_create.getPropiedad();
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, new Gson().toJson(contact));
    }
    public void readContact(Contact contact, String page, String pagination, String orderBy ) throws UnsupportedEncodingException, JSONException {
        method = "readContact";
        String URL = URL_BASE + Conf.http_contacts_read.getPropiedad() + getParameters(contact);
        if (page!=null && !page.isEmpty())
            URL += "/page="+page;
        if (pagination!=null && !pagination.isEmpty())
            URL += "/pagination="+pagination;
        if (orderBy!=null && !orderBy.isEmpty())
            URL += "/orderBy="+orderBy;
        mGET = new MethodGET(this);
        mGET.execute(URL);

    }
    public void updateContact(Contact contact) throws JSONException {
        method = "updateContact";
        String URL = URL_BASE + Conf.http_contacts_update.getPropiedad() + "/" + contact.getId();
        mPOST = new MethodPOST(this);
        mPOST.execute(URL, new Gson().toJson(contact));
    }
    public void deleteContact(Contact contact) throws JSONException {
        method = "updateContact";
        String URL = URL_BASE + Conf.http_contacts_delete.getPropiedad() + "/" + contact.getId();
        mGET = new MethodGET(this);
        mGET.execute(URL);
    }

    public String getParameters(Contact contact) throws JSONException, UnsupportedEncodingException {
        String parameters = "";
        JSONObject jObject = new JSONObject(new Gson().toJson(contact));
        Iterator<?> iKeys = jObject.keys();
        while (iKeys.hasNext()) {
            String key = (String) iKeys.next();
            Object value = jObject.get(key);
            if (value!=null && !value.toString().isEmpty() && !value.toString().equalsIgnoreCase("0")){
                parameters += "/" + key + "=" + URLEncoder.encode(value.toString(), "UTF-8");
            }
        }
        return parameters;
    }
}


