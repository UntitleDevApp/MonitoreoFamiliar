package com.untitledev.monitoreofamiliar.adapters;

import android.content.Context;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

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
    public View getView(int position, View convertView, ViewGroup parent) {
        //View Holder Pattern : nos permite aumentar la productividad
        ViewHolder holder;

        if(convertView == null){
            LayoutInflater layoutInflater = LayoutInflater.from(this.context);
            convertView = layoutInflater.inflate(this.layout, null);

            holder = new ViewHolder();
            //Referenciamos el elemento a modificar y lo rellenamos.
            holder.textViewContact = (TextView) convertView.findViewById(R.id.textViewContact);
            convertView.setTag(holder);
        }else{
            holder = (ViewHolder) convertView.getTag();
        }

        //Nos traemos el valor dependiente de la posición
        Contact currentContact = listContact.get(position);
        holder.textViewContact.setText(""+currentContact.getId()+" - "+currentContact.getName()+" "+currentContact.getLastName()+" - "+currentContact.getPhone());


        return convertView;
    }

    static class ViewHolder{
        private TextView textViewContact;
    }
}
