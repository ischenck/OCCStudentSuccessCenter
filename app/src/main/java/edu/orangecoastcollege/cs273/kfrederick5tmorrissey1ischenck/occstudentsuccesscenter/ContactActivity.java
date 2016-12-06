package edu.orangecoastcollege.cs273.kfrederick5tmorrissey1ischenck.occstudentsuccesscenter;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

public class ContactActivity extends AppCompatActivity {

    private static final int CALL_PHONE_REQUEST_CODE = 111;
    private Context mContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contact);
        if (ActivityCompat.checkSelfPermission(getBaseContext(),
                android.Manifest.permission.CALL_PHONE)
                != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                    new String[]{android.Manifest.permission.CALL_PHONE},
                    CALL_PHONE_REQUEST_CODE);
            return;
        }


        findViewById(R.id.phoneNumberTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getBaseContext(),
                        android.Manifest.permission.CALL_PHONE)
                        == PackageManager.PERMISSION_GRANTED) {
                    Intent callIntent = new Intent(Intent.ACTION_CALL);
                    callIntent.setData(Uri.fromParts("tel", "7144325559", null));
                    startActivity(callIntent);
                }
            }
        });

        findViewById(R.id.addressTextView).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(ContactActivity.this, LocationsListActivity.class));
            }
        });
    }
}