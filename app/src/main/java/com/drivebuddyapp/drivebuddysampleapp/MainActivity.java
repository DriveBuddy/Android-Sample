package com.drivebuddyapp.drivebuddysampleapp;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.Toast;
import android.widget.ToggleButton;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyConfiguration;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyNotification;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyOperationalCallback;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final EditText usernameText = findViewById(R.id.usernameText);
        final EditText firstNameText = findViewById(R.id.firstNameText);
        final EditText surnameText = findViewById(R.id.surnameText);
        final EditText mailText = findViewById(R.id.mailText);



        final ToggleButton toggleButton = findViewById(R.id.toggleButton);
        final Button setupButton = findViewById(R.id.setupButton);
        final CheckBox automaticDrivingDetectionCheckbox = findViewById(R.id.automaticDrivingDetectionCheckbox);
        final Button resetButton = findViewById(R.id.resetButton);

        // CALLBACKS

        final DriveBuddyOperationalCallback callback = new DriveBuddyOperationalCallback() {
            @Override
            public void onCompletion(boolean success, String message) {
                Toast.makeText(MainActivity.this, "DriveBuddy - " + message, Toast.LENGTH_SHORT).show();
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

        automaticDrivingDetectionCheckbox.setChecked(true); // Default: Automatic Drive Recognition is on
        setupButton.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String username = usernameText.getText().toString();

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
                            String firstName = firstNameText.getText().toString();
                            String surname = surnameText.getText().toString();
                            String mail = mailText.getText().toString();
                            boolean automaticDriveRecognition = automaticDrivingDetectionCheckbox.isChecked();

                            usernameText.setVisibility(View.INVISIBLE);
                            firstNameText.setVisibility(View.INVISIBLE);
                            surnameText.setVisibility(View.INVISIBLE);
                            mailText.setVisibility(View.INVISIBLE);
                            automaticDrivingDetectionCheckbox.setVisibility(View.INVISIBLE);

                            setupButton.setVisibility(View.INVISIBLE);
                            resetButton.setVisibility(View.VISIBLE);

                            DriveBuddyNotification notification = new DriveBuddyNotification("DriveBuddy",
                                    "DriveBuddy is working",
                                    R.drawable.ic_stat);
                            DriveBuddyConfiguration config = new DriveBuddyConfiguration("07aa4cd40f1aa994555a5fcba1429e7b",
                                    username,
                                    automaticDriveRecognition,
                                    notification,
                                    firstName,
                                    surname,
                                    mail);
                            DriveBuddy.setup(MainActivity.this, config, callback);
                            if (!automaticDriveRecognition) {
                                toggleButton.setVisibility(View.VISIBLE);
                            }
                        }
                    }
                });

        if(DriveBuddy.isSdkSetup(MainActivity.this)) {
            usernameText.setVisibility(View.INVISIBLE);
            firstNameText.setVisibility(View.INVISIBLE);
            surnameText.setVisibility(View.INVISIBLE);
            mailText.setVisibility(View.INVISIBLE);
            automaticDrivingDetectionCheckbox.setVisibility(View.INVISIBLE);

            setupButton.setVisibility(View.INVISIBLE);
            resetButton.setVisibility(View.VISIBLE);
            DriveBuddy.setup(MainActivity.this, DriveBuddy.getCurrentConfiguration(MainActivity.this), callback);
        }

        // LOCATION PERMISSION

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {android.Manifest.permission.ACCESS_FINE_LOCATION};

        if(!hasPermissions(PERMISSIONS)){
            ActivityCompat.requestPermissions(this, PERMISSIONS, PERMISSION_ALL);
        }

    }

    private boolean hasPermissions(String[] permissions) {
        if (permissions != null) {
            for (String permission : permissions) {
                if (ActivityCompat.checkSelfPermission(MainActivity.this, permission) != PackageManager.PERMISSION_GRANTED) {
                    return false;
                }
            }
        }
        return true;
    }
}
