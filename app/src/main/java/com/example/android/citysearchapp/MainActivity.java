package com.example.android.citysearchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.city_name_container, new CityNameFragment()).addToBackStack("CityNameFragment").commit();
        }
    }
}
