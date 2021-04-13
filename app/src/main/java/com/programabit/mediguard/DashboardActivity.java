package com.programabit.mediguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.programabit.mediguard.rest.MedicDto;
import com.programabit.mediguard.rest.MedicRestRepositoryAsync;

import java.util.concurrent.ExecutionException;

public class DashboardActivity extends AppCompatActivity {

    //MedicViewModel medicViewModel;
    MedicRestRepositoryAsync medicRepo;
    TextView username;
    String myToken = "";
    MedicDto myself;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        username = findViewById(R.id.username);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            myToken = (intent.getStringExtra("data"));
            Log.i("app token value set: ",myToken);
            medicRepo = new MedicRestRepositoryAsync(this.getApplication(),myToken);
            medicRepo.execute(new String[]{myToken});
            try {
                myself = medicRepo.get();
                Log.i("try myself","getting");
                Log.i("try myself",myself.getNombre_apellido());
            } catch (ExecutionException e) {
                Log.i("excecute exception",e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.i("interrup exception",e.getMessage());
                e.printStackTrace();
            }
            Log.i("medicRepo created","ok");

            Log.i("loged in user","aaaaaaa");
            if(myself !=null){
                //Log.i("Token test", myself.getDepartamento());
                username.setText("Welcome "+ myself.getNombre_apellido());
            }
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.activity_name_dashboard,false);

        String TAG = "DashboardActivity";
        
        FirebaseMessaging.getInstance().subscribeToTopic(Integer.toString(myself.getCi()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = ("Mediguard se ha sincronizado");
                        Log.e("TOPICO CI", Integer.toString(myself.getCi()));
                        if (!task.isSuccessful()) {
                           msg = getString(R.string.msg_subscribe_medic_failed);


                        }
                        Log.d(TAG, msg);
                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
        
        FirebaseMessaging.getInstance().subscribeToTopic(myself.getDepartamento())
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = ("Departamento de registro: " + myself.getDepartamento());
                        Log.e("TOPICO DEPARTAMAENTO", myself.getDepartamento());

                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_dept_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
    }
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
    // AppBar (toolbar y menu):
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.mSettings:
                Intent intentSettings = new Intent(this, UserSettingsActivity.class);
                startActivity(intentSettings);
                break;
            case R.id.mContact:
                Intent intentContact = new Intent(this, ContactActivity.class);
                startActivity(intentContact);
                break;
            case R.id.mAbout:
                Intent intentAbout = new Intent(this, AboutActivity.class);
                startActivity(intentAbout);
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    private void appToolbar(Toolbar toolbar, int activity_name, boolean enable) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(activity_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    public String getMyTokenValue() {
        return this.myToken;
    }
}