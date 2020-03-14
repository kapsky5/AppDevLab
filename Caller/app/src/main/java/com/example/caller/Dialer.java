package com.example.caller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class Dialer extends AppCompatActivity {

    TextView textview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dialer);
        Toast.makeText(this, "Hi Saksham", Toast.LENGTH_SHORT).show();
        textview = findViewById(R.id.textView);
        textview.setText("");
    }

    public void one(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "1");

    }

    public void two(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "2");
    }

    public void three(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "3");
    }

    public void four(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "4");
    }

    public void five(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "5");
    }

    public void six(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "6");
    }

    public void seven(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "7");
    }

    public void eight(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "8");
    }

    public void nine(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "9");
    }

    public void star(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "*");
    }

    public void hash(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "#");
    }

    public void zero(View v) {
        String a = (String) textview.getText();
        textview.setText(a + "0");
    }

    public void delete(View v) {
        String a = (String) textview.getText();
        int len = a.length();
        String b = a.substring(0, len - 1);
        textview.setText(b);
    }

    public void dial(View v) {
        Intent callIntent = new Intent(Intent.ACTION_CALL); //use ACTION_CALL class
        textview=findViewById(R.id.textView);

        callIntent.setData(Uri.parse("tel:"+textview.getText().toString().trim()));    //this is the phone number calling
        //check permission
        //If the device is running Android 6.0 (API level 23) and the app's targetSdkVersion is 23 or higher,
        //the system asks the user to grant approval.
        if (ActivityCompat.checkSelfPermission(this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
            //request permission from user if the app hasn't got the required permission
            ActivityCompat.requestPermissions(this,
                    new String[]{Manifest.permission.CALL_PHONE},   //request specific permission from user
                    10);
            return;
        }else {     //have got permission
            try{
                startActivity(callIntent);  //call activity and make phone call
            }
            catch (android.content.ActivityNotFoundException ex){
                Toast.makeText(getApplicationContext(),"yourActivity is not founded",Toast.LENGTH_SHORT).show();
            }
        }

    }

}
