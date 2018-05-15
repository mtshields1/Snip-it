package com.example.ch.snip_it;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.ListView;

import java.util.ArrayList;
import java.util.List;

/**
 * Class to select a contact for sending a snip to
 */
public class SelectContactActivity extends AppCompatActivity
{
    @Override
    protected void onCreate(Bundle savedInstance){
        super.onCreate(savedInstance);
        setContentView(R.layout.content_select_contact_info);
        setContactButtonListener();
        //getContacts();
    }

    public void setContactButtonListener(){
        Button contactButtonListener = findViewById(R.id.contactButton);
        contactButtonListener.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Context context = getApplicationContext();
                List<AndroidContact> contacts = ContactService.getContacts(context);
                List<String> names = new ArrayList<>();
                //NOTE: a stream would be much easier to do this operation, but wouldn't be supported by older devices, so doing it this way
                for (AndroidContact contact: contacts){
                    names.add(contact.getName());
                }
                AlertDialog.Builder alertDialog = new AlertDialog.Builder(SelectContactActivity.this);
                LayoutInflater inflater = getLayoutInflater();
                View convertView = (View) inflater.inflate(R.layout.contact_listview, null);
                alertDialog.setView(convertView);
                alertDialog.setTitle("Select a contact");
                ListView lv = (ListView) convertView.findViewById(R.id.listView1);
                ArrayAdapter<String> adapter = new ArrayAdapter<String>(context,android.R.layout.simple_list_item_1,names);
                lv.setAdapter(adapter);
                alertDialog.show();
            }
        });
    }
}
