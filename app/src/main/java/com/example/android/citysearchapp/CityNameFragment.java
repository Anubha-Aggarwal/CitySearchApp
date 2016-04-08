package com.example.android.citysearchapp;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;

/**
 * Created by User on 06-04-2016.
 */
public class CityNameFragment extends Fragment {
    AutoCompleteTextView cityNametextview;
     ArrayAdapter<String> sugestAdapter;
    SuggestCityTask task;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        sugestAdapter=new ArrayAdapter<String>(getActivity(),R.layout.support_simple_spinner_dropdown_item);
       // sugestAdapter.add("anubha");
        //sugestAdapter.setNotifyOnChange(true);
       // SuggestCityTask task = new SuggestCityTask(s.toString(), sugestAdapter);
        //task.execute();
        View view=inflater.inflate(R.layout.fragment_main,container,false);
        cityNametextview=(AutoCompleteTextView)view.findViewById(R.id.city_name_edit);
        cityNametextview.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length() == 3) {
                    Log.d("Text", s.toString());
                     task = new SuggestCityTask(s.toString(), sugestAdapter);
                    task.execute();
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });
        //sugestAdapter.add("rahul");
        cityNametextview.setAdapter(sugestAdapter);
        cityNametextview.setThreshold(3);
        cityNametextview.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d("position e", position + "");
                String cityName=sugestAdapter.getItem(position);
               String cityId= task.getCityId(cityName);
                Intent intent = new Intent(getActivity(), CityDetail.class)
                        .putExtra("cityId",cityId);
                startActivity(intent);
            }
        });
        return view;
    }


}

