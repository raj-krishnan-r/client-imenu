package com.example.raj.qrtowifi;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;
import android.widget.Toast;

public class startup extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_startup);

        ActivityCompat.requestPermissions(startup.this,
                new String[]{Manifest.permission.CAMERA},
                1);

    }
    @Override
    public void onRequestPermissionsResult(int requestCode,
                                           String permissions[], int[] grantResults) {
        switch (requestCode) {
            case 1: {

                // If request is cancelled, the result arrays are empty.
                if (grantResults.length > 0
                        && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                    Intent intent = new Intent(getApplicationContext(), QrScanner.class);
                    intent.addFlags(intent.FLAG_ACTIVITY_NEW_TASK);
                    getApplicationContext().startActivity(intent);



                } else {
                    TextView tv = (TextView) findViewById(R.id.permReport) ;
                    tv.setText("i-Menu cannot proceed without permission to access CAMERA. Kindly re-start the app and grand the perission.");

                }
                return;
            }

            // other 'case' lines to check for other
            // permissions this app might request
        }
    }

}