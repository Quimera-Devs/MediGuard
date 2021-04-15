package com.programabit.mediguard;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.media.RingtoneManager;
import android.net.Uri;
import android.os.Build;
import android.util.Log;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

import java.util.Map;
import java.util.Random;
public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";



    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotificationChannel();


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> dataMap = remoteMessage.getData();
            String title = dataMap.get("title");
            String body = dataMap.get("body");
            //String otherdatavalue = dataMap.get("otherdatakey");
            Notification notification = new NotificationCompat
                    .Builder(this,"mediguardPush")
                .setContentTitle(title)
                .setContentText(body)
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();

            NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
            manager.notify(RandomInt(), notification);
        }

        /*if (remoteMessage.getNotification() != null) {
            Log.d(TAG, "Message Notification Body: " + remoteMessage.getNotification().getBody());
        }
        Notification notification = new NotificationCompat.Builder(this,"mediguardPush")
                .setContentTitle(remoteMessage.getNotification().getTitle())
                .setContentText(remoteMessage.getNotification().getBody())
                .setSmallIcon(R.mipmap.ic_launcher)
                .build();*/


    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        Log.d("TOKEN", "Debug del nuevo token " + token);
    }

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel =
                    new NotificationChannel("mediguardPush", "MediGuard Notifications", importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    public int RandomInt(){
        int min = 1;
        int max = 9999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;

    }

}


