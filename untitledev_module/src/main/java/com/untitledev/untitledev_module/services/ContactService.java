package com.untitledev.untitledev_module.services;

import android.content.Context;

import com.untitledev.untitledev_module.entities.Contact;

import java.util.List;

/**
 * Created by Cipriano on 2/18/2018.
 */

public interface ContactService {
    public abstract Response addContact(Context context, Contact contact);
    public abstract Response updateContact(Contact contact);
    public abstract Response findContactById(int id);
    public abstract List<Contact> listAllContacts(Context context, int tblUserId);
    public abstract Response removeContact(int id);

}
