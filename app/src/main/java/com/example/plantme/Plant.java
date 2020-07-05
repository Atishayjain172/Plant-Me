package com.example.plantme;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import pl.droidsonroids.gif.GifImageView;

public class Plant extends AppCompatActivity implements LocationListener {
    public static String temperature = "", precipitation = "";
    public int temp, prec;
    LocationManager locationManager;
    double longitude,latitude;
    GifImageView treegrow;
    int fcmptem;
    TextView t1;
    ImageView treeimg0,treeimg1,treeimg2,treeimg3,treeimg4;
    TextView treename0,treename1,treename2,treename3,treename4;
    TextView re;
    String[] name={"Arborvitae","Cedar Trees","Cryptomeria Trees","Cypress Trees","Fir Trees","Holly Trees","Juniper trees","Pine Trees","Spruce Trees","Thuja Trees","Birch Trees","Maple Trees","Willow  Trees","Oak Trees","Poplar Trees","Sycamore Trees","Crape Myrtle Trees","Dogwood Trees","Flowering Cherry Trees","Blue Spurce","Western Red cedar","Eastern Hemlock","Scots Pine","White fir","Tsuga Chinensis","Mountain Hemlock","Tsuga Sieboldii","Jack pine","Western Hemlock"};
    int[] minArray={-6,40,10,12,-26,20,-10,32,14,-12,-5,-5,-5,45,20,4,-10,-28,28,-40,11,-12,-64,-12,-29,-29,-23,-20,0};
    int[] maxArray={28,46,18,22,30,25,30,36,18,-4,28,50,22,70,35,30,0,34,45,40,11,16,40,24,38,-17,27,11};
    int result[]=new int[5];
    private static final int PERMISSION_CODE = 1000;
    String[] resultString={"","","","",""};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_plant);
        treegrow=(GifImageView) findViewById(R.id.treegrow);
        t1=(TextView) findViewById(R.id.texttreegrow);
        t1.setVisibility(View.VISIBLE);
        treegrow.setVisibility(View.VISIBLE);
        re=(TextView) findViewById(R.id.result);
        treename0=(TextView) findViewById(R.id.ttext1);
        treename1=(TextView) findViewById(R.id.ttext2);
        treename2=(TextView) findViewById(R.id.ttext3);
        treename3=(TextView) findViewById(R.id.ttext4);
        treename4=(TextView) findViewById(R.id.ttext5);
        locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
        if (checkSelfPermission(Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && checkSelfPermission(Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {
            String[] permission = {Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION};
            requestPermissions(permission, PERMISSION_CODE);
            return;
        }
        Location location = locationManager.getLastKnownLocation(locationManager.NETWORK_PROVIDER);
        onLocationChanged(location);
        preprocess();
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent=new Intent(Plant.this,MainActivity.class);
                startActivity(intent);
            }
        });
    }


    @Override
    public void onLocationChanged(Location location) {
       longitude=location.getLongitude();
       latitude=location.getLatitude();

    }

    @Override
    public void onStatusChanged(String provider, int status, Bundle extras) {

    }

    @Override
    public void onProviderEnabled(String provider) {

    }

    @Override
    public void onProviderDisabled(String provider) {

    }

    public void preprocess()
    {
        t1.setText("Getting data");
        Weather_api api = new Weather_api();
        String lat,longi;
        lat=latitude+"";
        longi=longitude+"";
        api.setcordinates(lat,longi);
        api.execute();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               t1.setText("Pre Processing collected data");
               postprocess();
            }
        }, 4000);


    }
    public void postprocess()
    {
        float cmptem,cmppreci;
        cmptem=Float.parseFloat(temperature);
        cmppreci=Float.parseFloat(precipitation);
        fcmptem=(int) cmptem;
        fcmptem= (int) ((int)(fcmptem-32)*(0.555));
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               t1.setText("Post Processing collected data");
            }
        }, 8000);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
               t1.setVisibility(View.GONE);
               treegrow.setVisibility(View.GONE);
                comapare();
            }
        }, 12000);

    }
    public void comapare()
    {
        int z=0;
        for (int i=0;i<20;i++)
        {
            if(fcmptem>minArray[i]&&fcmptem<maxArray[i])
            {
                result[z]=i;
                z++;
            }
            if(z==5)
            {
                break;
            }
        }
        for (int i=0;i<5;i++)
        {
            int zi=result[i];
            resultString[i]=name[zi];
        }
        treename0.setText(resultString[0]);
        treename1.setText(resultString[1]);
        treename2.setText(resultString[2]);
        treename3.setText(resultString[3]);
        treename4.setText(resultString[4]);
        treename0.setVisibility(View.VISIBLE);
        treename1.setVisibility(View.VISIBLE);
        treename2.setVisibility(View.VISIBLE);
        treename3.setVisibility(View.VISIBLE);
        treename4.setVisibility(View.VISIBLE);
        re.setVisibility(View.VISIBLE);


    }
}
