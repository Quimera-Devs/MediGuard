package com.programabit.mediguard.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.programabit.mediguard.R;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;
import com.programabit.mediguard.data.preferences.GuardCountPreference;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.domain.GuardsViewModel;
import com.programabit.mediguard.domain.MedicDto;

import java.util.concurrent.ExecutionException;

public class DashboardActivity extends BaseActivity {

    MedicRestRepositoryAsync medicRepo;
    GuardsViewModel guardsViewModel;
    TextView username;
    CardView cvMisGuardias;
    CardView cvGuardiasDispo;
    String myToken = "";
    static MedicDto myself;
    TextView tvGuardiasActivas;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        username = findViewById(R.id.tvBienvenido);
        cvMisGuardias = findViewById(R.id.cvMisGuardias);
        cvGuardiasDispo = findViewById(R.id.cvGuardiasDispo);
        tvGuardiasActivas = findViewById(R.id.tvGuardiasActivas);
        final MyGuardsListAdapter adapter = new MyGuardsListAdapter(new MyGuardsListAdapter.guardDiff());
        TokenPreference preferences = new TokenPreference(this);
        try {
            // Setear extras (token y usuario)
            Intent intent = getIntent();
            if(intent.getExtras() != null) {
                myToken = (intent.getStringExtra("data"));
                medicRepo = new MedicRestRepositoryAsync(this.getApplication(), myToken);
                try {
                    medicRepo.execute(myToken);
                    myself = medicRepo.get();
                    if(myself==null){
                        preferences.saveToken("");
                        startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
                        finish();
                    }
                } catch (ExecutionException e) {
                    Log.i("excecute exception", e.getMessage());
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    Log.i("interrup exception", e.getMessage());
                    e.printStackTrace();
                }
                if (myself != null) {
                    username.setText(getString(R.string.dashboard_welcome_dr,myself.getNombre_apellido()));
                    Log.i("dashboard","got user correctly");
                }
            }

            // ViewModel
            guardsViewModel = new ViewModelProvider(this,
                    new GuardsFactory(this.getApplication(), myToken)).get(GuardsViewModel.class);
            Log.i("dashboard","guardsViewModel created");
            guardsViewModel.getMyGuards().observe(this,
                    adapter::submitList);
            Log.i("dashboard","observing my guards");
            int guardsNum = guardsViewModel.getNumGuards();
            Log.i("guardsViewModels",""+guardsViewModel.getNumGuards());
            GuardCountPreference guardCountPreference = new GuardCountPreference(DashboardActivity.this);
            guardCountPreference.setCount(guardsViewModel.getNumGuards());
            setGuardCountMessage(guardsNum);

        } catch(Exception e) {

            preferences.saveToken("");
            clearAppData();
            Log.i("DASHBOARD","posiblemente token no existe o no valido: " + e);
            startActivity(new Intent(DashboardActivity.this,LoginActivity.class));
            finish();
        }

        // Intent a MIS GUARDIAS (Nehuen)
        cvMisGuardias.setOnClickListener(v -> {
            Log.i("Dashboard","Go to My Guards Activity");
            startActivity(new Intent(DashboardActivity.this,MyGuardsActivity.class).
                    putExtra("token",myToken));
        });

        // Intent a GUARDIAS DISPONIBLES (Javier)
        cvGuardiasDispo.setOnClickListener(v -> {
            Log.i("Dashboard","Go to Avaible Guards Activity");
            startActivity(new Intent(DashboardActivity.this,AvaibleGuardsActivity.class).
                    putExtra("token",myToken));
        });

        String TAG = "DashboardActivity";

        //redirigir a guardias disponibles o asignadas si ee intent proviene de una notificacion
        checkIfIsNotificationIntent();


        FirebaseMessaging.getInstance().subscribeToTopic(Integer.toString(myself.getCi()))
                .addOnCompleteListener(task -> {
                    String msg = getString(R.string.notification_success_msg);
                    Log.e("TOPICO CI", Integer.toString(myself.getCi()));
                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_medic_failed);
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                });

        FirebaseMessaging.getInstance().subscribeToTopic(myself.getDepartamento())
                .addOnCompleteListener(task -> {
                    String msg = (getString(R.string.registry_location) + myself.getDepartamento());
                    Log.e("TOPICO DEPARTAMAENTO", myself.getDepartamento());

                    if (!task.isSuccessful()) {
                        msg = getString(R.string.msg_subscribe_dept_failed);
                    }
                    Log.d(TAG, msg);
                    Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                });
    }

    private void checkIfIsNotificationIntent() {
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            String notificationType;
            try {
                notificationType = getIntent().getStringExtra("notif_type");
                String myToken = getIntent().getStringExtra("token");
                if (notificationType.equals("avalibe")) {
                    startActivity(new Intent(this,AvaibleGuardsActivity.class).putExtra("token",myToken));
                    onPause();
                } /*else if (notificationType.equals("assigned")) {
                    startActivity(new Intent(this,MyGuardsActivity.class).putExtra("token",myToken));
                }*/
            } catch (Exception e) {
                Log.i("Back Button exception", "No estas volviendo desde una nofiticación" + e);
                e.printStackTrace();
            }
        }
    }

    private void setGuardCountMessage(int guardsNum) {
        if (guardsNum == 1) {
            tvGuardiasActivas.setText(getString(R.string.single_asigned_guard_count,String.valueOf(guardsNum)));
        } else {
            tvGuardiasActivas.setText(getString(R.string.multiple_asigned_guard_count,String.valueOf(guardsNum)));
        }
    }

    /*We need create notification channel for the push notification, the docs says it's ok call this
    function anywhere and many times because it didn't cause overload*/
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
  
    public String getMyTokenValue() {
        return this.myToken;
    }

    public GuardsViewModel getGuardsViewModel() {
        return guardsViewModel;
    }

    @Override
    protected void onResume() {
        super.onResume();
        TokenPreference preference = new TokenPreference(this);
        GuardCountPreference guardCountPreference = new GuardCountPreference(this);
        if (preference.getToken().isEmpty()) {
            startActivity(new Intent(this, LoginActivity.class));
            finish();
        }
        setGuardCountMessage(guardCountPreference.getCount());
    }

    public static MedicDto getMyself() {
        return myself;
    }
}