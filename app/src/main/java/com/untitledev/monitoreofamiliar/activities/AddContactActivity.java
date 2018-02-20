package com.untitledev.monitoreofamiliar.activities;

import android.content.Intent;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.untitledev_module.controllers.ContactController;
import com.untitledev.untitledev_module.entities.Contact;
import com.untitledev.untitledev_module.entities.User;
import com.untitledev.untitledev_module.services.ContactsService;
import com.untitledev.untitledev_module.services.Response;
import com.untitledev.untitledev_module.utilities.ApplicationPreferences;
import com.untitledev.untitledev_module.utilities.Constants;
import com.untitledev.untitledev_module.utilities.Functions;

import org.json.JSONException;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class AddContactActivity extends AppCompatActivity implements View.OnClickListener{
    private EditText etAddContactName;
    private EditText etAddContactLastName;
    private EditText etAddContactPhone;
    private ImageButton btnImageAddContactPhone;
    private Button btnAddContact;
    private Button btnAddContactCancel;

    static final int PICK_CONTACT_REQUEST = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_contact);
        //Activar flecha ir atras
       // getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        bindUI();
        btnImageAddContactPhone.setOnClickListener(this);
        btnAddContact.setOnClickListener(this);
        btnAddContactCancel.setOnClickListener(this);
    }

    private void bindUI(){
        etAddContactName = (EditText) findViewById(R.id.etAddContactName);
        etAddContactLastName = (EditText) findViewById(R.id.etAddContactLastName);
        etAddContactPhone = (EditText) findViewById(R.id.etAddContactPhone);
        btnImageAddContactPhone = (ImageButton) findViewById(R.id.btnImageAddContactPhone);
        btnAddContact = (Button) findViewById(R.id.btnAddContact);
        btnAddContactCancel = (Button) findViewById(R.id.btnAddContactCancel);
    }

    private boolean isEmptyInputContact(String phone, String name, String lastName){
        if((TextUtils.isEmpty(phone) || phone.length() == 0) || (TextUtils.isEmpty(name) || name.length() == 0) || (TextUtils.isEmpty(lastName) || lastName.length() == 0)){
            return true;
        }else{
            return false;
        }
    }

    private void addContact(String phone, String name, String lastName){
        if(isEmptyInputContact(phone, name, lastName) == true){
            Toast.makeText(getApplicationContext(), R.string.message_empty_input, Toast.LENGTH_SHORT).show();
        }else{
            String sUser = new ApplicationPreferences().getPreferenceString(getApplicationContext(), Constants.PREFERENCE_NAME_GENERAL, Constants.PREFERENCE_KEY_USER);
            User mUser = (User) new Response().parseToObject(User.class, sUser);
            Contact contact = new Contact();
            contact.setName(name);
            contact.setLastName(lastName);
            contact.setPhone(phone);
            Date date = new Date();
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            contact.setCreationDate(format.format(date));
            contact.setTblUserId(mUser.getId()); //ID
            contact.setStatus(1);
            contact.setUserEnable(0);

            ContactsService cService = new ContactsService(AddContactActivity.this, new ContactsService.ContactsServiceMethods() {
                @Override
                public void createContact(Response response) {
                    switch (response.getHttpCode()){
                        case 200:
                        case 201:
                            cleanFields();
                            Toast.makeText(getApplicationContext(), R.string.message_successful_registration, Toast.LENGTH_SHORT).show();
                            break;
                        case 400:
                            Toast.makeText(getApplicationContext(), R.string.message_error_saving, Toast.LENGTH_SHORT).show();
                            break;
                        default:
                    }
                }

                @Override
                public void readContact(Response response) {

                }

                @Override
                public void updateContact(Response response) {

                }

                @Override
                public void deleteContact(Response response) {

                }
            });
            try {
                cService.createContact(contact);
            } catch (JSONException e) {
                e.printStackTrace();
            }

            //if(new ContactController().addContact(getApplicationContext(), contact) != null){

            //}else{

            //}
        }
    }

    private void cleanFields(){
        etAddContactName.setText("");
        etAddContactLastName.setText("");
        etAddContactPhone.setText("");
    }

    private void pickContact() {
        Intent pickContactIntent = new Intent(Intent.ACTION_PICK, Uri.parse("content://contacts"));
        pickContactIntent.setType(ContactsContract.CommonDataKinds.Phone.CONTENT_TYPE); // Show user only contacts w/ phone numbers
        startActivityForResult(pickContactIntent, PICK_CONTACT_REQUEST);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btnImageAddContactPhone:
                pickContact();
                break;
            case R.id.btnAddContact:
                String name = etAddContactName.getText().toString();
                String lastName = etAddContactLastName.getText().toString();
                String phone = etAddContactPhone.getText().toString();
                addContact(phone, name, lastName);
                break;
            case R.id.btnAddContactCancel:
                Contact contact = new Contact();
                /*String stringDate = Functions.getCurrentDate();
                Log.i("String Date: ", ""+stringDate);
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                Date date = format.parse(stringDate);*/
                /*String dtStart = Functions.getCurrentDate();
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
                try {
                    Date date = format.parse(dtStart);
                    Log.i("String Date: ", ""+date);
                    contact.setCreationDate(date);
                } catch (ParseException e) {
                    e.printStackTrace();
                }*/
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        // Check which request it is that we're responding to
        if (requestCode == PICK_CONTACT_REQUEST) {
            // Make sure the request was successful
            if (resultCode == RESULT_OK) {
                // Get the URI that points to the selected contact
                Uri contactUri = data.getData();
                // We only need the NUMBER column, because there will be only one row in the result
                String[] projection = {ContactsContract.CommonDataKinds.Phone.NUMBER, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME};

                // Perform the query on the contact to get the NUMBER column
                // We don't need a selection or sort order (there's only one result for the given URI)
                // CAUTION: The query() method should be called from a separate thread to avoid blocking
                // your app's UI thread. (For simplicity of the sample, this code doesn't do that.)
                // Consider using CursorLoader to perform the query.
                Cursor cursor = getContentResolver().query(contactUri, projection, null, null, null);
                cursor.moveToFirst();


                // Retrieve the phone number from the NUMBER column
                int column = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER);
                String number = cursor.getString(column);
                int columName = cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME_PRIMARY);
                String name = cursor.getString(columName);
                etAddContactPhone.setText(number);
                etAddContactName.setText(name);
                // Do something with the phone number...
            }
        }


    }
}
