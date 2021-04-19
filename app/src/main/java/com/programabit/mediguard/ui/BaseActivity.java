package com.programabit.mediguard.ui;

import android.content.Intent;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.preferences.TokenPreference;

public abstract class BaseActivity extends AppCompatActivity {
    Toolbar toolbar;
    int layoutID;

    @Override
    public void setContentView(int layoutResID) {
        super.setContentView(layoutResID);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        configureToolbar(toolbar, layoutResID);
    }

    //Set toolbar
    public void configureToolbar(Toolbar toolbar, int layoutResID) {
        layoutID = layoutResID;
        if(toolbar != null) {
            setSupportActionBar(toolbar);
            if (layoutResID == R.layout.activity_dashboard) {
                getSupportActionBar().setSubtitle(R.string.activity_name_dashboard);
                getSupportActionBar().setDisplayHomeAsUpEnabled(false);
            } else if (layoutResID == R.layout.activity_my_guards) {
                getSupportActionBar().setSubtitle(R.string.activity_name_my_guards);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (layoutResID == R.layout.activity_avaible_guards) {
                getSupportActionBar().setSubtitle(R.string.activity_name_avalible_guards);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (layoutResID == R.layout.activity_user_settings) {
                getSupportActionBar().setSubtitle(R.string.activity_name_user_panel);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (layoutResID == R.layout.activity_user_photo) {
                getSupportActionBar().setSubtitle(R.string.acivity_name_update_photo);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (layoutResID == R.layout.activity_about) {
                getSupportActionBar().setSubtitle(R.string.activity_name_about);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            } else if (layoutResID == R.layout.activity_contact) {
                getSupportActionBar().setSubtitle(R.string.activity_name_contact);
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            }
        }
    }

    //Back button
    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

    // Toolbar menu inflater:
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_toolbar_options, menu);
        return true;
    }

    // Toolbar menu items:
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int itemId = item.getItemId();
        if (itemId == R.id.mSettings) {
            startActivity(new Intent(this, UserSettingsActivity.class));
        } else if (itemId == R.id.mContact) {
            startActivity(new Intent(this, ContactActivity.class));
        } else if (itemId == R.id.mAbout) {
            startActivity(new Intent(this, AboutActivity.class));
        } else if (itemId == R.id.mLogoff) {
            TokenPreference preferences = new TokenPreference(this);
            preferences.saveToken("");
            startActivity(new Intent(this, LoginActivity.class));
        }
        return super.onOptionsItemSelected(item);
    }

}
