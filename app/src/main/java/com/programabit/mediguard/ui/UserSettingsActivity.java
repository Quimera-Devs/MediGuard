package com.programabit.mediguard.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.TextView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.domain.MedicDto;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;

import java.util.concurrent.ExecutionException;

public class UserSettingsActivity extends AppCompatActivity {
    ImageButton imgUserPhoto;
    TextView username;
    TextView ci;
    TextView dir;
    TextView phone;
    TextView account_num;
    TextView ranking;
    TextView department;
    MedicRestRepositoryAsync medicRepo;
    MedicDto myself;
    private String myToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        username    = findViewById(R.id.tvUserName);
        ci          = findViewById(R.id.tvCedula);
        dir         = findViewById(R.id.tvDir);
        phone         = findViewById(R.id.tvPhone);
        account_num = findViewById(R.id.tvCheckingAccount);
        ranking     = findViewById(R.id.tvRanking);
        department  = findViewById(R.id.tvDepartment);

        // Setear extras (token y usuario)
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("data"));
            medicRepo = new MedicRestRepositoryAsync(this.getApplication(), myToken);
            medicRepo.execute(new String[]{myToken});
            try {
                myself = medicRepo.get();
            } catch (ExecutionException e) {
                Log.i("execute exception", e.getMessage());
                e.printStackTrace();
            } catch (InterruptedException e) {
                Log.i("interrupt exception", e.getMessage());
                e.printStackTrace();
            }
            if (myself != null) {
                username.setText(myself.getNombre_apellido());
                Log.i("user_settings","got user correctly");
            }
        }


        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("token"));
            username.setText(intent.getStringExtra("name"));
            ci.setText(intent.getStringExtra("ci"));
            dir.setText(intent.getStringExtra("dir"));
            phone.setText(intent.getStringExtra("phone"));
            account_num.setText(intent.getStringExtra("name"));
            ranking.setText(intent.getStringExtra("ranking"));
            department.setText(intent.getStringExtra("department"));
            Log.i("settings","got user correctly");
            }

        // Intent a CAMBIAR FOTO (Ramon)
        imgUserPhoto = findViewById(R.id.userPhoto);
        imgUserPhoto.setOnClickListener(v -> {
            Intent intentPhoto = new Intent(UserSettingsActivity.this,UserPhotoActivity.class);
            startActivity(intentPhoto);
        });

        // AppBar Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.activity_name_user_panel,true);
    }

    // AppBar Menu
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_options, menu);
        return true;
    }

    // AppBar Menu Items
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mContact) {
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

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}