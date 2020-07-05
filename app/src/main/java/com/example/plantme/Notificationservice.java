package com.example.plantme;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;

import androidx.core.app.NotificationCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class Notificationservice extends FirebaseMessagingService {
    public static int Notification_id=1;
    public static final String NOTIFICATION_CHANNEL_ID = "4565";

    public void createNotificationChannel(String channelname,String channeldes) {
        // Create the NotificationChannel, but only on API 26+ because
        // the NotificationChannel class is new and not in the support library
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = channelname;
            String description = channeldes;
            int importance = NotificationManager.IMPORTANCE_DEFAULT;
            NotificationChannel channel = new NotificationChannel(NOTIFICATION_CHANNEL_ID, name, importance);
            channel.setDescription(description);
            // Register the channel with the system; you can't change the importance
            // or other notification behaviors after this
            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }


    public void onMessageReceived(RemoteMessage remoteMessage) {
        if (remoteMessage.getNotification() != null) {
            createNotificationChannel(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
            generateNotification(remoteMessage.getNotification().getBody(),remoteMessage.getNotification().getTitle());
        }


    }

    private void generateNotification(String body, String title) {
        Intent intent = new Intent(this,MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent=PendingIntent.getActivity(this,0,intent,PendingIntent.FLAG_ONE_SHOT);
        Uri soundUri= RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION);

        if(Build.VERSION.SDK_INT>Build.VERSION_CODES.O)
        {
            NotificationChannel channel = new NotificationChannel("MyNotification","MyNotification", NotificationManager.IMPORTANCE_DEFAULT);
            NotificationManager manager=getSystemService(NotificationManager.class);
            manager.createNotificationChannel(channel);
        }
        if(Notification_id>1073741824)
        {
            Notification_id=0;
        }


        final NotificationCompat.Builder notificationbuilder = new NotificationCompat.Builder(this,"MyNotification");
        notificationbuilder.setContentTitle(title);
        notificationbuilder.setStyle(new NotificationCompat.BigPictureStyle());
        notificationbuilder.setContentText(body);
        notificationbuilder.setAutoCancel(true);
        notificationbuilder.setSound(soundUri);
        notificationbuilder.setContentIntent(pendingIntent);
        NotificationManager notificationManager=(NotificationManager) getSystemService(NOTIFICATION_SERVICE);
        notificationManager.notify(Notification_id,notificationbuilder.build());







    }

}
