package com.example.plantme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Build;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.google.firebase.messaging.FirebaseMessaging;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

import java.util.Calendar;

import pl.droidsonroids.gif.GifImageView;

public class MainActivity extends AppCompatActivity {
    TextView good;
    int current_time;
    GifImageView homeback;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        good=(TextView) findViewById(R.id.time);
        homeback=(GifImageView) findViewById(R.id.backgif);
        Calendar calendar = Calendar.getInstance();
        int hour24hrs = calendar.get(Calendar.HOUR_OF_DAY);
        current_time=hour24hrs;
        String checktime=timech(current_time);
        if(checktime.equalsIgnoreCase("night"))
        {
            good.setText("Good Night");
            homeback.setBackgroundResource(R.drawable.nitree);
        }
        else if(checktime.equalsIgnoreCase("evening"))
        {
            good.setText("Good Evening");
            homeback.setBackgroundResource(R.drawable.evetree);
        }
        else if(checktime.equalsIgnoreCase("afternoon"))
        {
            good.setText("Good Afternoon");
            homeback.setBackgroundResource(R.drawable.morntree);
        }
        else if(checktime.equalsIgnoreCase("morning"))
        {
            good.setText("Good Morning");
            homeback.setBackgroundResource(R.drawable.morntree);
        }

        createnotificationchannel();


    }

    public String timech(int time) {
        if (time>=12) {
            if (time>=16) {
                if (time>=20) {
                    return "night";
                }
                return "evening";
            } else {
                return "afternoon";
            }
        } else {
            return "morning";
        }
    }
    public void reviewclicked(View view)
    {
        Intent intent =new Intent(MainActivity.this,Review.class);
        startActivity(intent);
    }

    public void halloffame(View view)
    {
        Intent intent2 =new Intent(MainActivity.this,Halloffame.class);
        startActivity(intent2);
    }
    public void storecl(View view)
    {
        Intent intent3 =new Intent(MainActivity.this,Shop.class);
        startActivity(intent3);
    }
    public void plantmecl(View view)
    {
        Intent intent4 =new Intent(MainActivity.this,Plant.class);
        startActivity(intent4);
    }

    public void createnotificationchannel() {
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel("MyNotification", "MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager = getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
            channel.setDescription("Description");
            channel.enableVibration(true);
            manager.createNotificationChannel(channel);
            FirebaseMessaging.getInstance().subscribeToTopic("GardenCityUniversity");

        }
    }
}
