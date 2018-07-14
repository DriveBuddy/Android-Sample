package com.drivebuddyapp.drivebuddysampleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;

public class DriveBuddyBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            DriveBuddy.setup(context, DriveBuddy.getCurrentConfiguration(context));
        }
    }
}
