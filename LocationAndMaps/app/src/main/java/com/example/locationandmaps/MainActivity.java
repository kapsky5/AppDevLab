package com.example.locationandmaps;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


    }

    public void openMap(View view) {
        Intent OpenMaps = new Intent(getApplicationContext(), MapsActivity.class);
        startActivity(OpenMaps);
    }
}
