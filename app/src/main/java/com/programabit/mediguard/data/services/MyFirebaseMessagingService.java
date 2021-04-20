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

            switch (title) {
                case "NUEVA GUARDIA DISPONIBLE":
                    Intent toAvaibleGuards = new Intent(this, AvaibleGuardsActivity.class).putExtra("token", token);
                    TaskStackBuilder stackBuilder = TaskStackBuilder.create(this);
                    stackBuilder.addNextIntentWithParentStack(toAvaibleGuards);
                    PendingIntent resultPendingIntent =
                            stackBuilder.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    Notification notification = new NotificationCompat.Builder(this, "mediguardPush")
                            .setContentIntent(resultPendingIntent)
                            .setContentTitle(title)
                            .setContentText(place)
                            .setStyle(new NotificationCompat.BigTextStyle()
                            .bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            //.setContentIntent(pendingAlarmGuard)
                            //.addAction(R.drawable.ic_menu_send, getString(R.string.crear_alarma), pendingAlarmGuard)
                            .build();

                    NotificationManagerCompat manager = NotificationManagerCompat.from(getApplicationContext());
                    manager.notify(RandomInt(), notification);
                    return;

                case "GUARDIA ASIGNADA":
                    //Intent para que al presionar notificacion lleve al usuario a Mis Guardias
                    Intent toMyGuards = new Intent(this, MyGuardsActivity.class).putExtra("token", token);
                    TaskStackBuilder stackBuilder2 = TaskStackBuilder.create(this);
                    stackBuilder2.addNextIntentWithParentStack(toMyGuards);
                    PendingIntent toMyGuardsPendingIntent =
                            stackBuilder2.getPendingIntent(0, PendingIntent.FLAG_UPDATE_CURRENT);

                    //parsear date para ponerla en el putExtra
                    LocalDate date_parsed = LocalDate.parse(date);
                    int date_day = date_parsed.getDayOfMonth();
                    int date_month = date_parsed.getMonthValue();
                    int date_year = date_parsed.getYear();
                    assert turn != null;
                    int turn_hour = Integer.parseInt(ParseTurno(turn));

                    //Intent y pendingIntent para que al presionar "Crear Alarma" se despliegue
                    //la aplicacion calendar por defecto del dispositivo
                    Calendar beginTime = Calendar.getInstance();
                    beginTime.set(date_year,date_month,date_day,turn_hour,00);
                    Log.e("DATOS FECHA",Integer.toString(date_year) + Integer.toString(date_month) + Integer.toString(date_day) );
                    Calendar endTime = Calendar.getInstance();
                    endTime.set(date_day,date_month,date_year,turn_hour,00);

                    Intent alarmGuardIntent = new Intent(Intent.ACTION_INSERT)
                            .setData(CalendarContract.Events.CONTENT_URI)
                            .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                            //.putExtra(CalendarContract.EXTRA_EVENT_END_TIME, endTime.getTimeInMillis())
                            .putExtra(CalendarContract.Events.TITLE, place + " " + turn)
                            .putExtra(CalendarContract.Events.DESCRIPTION, body)
                            .putExtra(CalendarContract.Events.EVENT_LOCATION, place)
                            .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

                    PendingIntent pendingAlarmGuard =
                            PendingIntent.getActivity(this, 0, alarmGuardIntent, 0);

                    Notification notification2 = new NotificationCompat.Builder(this, "mediguardPush")
                            .setContentIntent(toMyGuardsPendingIntent)
                            .setContentTitle(title)
                            .setContentText(place)
                            .setStyle(new NotificationCompat.BigTextStyle()
                                    .bigText(body))
                            .setSmallIcon(R.mipmap.ic_launcher)
                            .setAutoCancel(true)
                            .setContentIntent(pendingAlarmGuard)
                            .addAction(R.drawable.ic_menu_send, getString(R.string.crear_alarma), pendingAlarmGuard)
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


    public int RandomInt() {
        int min = 1;
        int max = 9999;
        int random_int = (int) Math.floor(Math.random() * (max - min + 1) + min);
        return random_int;

    }

    public String ParseTurno(String str) {
        return str.length() < 2 ? str : str.substring(0, 2);
    }


}


