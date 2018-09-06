package com.drivebuddyapp.drivebuddysampleapp;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class DriveBuddySharedPreferences {

    static void setString(Context context, String key, String value){
        context = context.getApplicationContext();
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putString(key, value);
        e.apply();
    }

    static String getString(Context context, String key){
        context = context.getApplicationContext();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return p.getString(key, "");
    }

    static void setBoolean(Context context, String key, boolean value){
        context = context.getApplicationContext();
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putBoolean(key, value);
        e.apply();
    }

    static boolean getBoolean(Context context, String key){
        context = context.getApplicationContext();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return p.getBoolean(key, true);
    }

    static void setInt(Context context, String key, int value){
        context = context.getApplicationContext();
        SharedPreferences.Editor e = PreferenceManager.getDefaultSharedPreferences(context).edit();
        e.putInt(key, value);
        e.apply();
    }

    static int getInt(Context context, String key){
        context = context.getApplicationContext();
        SharedPreferences p = PreferenceManager.getDefaultSharedPreferences(context);
        return p.getInt(key, 0);
    }

}

