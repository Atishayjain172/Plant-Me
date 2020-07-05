package com.example.plantme;

import android.os.AsyncTask;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

public class Weather_api extends AsyncTask<Void,Void,Void> {

    public String s="",data="";
    public String lat="",longi="";
    public String temperature="",precipitation="";
    URL url2;

    public void setcordinates(String latitude,String longitude)
    {
       lat=latitude;
       longi=longitude;
    }
    @Override
    protected Void doInBackground(Void... voids) {
        try {
            URL url=new URL("https://api.darksky.net/forecast/b0129fe2e2239ab6e6f986d53e5eb9bb/"+lat+","+longi);
            url2=new URL("https://api.eu-gb.assistant.watson.cloud.ibm.com/instances/94a4a3a5-b524-45d5-b653-33bd368bcfdf");

            HttpURLConnection httpURLConnection=(HttpURLConnection) url.openConnection();
            InputStream inputStream=httpURLConnection.getInputStream();
            BufferedReader bufferedReader=new BufferedReader(new InputStreamReader(inputStream));
            String line="";
            while (line!=null)
            {
                line=bufferedReader.readLine();
                data=data+line;
            }
            JSONObject obj = new JSONObject(data);
            JSONObject values = obj.getJSONObject("currently");
            temperature=values.getString("temperature");
            precipitation=values.getString("precipProbability");
        } catch (MalformedURLException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        super.onPostExecute(aVoid);
       Plant.temperature=temperature;
       Plant.precipitation=precipitation;
    }
}
