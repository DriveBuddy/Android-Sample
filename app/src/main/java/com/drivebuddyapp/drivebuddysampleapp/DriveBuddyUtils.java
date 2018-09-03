package com.drivebuddyapp.drivebuddysampleapp;

import android.content.Context;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyConfiguration;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyNotification;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyOperationalCallback;

public class DriveBuddyUtils {
    static void setup(Context context, DriveBuddyOperationalCallback callback){

        DriveBuddyNotification notification = new DriveBuddyNotification(
                DriveBuddySharedPreferences.getString(context, "_notificationTitle"),
                DriveBuddySharedPreferences.getString(context, "_notificationContent"),
                DriveBuddySharedPreferences.getInt(context, "_notificationIcon")
        );

        DriveBuddyConfiguration config = new DriveBuddyConfiguration(
                DriveBuddySharedPreferences.getString(context, "_sdk-key"),
                DriveBuddySharedPreferences.getString(context, "_username"),
                DriveBuddySharedPreferences.getBoolean(context, "_drivingDetection"),
                notification,
                DriveBuddySharedPreferences.getString(context, "_firstName"),
                DriveBuddySharedPreferences.getString(context, "_surname"),
                DriveBuddySharedPreferences.getString(context, "_mail")
        );

        DriveBuddy.setup(context, config, callback);
    }
}