package com.example.android.citysearchapp;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

public class CityDetail extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_city_detail);
        if(savedInstanceState==null)
        {
            getSupportFragmentManager().beginTransaction().
                    add(R.id.city_detail_container, new CityDetailFragment()).commit();
        }
    }
}
