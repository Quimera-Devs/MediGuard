package com.programabit.mediguard.data.services;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.provider.CalendarContract;
import android.util.Log;

import androidx.core.app.NotificationManagerCompat;

import java.time.LocalDate;
import java.util.Calendar;

public class NotifReceiver extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {

        String action = intent.getStringExtra("action");
        String title = intent.getStringExtra("title");
        String body = intent.getStringExtra("body");
        String place = intent.getStringExtra("place");
        String date = intent.getStringExtra("date");
        int turn = Integer.parseInt(ParseTurno(intent.getStringExtra("turn")));
        int notID = intent.getIntExtra("notID",0);

        if(action.equals("event_insert")) {

            //parsear date para ponerla en el putExtra
            LocalDate date_parsed = LocalDate.parse(date);
            int date_day = date_parsed.getDayOfMonth();
            int date_month = date_parsed.getMonthValue() - 1;
            int date_year = date_parsed.getYear();

            //Intent y pendingIntent para que al presionar "Crear Alarma" se despliegue
            //la aplicacion calendar por defecto del dispositivo
            Calendar beginTime = Calendar.getInstance();
            beginTime.set(date_year ,date_month,date_day,turn,00);
            Calendar endTime = Calendar.getInstance();
            endTime.set(date_day,date_month,date_year,turn,05);

            Intent alarmGuardIntent = new Intent(Intent.ACTION_INSERT)
                .setData(CalendarContract.Events.CONTENT_URI)
                .putExtra(CalendarContract.EXTRA_EVENT_BEGIN_TIME, beginTime.getTimeInMillis())
                .putExtra(CalendarContract.Events.TITLE, place + " " +date_day+"/"+date_month+"/"+date_year)
                .putExtra(CalendarContract.Events.DESCRIPTION, body)
                .putExtra(CalendarContract.Events.EVENT_LOCATION, place)
                .putExtra(CalendarContract.Events.AVAILABILITY, CalendarContract.Events.AVAILABILITY_BUSY);

            context.startActivity(alarmGuardIntent);
            NotificationManagerCompat.from(context).cancel(notID);
        }



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






