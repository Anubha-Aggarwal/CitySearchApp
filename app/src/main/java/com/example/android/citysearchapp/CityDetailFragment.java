package com.example.android.citysearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by User on 07-04-2016.
 */
public class CityDetailFragment extends Fragment {
    public class CityDetailViewHolder {
        TextView cityTextview;
        TextView stateTextView;
        TextView countryTextView;
        TextView longitudeTextView;
        TextView latitudeTextView;
    }
    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.city_detail_fragment,container,false);
        CityDetailViewHolder viewHolder=new CityDetailViewHolder();
        viewHolder.cityTextview=(TextView)view.findViewById(R.id.city_textview);
        viewHolder.stateTextView=(TextView)view.findViewById(R.id.state_textview);
        viewHolder.countryTextView=(TextView)view.findViewById(R.id.country_textview);
        viewHolder.latitudeTextView=(TextView)view.findViewById(R.id.latitude_textview);
        viewHolder.longitudeTextView=(TextView)view.findViewById(R.id.longitude_textview);
        Button back=(Button)view.findViewById(R.id.button);
        back.setOnClickListener(new View.OnClickListener() {
            /**
             * Called when a view has been clicked.
             *
             * @param v The view that was clicked.
             */
            @Override
            public void onClick(View v) {
                getActivity().finish();
                //Toast.makeText(v.getContext(), "Hello!! button Clicked", Toast.LENGTH_SHORT).show();
            }
        });

        Intent intent = getActivity().getIntent();
        if (intent != null) {
            String cityId = intent.getStringExtra("cityId");
            CityDetailTask task=new CityDetailTask(cityId,viewHolder);
            task.execute();
        }
        return view;
    }
}
