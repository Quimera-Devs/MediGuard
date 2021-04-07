package com.programabit.mediguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.util.Log;
import android.widget.TextView;

import com.programabit.mediguard.rest.MedicDto;
import com.programabit.mediguard.rest.MedicViewModel;

public class DashboardActivity extends AppCompatActivity {
    MedicViewModel medicViewModel;
    TextView username;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);

        username = findViewById(R.id.username);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            String passedToken = intent.getStringExtra("data");
            medicViewModel = new MedicViewModel(this.getApplication(),passedToken );
            MedicDto myself = medicViewModel.getMyself();
            if(myself !=null){
                Log.i("Token test", myself.getDepartamento());
                username.setText("Welcome "+ myself.getDepartamento());
            }
        }

        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.activity_name_dashboard,false);
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
}