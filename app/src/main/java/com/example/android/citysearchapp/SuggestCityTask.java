package com.example.android.citysearchapp;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.ArrayAdapter;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.HashMap;

/**
 * Created by User on 06-04-2016.
 */
public class SuggestCityTask extends AsyncTask<Void,Void,String[]> {
    String suggestString;
    String sugestCityJson=null;
     HashMap<String,String> cityId;
    ArrayAdapter<String> adp;
    public SuggestCityTask(String suggestString,ArrayAdapter<String> adp)
    {
        this.suggestString=suggestString;
        this.adp=adp;
        cityId=new HashMap<String,String>();
    }
    private String[] getSugestedCity() throws JSONException
    {
        JSONArray jsonArray=new JSONArray(sugestCityJson);
        String[] sugestedCityArray=new String[jsonArray.length()];
        for(int i=0;i<jsonArray.length();i++)
        {
            JSONObject jsonObject=jsonArray.getJSONObject(i);
            String cityName=jsonObject.getString("Title");
            sugestedCityArray[i]=cityName;
            cityId.put(cityName,jsonObject.getString("Id"));
        }
        return sugestedCityArray;
    }
    public String getCityId(String cityName)
    {
        return cityId.get(cityName);
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
        String siteUrl="http://test.maheshwari.org/services/testwebservice.asmx/SuggestCity";
        final String TRY_VALUE="tryValue";
        BufferedReader reader=null;
        try {
            //request and open connection
            Uri uri=Uri.parse(siteUrl).buildUpon().appendQueryParameter(TRY_VALUE,suggestString).build();
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
            sugestCityJson = buffer.toString();
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
            return getSugestedCity();
        }
        catch(Exception e)
        {
            Log.d("Error",e.toString());
        }
        return null;
    }

    @Override
    protected void onPostExecute(String[] strings) {
        if(strings!=null)
        {
            adp.clear();
            for(int i=0;i<strings.length;i++)
                adp.add(strings[i]);
            adp.notifyDataSetChanged();
            Log.d("Text",strings[0]);
        }
    }
}
