package com.programabit.mediguard.data.services;

import android.app.Notification;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.TaskStackBuilder;
import android.content.Intent;
import android.os.Build;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.programabit.mediguard.R;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.ui.AvaibleGuardsActivity;
import com.programabit.mediguard.ui.MyGuardsActivity;

import java.time.LocalDate;
import java.util.Calendar;
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
            String date = dataMap.get("fecha");
            String turn = dataMap.get("turno");
            String guardID = dataMap.get("id");
            String medic = dataMap.get("medico");
            int notID = Integer.parseInt(guardID);

            switch (title) {
                case "NUEVA GUARDIA DISPONIBLE":
                    Intent toAvaibleGuards = new Intent(this, AvaibleGuardsActivity.class).putExtra("token", token);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(toAvaibleGuards);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification NewGuardNotify = new NotificationCompat.Builder(this, "mediguardPush")
                            .setContentIntent(resultPendingIntent)
                            .setContentTitle(title)
                            .setContentText(place)
                            .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .build();

                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(notID, NewGuardNotify);
                    return;

                case "GUARDIA ASIGNADA":
                    //Intent para que al presionar notificacion lleve al usuario a Mis Guardias
                    Intent toMyGuards = new Intent(this, MyGuardsActivity.class).putExtra("token", token);
                    TaskStackBuilder AsignadaStackBuilder = TaskStackBuilder.create(this);
                    AsignadaStackBuilder.addNextIntentWithParentStack(toMyGuards);
                    PendingIntent toMyGuardsPendingIntent =
                            AsignadaStackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    Intent alarmGuardIntent = new Intent(this, NotifReceiver.class)
                       .putExtra("title", title)
                       .putExtra("body", body)
                       .putExtra("place", place)
                       .putExtra("date", date)
                       .putExtra("turn", turn)
                       .putExtra("guardID", guardID)
                       .putExtra("notID", notID)
                       .putExtra("action","event_insert");

                    PendingIntent pendingAlarmGuard = PendingIntent.getBroadcast(this, 1, alarmGuardIntent, PendingIntent.FLAG_CANCEL_CURRENT );

                    Notification assignNotification = new NotificationCompat.Builder(this, "mediguardPush")
                            .setContentIntent(toMyGuardsPendingIntent)
                            .setContentTitle(title)
                            .setContentText(place)
                            .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setContentIntent(toMyGuardsPendingIntent)
                            .addAction(R.drawable.booking_confirmed, getString(R.string.crear_alarma), pendingAlarmGuard)
                            .build();

                    NotificationManagerCompat AssignManager = NotificationManagerCompat.from(getApplicationContext());
                    AssignManager.notify(notID, assignNotification);
            }
        }
    }

    @Override
    public void onNewToken(String token) {
        super.onNewToken(token);
        Log.e("newToken", token);
        Log.d("TOKEN", "Debug del nuevo token " + token);
    }




}


