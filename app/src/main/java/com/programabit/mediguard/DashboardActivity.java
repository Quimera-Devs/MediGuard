package com.programabit.mediguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Parcelable;
import android.provider.Settings;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.messaging.FirebaseMessaging;
import com.programabit.mediguard.rest.GuardRestRepository;
import com.programabit.mediguard.rest.GuardsViewModel;
import com.programabit.mediguard.rest.MedicDto;
import com.programabit.mediguard.rest.MedicRestRepositoryAsync;

import java.util.concurrent.ExecutionException;

public class DashboardActivity extends AppCompatActivity {

    MedicRestRepositoryAsync medicRepo;
    GuardsViewModel guardsViewModel;
    TextView username;
    CardView cvMisGuardias;
    CardView cvGuardiasDispo;
    String myToken = "";
    MedicDto myself;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        createNotificationChannel();
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        username = findViewById(R.id.tvBienvenido);
        cvMisGuardias = findViewById(R.id.cvMisGuardias);
        cvGuardiasDispo = findViewById(R.id.cvGuardiasDispo);
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
            startActivity(new Intent(DashboardActivity.this,MyGuardsActivity.class).putExtra("token",myToken));
        });

        // Intent a GUARDIAS DISPONIBLES (Javier)
        cvGuardiasDispo.setOnClickListener(v -> {
            Log.i("Dashboard","Go to Avaible Guards Activity");
            startActivity(new Intent(DashboardActivity.this,AvaibleGuardsActivity.class).putExtra("token",myToken));
        });

        // ViewModel
        guardsViewModel = new ViewModelProvider(this,
                new GuardsFactory(this.getApplication(), myToken)).get(GuardsViewModel.class);
        Log.i("dashboard","guardsViewModel created");
        guardsViewModel.getMyGuards().observe(this,
                adapter::submitList);
        Log.i("dashboard","observing my guards");
        guardsViewModel.getNumGuards();
        Log.i("guardsViewModels",""+guardsViewModel.getNumGuards());

        // Toolbar
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

    // AppBar menu:
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

    // AppBar menu:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mSettings) {
            Intent intent = new Intent(this, UserSettingsActivity.class).putExtra("token",myToken);
            intent.putExtra("ci", myself.getCi());
            intent.putExtra("dir", myself.getDireccion());
            intent.putExtra("department", myself.getDepartamento());
            intent.putExtra("name", myself.getNombre_apellido());
            intent.putExtra("account_num", myself.getNroCaja());
            intent.putExtra("ranking", myself.getRanking());
            intent.putExtra("phone", myself.getTelefono());
            startActivity(intent);
        } else if (itemId == R.id.mContact) {
            startActivity(new Intent(this, ContactActivity.class));
        } else if (itemId == R.id.mAbout) {
            startActivity(new Intent(this, AboutActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

    // AppBar toolbar:
    private void appToolbar(Toolbar toolbar, int activity_name, boolean enable) {
        setSupportActionBar(toolbar);
        if(getSupportActionBar() != null) {
            getSupportActionBar().setSubtitle(activity_name);
        }
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    public String getMyTokenValue() {
        return this.myToken;
    }

    public GuardsViewModel getGuardsViewModel() {
        return guardsViewModel;
    }
}