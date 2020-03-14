package com.example.caller;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;

import android.Manifest;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.net.Uri;
import android.os.Bundle;
import android.provider.ContactsContract;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;

public class Contacts extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_contacts);
        fetchContacts();
    }
    public void fetchContacts(){

        ArrayList<String > contacts=new ArrayList<>();
        ContentResolver resolver=getContentResolver();
        Uri uri= ContactsContract.CommonDataKinds.Phone.CONTENT_URI;
        String[] projection={ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,ContactsContract.CommonDataKinds.Phone.NUMBER};
        String selection=null;
        String[] selectionArgs=null;
        String sortOrder=null;
        Cursor cursor=resolver.query(uri,projection,selection,selectionArgs,sortOrder);
        while(cursor.moveToNext())
        {
            String name=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME));
            String num=cursor.getString(cursor.getColumnIndex(ContactsContract.CommonDataKinds.Phone.NUMBER));
            Log.i("Name",name+" "+num);
            contacts.add(name+"\n"+"+"+num);
        }
        ListView listview=findViewById(R.id.contacts);
        listview.setAdapter(new ArrayAdapter<>(this,android.R.layout.simple_list_item_1,contacts));
        listview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String number = ((TextView) view).getText().toString();
                int len = number.length();
                int k=0;
                for(int i=0;i<len;i++){
                    if(number.charAt(i)=='+'){
                        k=i;
                        break;
                    }
                }
                String dial1=number.substring(k+1,len);
                String dial = "tel:" + dial1;
                Intent callIntent = new Intent(Intent.ACTION_CALL, Uri.parse(dial.trim()));

                if (ActivityCompat.checkSelfPermission(Contacts.this, Manifest.permission.CALL_PHONE) != PackageManager.PERMISSION_GRANTED) {
                    //request permission from user if the app hasn't got the required permission
                    ActivityCompat.requestPermissions(Contacts.this,
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
                startActivity(callIntent);


            }

        });
    }
}
