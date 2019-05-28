package com.drivebuddyapp.drivebuddysampleapp;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.support.annotation.NonNull;
import android.support.v4.app.NotificationCompat;

import com.drivebuddyapp.drivebuddysdk.DriveBuddyNotificationProvider;

public class DriveBuddyNotificationProviderUtility implements DriveBuddyNotificationProvider {

    private static final String LOW_PRIORITY_CHANNEL = "Foreground";

    @NonNull
    @Override
    public Notification getUploadingNotificationContainer(@NonNull Context context) {
        createNotificationChannel(context);
        return new NotificationCompat.Builder(context, LOW_PRIORITY_CHANNEL)
                .setPriority(-2)
                .setContentTitle(DriveBuddySharedPreferences.getString(context, "notificationTitle"))
                .setContentText("DriveBuddy is uploading some files.")
                .setSmallIcon(DriveBuddySharedPreferences.getInt(context, "notificationIcon"))
                .setContentIntent(getNotificationClickIntent(context))
                .build();
    }

    @NonNull
    @Override
    public Notification getInDriveNotificationContainer(@NonNull Context context) {
        createNotificationChannel(context);
        return new NotificationCompat.Builder(context, LOW_PRIORITY_CHANNEL)
                .setPriority(-2)
                .setContentTitle(DriveBuddySharedPreferences.getString(context, "notificationTitle"))
                .setContentText("Have a good trip.")
                .setSmallIcon(DriveBuddySharedPreferences.getInt(context, "notificationIcon"))
                .setContentIntent(getNotificationClickIntent(context))
                .build();
    }

    private static void createNotificationChannel(Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            NotificationManager manager = context.getSystemService(NotificationManager.class);
            NotificationChannel lowPriorityNotificationChannel = new NotificationChannel(LOW_PRIORITY_CHANNEL,
                    "Drive Buddy Foreground Service",
                    NotificationManager.IMPORTANCE_MIN);
            lowPriorityNotificationChannel.setShowBadge(false);
            manager.createNotificationChannel(lowPriorityNotificationChannel);
        }
    }

    private static PendingIntent getNotificationClickIntent(Context context) {
        Intent notificationIntent = new Intent(context.getApplicationContext(), MainActivity.class);
        notificationIntent.setFlags(Intent.FLAG_ACTIVITY_SINGLE_TOP);
        return PendingIntent.getActivity(context.getApplicationContext(), 0,
                notificationIntent, 0);
    }
}
