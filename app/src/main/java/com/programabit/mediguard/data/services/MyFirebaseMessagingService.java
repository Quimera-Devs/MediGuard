package com.programabit.mediguard.data.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.programabit.mediguard.R;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.ui.AvaibleGuardsActivity;
import com.programabit.mediguard.ui.MyGuardsActivity;

import java.util.Map;

public class MyFirebaseMessagingService extends FirebaseMessagingService {
    private static final String TAG = "MyFirebaseMsgService";

    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("mediguardPush", "MediGuard Notifications", importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);

        }
    }

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        super.onMessageReceived(remoteMessage);
        createNotificationChannel();
        TokenPreference preference = new TokenPreference(this);
        String token = preference.getToken();


        if (remoteMessage.getData().size() > 0) {
            Log.d(TAG, "Message data payload: " + remoteMessage.getData());
            Map<String, String> dataMap = remoteMessage.getData();
            String title = dataMap.get("title");
            String body = dataMap.get("body");
            String place = dataMap.get("place");
            //String otherdatavalue = dataMap.get("otherdatakey");
            switch (title){
                case "NUEVA GUARDIA DISPONIBLE":
                    Intent toAvaibleGuards = new Intent(this, AvaibleGuardsActivity.class).putExtra("token",token);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(toAvaibleGuards);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification = new NotificationCompat.Builder(this,"mediguardPush")
                        .setContentIntent(resultPendingIntent)
                        .setContentTitle(title)
                        .setContentText(place)
                        .setStyle(new NotificationCompat.BigTextStyle()
                        .bigText(body))
                        .setSmallIcon(R.mipmap.ic_launcher)
                        .setAutoCancel(true)
                        .build();

                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(RandomInt(), notification);
                    return;

                case "GUARDIA ASIGNADA":
                    Intent toMyGuards = new Intent(this, MyGuardsActivity.class).putExtra("token",token);
                    TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(this);
                    stackBuilder2.addNextIntentWithParentStack(toMyGuards);
                    PendingIntent resultPendingIntent2 =
                            stackBuilder2.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification2 = new NotificationCompat.Builder(this,"mediguardPush")
                            .setContentIntent(resultPendingIntent2)
                            .setContentTitle(title)
                            .setContentText(place)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .build();

                    NotificationManagerCompat manager2 = NotificationManagerCompat.from(getApplicationContext());
                    manager2.notify(RandomInt(), notification2);
            }
            }
        }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        Log.d("TOKEN", "Debug del nuevo token " + token);
    }


    public int RandomInt(){
        int min = 1;
        int max = 9999;
        int random_int = (int)Math.floor(Math.random()*(max-min+1)+min);
        return random_int;

    }

}


