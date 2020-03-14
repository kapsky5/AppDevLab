package com.example.caller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.view.View;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }
    public void fun(View v)
    {
        //Toast.makeText(this,"hi Saksham",Toast.LENGTH_SHORT).show();
        Intent t=new Intent(this,Contacts.class);
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.READ_CONTACTS},   //request specific permission from user
                    1);
            if (ActivityCompat.checkSelfPermission(this, Manifest.permission.READ_CONTACTS) == PackageManager.PERMISSION_GRANTED) {

                startActivity(t);
            }
        }
        else
        {
            startActivity(t);
        }


    }
    public void makeacall(View v)
    {
        Intent t=new Intent(this,Dialer.class);
        startActivity(t);
    }
}
