package com.example.android.greekart1;

import android.content.Context;
import android.content.SharedPreferences;
import android.util.Log;

import java.util.HashSet;
import java.util.Set;

import static android.R.attr.id;

/**
 * Created by SruthiGanesh on 6/8/2017.
 */

public class SharedPreference {
    SharedPreferences sharedPreferences ;
    String email = null;
    SharedPreferences.Editor editor;

    SharedPreference(Context context)
    {
        sharedPreferences = context.getSharedPreferences("Id", context.MODE_PRIVATE);
         editor = sharedPreferences.edit();
        editor.clear();
        email = getSharedPreference();


    }

    public void putSharedPreference(String email )
    {

        this.email = email;
        Log.v("Shared Preference",email);
        editor.putString("Id",email);
        editor.apply();

    }

    public String getSharedPreference()
    {
        String id = sharedPreferences.getString("Id",null);
        if(id==null)
        {
            Log.e("Shared Preference","set is null");
                    return null;
        }
        return id;
    }

    public void clearSharedPreference()
    {
        editor.clear();
        email = null;
    }
}
