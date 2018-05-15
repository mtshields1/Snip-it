package com.example.ch.snip_it;

import android.app.Activity;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;
import android.support.v7.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.List;


public class ContactService extends AppCompatActivity
{
    protected static String previousName = "";
    protected static String previousNum = "";

    public static List<AndroidContact> getContacts(Context context){

        //-----< This arraylist will contain information for each contact >-----
        ArrayList<AndroidContact> android_contact_data = new ArrayList<AndroidContact>();

        //-----< Now get the contacts >-----
        Cursor cursor_android_contacts = null;

        //-----< This will give all contacts >-----
        cursor_android_contacts = context.getContentResolver().query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI, null, null, null, ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME + " ASC");

        if (cursor_android_contacts.getCount() > 0)
        {

            while (cursor_android_contacts.moveToNext())
            {
                //-----< create a new android contact object for retrieval >-----
                AndroidContact theContact = new AndroidContact();

                //-----< ensure that the contact has a phone number >-----
                int hasPhoneNumber = Integer.parseInt(cursor_android_contacts.getString(cursor_android_contacts.getColumnIndex(ContactsContract.Contacts.HAS_PHONE_NUMBER)));

                if (hasPhoneNumber > 0)
                {
                    //<----< retrieve and add the contact's name to the contact object >----
                    String contactDisplayName = cursor_android_contacts.getString(cursor_android_contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
                    theContact.name = contactDisplayName;

                    //----< retrieve and add the number to the contact object >-----
                    String number = cursor_android_contacts.getString(cursor_android_contacts.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
                    theContact.number = number.replaceAll("\\D+","");

                    //-----< add the contact to the contact arraylist if it wasn't added previously >-----
                    if (!contactDisplayName.equals(previousName))
                    {
                        android_contact_data.add(theContact);
                    }
                    previousName = contactDisplayName;
                    previousNum = number.replaceAll("\\D+","");
                }
            }
        }

        return android_contact_data;
    }
}
