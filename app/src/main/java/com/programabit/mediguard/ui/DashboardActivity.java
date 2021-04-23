package com.programabit.mediguard.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
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

        // Setear extras (token y usuario)
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("data"));
            medicRepo = new MedicRestRepositoryAsync(this.getApplication(), myToken);
            medicRepo.execute(new String[]{myToken});
            try {
                myself = medicRepo.get();
            } catch (ExecutionException e) {
                Log.i("excecute exception", e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.i("interrup exception", e.getMessage());
                e.printStackTrace();
            }
            if (myself != null) {
                username.setText("Bienvenido Dr." + myself.getNombre_apellido());
                Log.i("dashboard","got user correctly");
            }
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
        if (guardsNum == 1) {
            tvGuardiasActivas.setText(String.format(String.valueOf(R.string.single_asigned_guard_count),
                    guardsViewModel.getNumGuards()));
        } else {
            tvGuardiasActivas.setText(String.format(String.valueOf(R.string.multiple_asigned_guard_count),
                    guardsViewModel.getNumGuards()));
        }


        String TAG = "DashboardActivity";

        FirebaseMessaging.getInstance().subscribeToTopic(Integer.toString(myself.getCi()))
                .addOnCompleteListener(new OnCompleteListener<Void>() {

                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        String msg = String.valueOf(R.string.notification_success_msg);
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
                        String msg = (R.string.registry_location + myself.getDepartamento());
                        Log.e("TOPICO DEPARTAMAENTO", myself.getDepartamento());

                        if (!task.isSuccessful()) {
                            msg = getString(R.string.msg_subscribe_dept_failed);
                        }
                        Log.d(TAG, msg);
                        Toast.makeText(DashboardActivity.this, msg, Toast.LENGTH_SHORT).show();
                    }
                });
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
        if (guardCountPreference.getCount() == 1) {
            tvGuardiasActivas.setText(String.format(String.valueOf(R.string.single_asigned_guard_count),
                    guardCountPreference.getCount()));
        } else {
            tvGuardiasActivas.setText(String.format(String.valueOf(R.string.multiple_asigned_guard_count),
                    guardCountPreference.getCount()));
        }
    }

    public static MedicDto getMyself() {
        return myself;
    }
}