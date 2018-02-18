package com.untitledev.monitoreofamiliar.fragments;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.Toast;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.monitoreofamiliar.adapters.ContactAdapter;
import com.untitledev.untitledev_module.entities.Contact;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private ListView listViewContact;
    private List<Contact> listContact;
    private Contact contact;

    public ContactFragment() {
        // Required empty public constructor
        listContact = new ArrayList<Contact>();

        contact = new Contact();
        contact.setId(1);
        contact.setName("Fernando");
        contact.setLastName("Ramirez Cipriano");
        contact.setPhone("7471054389");
        listContact.add(contact);

        contact = new Contact();
        contact.setId(2);
        contact.setName("Carlos");
        contact.setLastName("Alvarez Vazquez");
        contact.setPhone("7771045678");
        listContact.add(contact);

        contact = new Contact();
        contact.setId(3);
        contact.setName("Leon Alberne");
        contact.setLastName("Torres Restrepo");
        contact.setPhone("7771045679");
        listContact.add(contact);

        contact = new Contact();
        contact.setId(4);
        contact.setName("Alix");
        contact.setLastName("Huerta Santamaria");
        contact.setPhone("77710451909");
        listContact.add(contact);

        contact = new Contact();
        contact.setId(5);
        contact.setName("Alan Axel");
        contact.setLastName("Caspeta Gomez");
        contact.setPhone("7779098723");
        listContact.add(contact);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_contact, container, false);

        listViewContact = (ListView) view.findViewById(R.id.listViewContact);

        //Enlazamos nuestro adaptador con la vista
        ContactAdapter contactAdapter = new ContactAdapter(getContext(), R.layout.list_contact, listContact);
        listViewContact.setAdapter(contactAdapter);

        /*listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Clicked: "+listContact.get(position), Toast.LENGTH_SHORT).show();
            }
        });*/

        return  view;
    }

}
