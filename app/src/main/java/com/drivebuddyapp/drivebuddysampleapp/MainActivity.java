package com.drivebuddyapp.drivebuddysampleapp;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyError;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyOperationalCallback;

public class MainActivity extends AppCompatActivity {

    static String DRIVE_BUDDY_SDK_KEY = "sdk-key";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // UI COMPONENTS

        final EditText usernameText = findViewById(R.id.usernameText);
        final EditText firstNameText = findViewById(R.id.firstNameText);
        final EditText surnameText = findViewById(R.id.surnameText);
        final EditText mailText = findViewById(R.id.mailText);

        if(!DriveBuddySharedPreferences.getString(MainActivity.this, "username").isEmpty()){
            usernameText.setText(DriveBuddySharedPreferences.getString(MainActivity.this, "username"));
        }
        if(!DriveBuddySharedPreferences.getString(MainActivity.this, "firstName").isEmpty()){
            firstNameText.setText(DriveBuddySharedPreferences.getString(MainActivity.this, "firstName"));
        }
        if(!DriveBuddySharedPreferences.getString(MainActivity.this, "surname").isEmpty()){
            surnameText.setText(DriveBuddySharedPreferences.getString(MainActivity.this, "surname"));
        }
        if(!DriveBuddySharedPreferences.getString(MainActivity.this, "mail").isEmpty()){
            mailText.setText(DriveBuddySharedPreferences.getString(MainActivity.this, "mail"));
        }

        final CheckBox drivingDetectionCheckbox = findViewById(R.id.drivingDetectionCheckbox);
        final ToggleButton toggleButton = findViewById(R.id.toggleButton);
        final Button setupButton = findViewById(R.id.setupButton);
        final Button resetButton = findViewById(R.id.resetButton);

        // CALLBACKS

        final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {
            @Override
            public void onCompletion(boolean success, int errorCode) {
                Toast.makeText(MainActivity.this, "DriveBuddy - " + DriveBuddyError.getDefaultErrorMessage(errorCode), Toast.LENGTH_SHORT).show();
            }
        };

        final DriveBuddyOperationalCallback callbackForSetup = new DriveBuddyOperationalCallback() {
            @Override
            public void onCompletion(boolean success, final int errorCode) {
                if (success) {

                    runOnUiThread(new Runnable() {

                        @Override
                        public void run() {
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("DriveBuddy");
                            alertDialog.setMessage(DriveBuddyError.getDefaultErrorMessage(errorCode));
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                            usernameText.setVisibility(View.INVISIBLE);
                            firstNameText.setVisibility(View.INVISIBLE);
                            surnameText.setVisibility(View.INVISIBLE);
                            mailText.setVisibility(View.INVISIBLE);
                            drivingDetectionCheckbox.setVisibility(View.INVISIBLE);

                            setupButton.setVisibility(View.INVISIBLE);
                            resetButton.setVisibility(View.VISIBLE);
                            if (!DriveBuddy.getCurrentConfiguration(MainActivity.this).getDrivingDetection()) {
                                toggleButton.setVisibility(View.VISIBLE);
                            }
                        }
                    });

                } else {
                    AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                    alertDialog.setTitle("DriveBuddy");
                    alertDialog.setMessage(DriveBuddyError.getDefaultErrorMessage(errorCode));
                    alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog, int which) {
                                    dialog.dismiss();
                                }
                            });
                    alertDialog.show();
                }
            }
        };

        final DriveBuddyOperationalCallback callbackForIsSdkSetup = new DriveBuddyOperationalCallback() {
            @Override
            public void onCompletion(boolean success, int errorCode) {
                if (success) {
                    usernameText.setVisibility(View.INVISIBLE);
                    firstNameText.setVisibility(View.INVISIBLE);
                    surnameText.setVisibility(View.INVISIBLE);
                    mailText.setVisibility(View.INVISIBLE);
                    drivingDetectionCheckbox.setVisibility(View.INVISIBLE);

                    setupButton.setVisibility(View.INVISIBLE);
                    resetButton.setVisibility(View.VISIBLE);
                }
            }
        };

        // RESET BUTTON

        resetButton.setVisibility(View.INVISIBLE);
        resetButton.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                DriveBuddy.tearDown(MainActivity.this, callback);
                Intent intent = new Intent(MainActivity.this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            }
        });

        // TOGGLE DRIVE BUTTON

        if(DriveBuddy.getCurrentConfiguration(MainActivity.this).getDrivingDetection()){
            toggleButton.setVisibility(View.INVISIBLE);
        }
        toggleButton.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DriveBuddy.startDrive(MainActivity.this, callback);
                }
                else{
                    DriveBuddy.stopDrive(MainActivity.this, callback);
                }
            }
        });

        // SETUP BUTTON

        drivingDetectionCheckbox.setChecked(true); // Default: drivingDetection is on
        setupButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        Context context = MainActivity.this;
                        String username = usernameText.getText().toString();
                        DriveBuddySharedPreferences.setString(context, "sdk-key",DRIVE_BUDDY_SDK_KEY);
                        DriveBuddySharedPreferences.setString(context, "username",usernameText.getText().toString());
                        DriveBuddySharedPreferences.setString(context, "firstName",firstNameText.getText().toString());
                        DriveBuddySharedPreferences.setString(context, "surname",surnameText.getText().toString());
                        DriveBuddySharedPreferences.setString(context, "mail",mailText.getText().toString());
                        DriveBuddySharedPreferences.setBoolean(context, "drivingDetection", drivingDetectionCheckbox.isChecked());
                        DriveBuddySharedPreferences.setString(context, "notificationTitle", "Drivebuddy");
                        DriveBuddySharedPreferences.setString(context, "notificationContent", "DriveBuddy is Working");
                        DriveBuddySharedPreferences.setInt(context, "notificationIcon", R.drawable.ic_stat);

                        if(username.matches("") || username.isEmpty()){
                            AlertDialog alertDialog = new AlertDialog.Builder(MainActivity.this).create();
                            alertDialog.setTitle("DriveBuddy");
                            alertDialog.setMessage("Username field cannot be empty!");
                            alertDialog.setButton(AlertDialog.BUTTON_NEUTRAL, "OK",
                                    new DialogInterface.OnClickListener() {
                                        public void onClick(DialogInterface dialog, int which) {
                                            dialog.dismiss();
                                        }
                                    });
                            alertDialog.show();
                        }
                        else {
                            DriveBuddyUtils.setup(context, callbackForSetup);
                        }
                    }
                });

        // IF IT'S ALREADY SET UP

        DriveBuddy.isSdkSetup(MainActivity.this, callbackForIsSdkSetup);

        // LOCATION PERMISSION

        if (ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(MainActivity.this, android.Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(MainActivity.this, new String[]{android.Manifest.permission.ACCESS_FINE_LOCATION}, 1);
        }

    }
}
