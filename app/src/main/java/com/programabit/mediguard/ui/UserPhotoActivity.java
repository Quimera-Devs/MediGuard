package com.programabit.mediguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.programabit.mediguard.R;

public class UserPhotoActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_photo);

        // Toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        appToolbar(toolbar, R.string.acivity_name_update_photo,true);
    }

    // AppBar (toolbar y menu):
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
}