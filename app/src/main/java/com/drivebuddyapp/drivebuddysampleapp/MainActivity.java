package com.drivebuddyapp.drivebuddysampleapp;

import android.Manifest;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.drivebuddyapp.drivebuddysdk.DriveBuddy;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyConfiguration;
import com.drivebuddyapp.drivebuddysdk.DriveBuddyNotification;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // TOGGLE DRIVE BUTTON
        /*

        ToggleButton t = findViewById(R.id.toggleButton);
        t.setVisibility(View.INVISIBLE);
        t.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                if(isChecked){
                    DriveBuddy.startDrive(MainActivity.this);
                }else{
                    DriveBuddy.stopDrive(MainActivity.this);
                }
            }
        });
*/

        // SETUP BUTTON

        final Button button = findViewById(R.id.button);
        final EditText editText = findViewById(R.id.editText);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View view) {
                        String username = editText.getText().toString();
                        button.setVisibility(View.INVISIBLE);
                        editText.setVisibility(View.INVISIBLE);

                        DriveBuddyNotification notification = new DriveBuddyNotification("DriveBuddy", "DriveBuddy is working", R.drawable.ic_stat);
                        DriveBuddyConfiguration config = new DriveBuddyConfiguration("07aa4cd40f1aa994555a5fcba1429e7b", username, true, notification);
                        DriveBuddy.setup(MainActivity.this, config);
                    }
                });

        if(!DriveBuddy.isSdkSetup(MainActivity.this)) {
            button.setVisibility(View.INVISIBLE);
            editText.setVisibility(View.INVISIBLE);
            DriveBuddy.setup(MainActivity.this, DriveBuddy.getCurrentConfiguration(MainActivity.this));
        }

        // LOCATION PERMISSION

        int PERMISSION_ALL = 1;
        String[] PERMISSIONS = {Manifest.permission.ACCESS_COARSE_LOCATION, Manifest.permission.ACCESS_FINE_LOCATION};

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
