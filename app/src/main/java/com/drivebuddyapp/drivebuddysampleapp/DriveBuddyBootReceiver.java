package com.drivebuddyapp.drivebuddysampleapp;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyOperationalCallback;

public class DriveBuddyBootReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(final Context context, Intent intent) {
        if (Intent.ACTION_BOOT_COMPLETED.equals(intent.getAction())) {
            final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {
                @Override
                public void onCompletion(boolean success, String message) {
                    Toast.makeText(context, "DriveBuddy - Setup is successful.", Toast.LENGTH_SHORT).show();
                }
            };
            DriveBuddy.setup(context, DriveBuddy.getCurrentConfiguration(context), callback);
        }
    }
}
