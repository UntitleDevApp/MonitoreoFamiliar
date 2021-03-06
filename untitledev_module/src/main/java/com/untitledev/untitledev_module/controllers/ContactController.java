package com.untitledev.untitledev_module.controllers;

import android.content.Context;
import android.util.Log;

import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.services.impl.ContactServiceImpl;
import com.untitledev.untitledev_module.utilities.Functions;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

/**
 * Created by Cipriano on 2/19/2018.
 */

public class ContactController {

    public Response addContact(Context context, Contact contact) {
        Date date = new Date();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

            contact.setCreationDate(format.format(date));


        contact.setTblUserId(2);
        contact.setStatus(1);
        contact.setUserEnable(0);
        return new ContactServiceImpl().addContact(context, contact);
    }

    public List<Contact> listAllContacts(Context context) {
        int tblUserId = 2;
        return new ContactServiceImpl().listAllContacts(context, tblUserId);
    }
}
