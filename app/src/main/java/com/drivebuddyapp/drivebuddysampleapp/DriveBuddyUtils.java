package com.drivebuddyapp.drivebuddysampleapp;

import android.content.Context;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyConfiguration;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyNotification;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyOperationalCallback;

public class DriveBuddyUtils {
    static void setup(Context context, DriveBuddyOperationalCallback callback){

        DriveBuddyNotification notification = new DriveBuddyNotification(
                DriveBuddySharedPreferences.getString(context, "notificationTitle"),
                DriveBuddySharedPreferences.getString(context, "notificationContent"),
                DriveBuddySharedPreferences.getInt(context, "notificationIcon")
        );

        DriveBuddyConfiguration config = new DriveBuddyConfiguration(
                DriveBuddySharedPreferences.getString(context, "sdk-key"),
                DriveBuddySharedPreferences.getString(context, "username"),
                DriveBuddySharedPreferences.getBoolean(context, "drivingDetection"),
                notification,
                DriveBuddySharedPreferences.getString(context, "firstName"),
                DriveBuddySharedPreferences.getString(context, "surname"),
                DriveBuddySharedPreferences.getString(context, "mail")
        );

        DriveBuddy.setup(context, config, callback);
    }
}