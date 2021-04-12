package com.programabit.mediguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.cardview.widget.CardView;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.programabit.mediguard.rest.GuardRestRepository;
import com.programabit.mediguard.rest.GuardsViewModel;
import com.programabit.mediguard.rest.MedicDto;
import com.programabit.mediguard.rest.MedicRestRepositoryAsync;

import java.util.concurrent.ExecutionException;

public class DashboardActivity extends AppCompatActivity {
    //MedicViewModel medicViewModel;
    MedicRestRepositoryAsync medicRepo;
    GuardsViewModel guardsViewModel;
    TextView username;
    CardView cvMisGuardias;
    CardView cvGuardiasDispo;
    String myToken = "";
    MedicDto myself;
    Button myButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        username = findViewById(R.id.tvBienvenido);
        cvMisGuardias = findViewById(R.id.cvMisGuardias);
        cvGuardiasDispo = findViewById(R.id.cvGuardiasDispo);
        myButton = findViewById(R.id.button);
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
        cvMisGuardias.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Dashboard","Go to My Guards Activity");
                startActivity(new Intent(DashboardActivity.this,MyGuardsActivity.class).putExtra("data",myToken));
            }
        });

        // Button MIS GUARDIAS (Nehuen)
        myButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Dashboard","Go to My Guards Activity");
                startActivity(new Intent(DashboardActivity.this,MyGuardsActivity.class).putExtra("data",myToken));
            }
        });

        // Intent a GUARDIAS DISPONIBLES (Javier)
        cvGuardiasDispo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.i("Dashboard","Go to Avaible Guards Activity");
                startActivity(new Intent(DashboardActivity.this,AvaibleGuardsActivity.class).putExtra("data",myToken));
            }
        });

        // Button GUARDIAS DISPONIBLES (Javier)
        Button button = findViewById(R.id.buttonAvaibleGuardsId);
        button.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Intent intentAvaibleGuard = new Intent(DashboardActivity.this, AvaibleGuardsActivity.class);
                intentAvaibleGuard.putExtra("app token value set",myToken);
                startActivity(intentAvaibleGuard);
            }
        });

        // Que es? adapter del cardview?? <--- escribanlo
        guardsViewModel = new ViewModelProvider(this,
                new GuardsFactory(this.getApplication(), myToken)).get(GuardsViewModel.class);
        Log.i("dashboard","guardsViewModel created");
        guardsViewModel.getMyGuards().observe(this,
                myGuards->{adapter.submitList(myGuards);});
        Log.i("dashboard","observing my guards");
        guardsViewModel.getNumGuards();
        Log.i("guardsViewModels",""+guardsViewModel.getNumGuards());

        // Toolbar
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.activity_name_dashboard,false);
    }

    // AppBar menu:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_options, menu);
        return true;
    }

    // AppBar menu:
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

    // AppBar toolbar:
    private void appToolbar(Toolbar toolbar, int activity_name, boolean enable) {
        setSupportActionBar(toolbar);
        getSupportActionBar().setSubtitle(activity_name);
        getSupportActionBar().setDisplayHomeAsUpEnabled(enable);
    }

    public String getMyTokenValue() {
        return this.myToken;
    }

    public GuardsViewModel getGuardsViewModel() {
        return guardsViewModel;
    }
}