package com.untitledev.monitoreofamiliar.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.CompoundButton;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import com.untitledev.monitoreofamiliar.R;
import com.untitledev.untitledev_module.entities.Contact;

import java.util.List;

/**
 * Created by Cipriano on 2/17/2018.
 */

public class ContactAdapter extends BaseAdapter {
    private Context context;
    private int layout;
    private List<Contact> listContact;

    public ContactAdapter(Context context, int layout, List<Contact> listContact){
        this.context = context;
        this.layout = layout;
        this.listContact = listContact;
    }

    @Override
    public int getCount() {
        return this.listContact.size();
    }

    @Override
    public Object getItem(int position) {
        return this.listContact.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, final ViewGroup parent) {
        LayoutInflater layoutInflater = LayoutInflater.from(this.context);
        convertView = layoutInflater.inflate(this.layout, null);

        //Nos traemos el valor dependiente de la posición
        Contact currentContact = listContact.get(position);
        ImageView imageViewContact = (ImageView) convertView.findViewById(R.id.imageViewContact);
        TextView textViewContact = (TextView) convertView.findViewById(R.id.textViewContact);
        Switch switchPermission = (Switch) convertView.findViewById(R.id.switchPermission);
        //textViewContact.setText(currentContact.getId());
        textViewContact.setText(""+currentContact.getId()+" - "+currentContact.getName()+" "+currentContact.getLastName()+" - "+currentContact.getPhone());
        switchPermission.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                Toast.makeText(ContactAdapter.this.context, "Hola" + isChecked, Toast.LENGTH_SHORT).show();
            }
        });
        return convertView;
    }

    public void addItems(List<Contact> items){
        //listContact = items;
        this.notifyDataSetChanged();
        for (Contact contact: items){
            listContact.add(contact);
        }
    }

    public void removeItem(int position){
        listContact.remove(position);
        this.notifyDataSetChanged();
    }
    static class ViewHolder{
        private ImageView imageViewContact;
        private TextView textViewContact;
        private Switch switchPermission;
    }
}
