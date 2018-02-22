package com.untitledev.monitoreofamiliar.fragments;


import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.MenuInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.untitledev.monitoreofamiliar.R;
import com.untitledev.monitoreofamiliar.adapters.ContactAdapter;
import com.untitledev.untitledev_module.controllers.ContactController;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.services.ContactsService;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class ContactFragment extends Fragment {
    private ListView listViewContact;
    private List<Contact> listContact;
    private Contact contact;
    private View view;
    private ContactAdapter contactAdapter;


    //private List<String> listNames;
    //private View view;

    public ContactFragment() {
        // Required empty public constructor
        listContact = new ArrayList<Contact>();

    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        view = inflater.inflate(R.layout.fragment_contact, container, false);

        return  view;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        listViewContact = (ListView) view.findViewById(R.id.listViewContact);

        /*listViewContact.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(getContext(), "Clicked: "+listContact.get(position), Toast.LENGTH_SHORT).show();
            }
        });*/

        //Enlazamos nuestro adaptador con la vista
        contactAdapter = new ContactAdapter(getContext(), R.layout.list_contact, listContact);
        listViewContact.setAdapter(contactAdapter);
        registerForContextMenu(listViewContact);
        //Obtenemos todos los contactos de este determinado usuario
        ContactsService cService = new ContactsService(getContext(), new ContactsService.ContactsServiceMethods() {
            @Override
            public void createContact(Response response) {

            }

            @Override
            public void readContact(Response response) {
                switch (response.getHttpCode()){
                    case 200:
                    case 201:
                        try {
                            Gson gson = new Gson();
                            //listContact = new ArrayList<>();
                            JSONObject jResponse = new JSONObject(response.getBodyString());
                            JSONArray jaContacts = jResponse.getJSONArray("data");
                            Contact[] items = gson.fromJson(jaContacts.toString(), Contact[].class);

                            //for (int index = 0; index<jaContacts.length(); index++){
                                //listContact.add(gson.fromJson(jaContacts.get(index).toString(), Contact.class));
                            //}
                            contactAdapter.addItems(Arrays.asList(items));
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        break;
                    case 204:
                        //EMPTY
                        break;
                    case 404:
                        break;
                    default:
                }
            }

            @Override
            public void updateContact(Response response) {

            }

            @Override
            public void deleteContact(Response response) {

            }
        });

        try {
            String sUser = new ApplicationPreferences().getPreferenceString(getContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER);
            User mUser = (User) new Response().parseToObject(User.class, sUser);
            Contact parameters = new Contact();
            parameters.setTblUserId(mUser.getId());
            cService.readContact(parameters, null, null, null);
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        MenuInflater menuInflater = getActivity().getMenuInflater();

        AdapterView.AdapterContextMenuInfo info =  (AdapterView.AdapterContextMenuInfo) menuInfo;
        //menu.setHeaderTitle(listContact.get(info.position).getName());
        Contact  contact = (Contact) contactAdapter.getItem(info.position);
        menu.setHeaderTitle(contact.getName());
        menuInflater.inflate(R.menu.contact_map_menu, menu);
    }
}
