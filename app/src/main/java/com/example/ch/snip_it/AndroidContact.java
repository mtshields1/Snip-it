package com.example.ch.snip_it;

import android.graphics.Bitmap;

/**
 * Class for saving contact data
 */
public class AndroidContact
{
    protected String name = "";
    protected String number = "";

    public AndroidContact()
    {
        //no op
    }

    public String getName() {return name; }

    public void setName(String name)
    {
        this.name = name;
    }

    public String getPhone()
    {
        return number;
    }

    public void setPhone(String number)
    {
        this.number = number;
    }
}
