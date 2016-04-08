package com.example.android.citysearchapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * Created by User on 07-04-2016.
 */
public class CityDetailTask extends AsyncTask<Void,Void,String[]> {
    String cityId;
    String cityDetailJson =null;
    CityDetailFragment.CityDetailViewHolder viewHolder;
    public CityDetailTask(String cityId,CityDetailFragment.CityDetailViewHolder viewHolder)
    {
        Log.d("Error",cityId);
        this.viewHolder=viewHolder;
        this.cityId =cityId;
    }
    private String[] getCityDetails() throws JSONException
    {
        JSONObject jsonObject=new JSONObject(cityDetailJson);
        String[] cityDetails=new String[5];
        cityDetails[0]=jsonObject.getString("CityName");
        JSONObject jsonState=jsonObject.getJSONObject("State");
        cityDetails[1]=jsonState.getString("StateName");
        JSONObject jsonCountry=jsonObject.getJSONObject("Country");
        cityDetails[2]=jsonCountry.getString("CountryName");
        cityDetails[3]=jsonObject.getString("Latitude");
        cityDetails[4]=jsonObject.getString("Longitude");
        return cityDetails;
    }
    /**
     * Override this method to perform a computation on a background thread. The
     * specified parameters are the parameters passed to {@link #execute}
     * by the caller of this task.
     * <p/>
     * This method can call {@link #publishProgress} to publish updates
     * on the UI thread.
     *
     * @param params The parameters of the task.
     * @return A result, defined by the subclass of this task.
     * @see #onPreExecute()
     * @see #onPostExecute
     * @see #publishProgress
     */
    @Override
    protected String[] doInBackground(Void... params) {

        HttpURLConnection urlConnection=null;
        String siteUrl="http://test.maheshwari.org/services/testwebservice.asmx/GetCity";
        final String CITY_ID="cityId";
        BufferedReader reader=null;
        try {
            //request and open connection
            Uri uri=Uri.parse(siteUrl).buildUpon().appendQueryParameter(CITY_ID, cityId).build();
            URL url = new URL(uri.toString());
            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();
            //read input stream into string
            InputStream inputStream = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();
            if (inputStream == null)
                return null;
            InputStreamReader inputStreamReader = new InputStreamReader(inputStream);
            reader = new BufferedReader(inputStreamReader);
            String line;
            while ((line = reader.readLine()) != null) {
                buffer.append(line + "\n");
            }
            if (buffer.length() == 0)
                return null;
            cityDetailJson = buffer.toString();
        }
        catch (IOException e) {
            Log.d("Error ", e.toString());
            return null;
        } finally {
            if (urlConnection != null) {
                urlConnection.disconnect();
            }
            if (reader != null) {
                try {
                    reader.close();
                } catch (final IOException e) {
                    Log.d( "Error closing stream", e.toString());
                }
            }
        }
        try
        {
            return getCityDetails();
        }
        catch(Exception e)
        {
            Log.d("Error",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        if (strings != null) {
            viewHolder.cityTextview.setText(strings[0]);
            viewHolder.stateTextView.setText(strings[1]);
            viewHolder.countryTextView.setText(strings[2]);
            viewHolder.latitudeTextView.setText(strings[3]);
            viewHolder.longitudeTextView.setText(strings[4]);
        }
    }
}

