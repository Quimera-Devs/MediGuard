package com.programabit.mediguard;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;

public class AboutActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_options, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mSettings) {
            startActivity(new Intent(this, UserSettingsActivity.class));
        } else if (itemId == R.id.mContact) {
            startActivity(new Intent(this, ContactActivity.class));
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

    private void init() {
        androidx.appcompat.widget.Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.activity_name_about, true);
        findViewById(R.id.repository).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/Quimera-Devs/AgendaGuardiasMedicas"));
            startActivity(intent);
        });
    }
}