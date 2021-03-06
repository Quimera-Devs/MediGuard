package com.programabit.mediguard.ui;

import android.content.Intent;
import android.util.Log;
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
        toolbar = findViewById(R.id.toolbar);
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
            } else {
                getSupportActionBar().setDisplayHomeAsUpEnabled(true);
                if (layoutResID == R.layout.activity_my_guards) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_my_guards);
                } else if (layoutResID == R.layout.activity_avaible_guards) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_avalible_guards);
                } else if (layoutResID == R.layout.activity_user_settings) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_user_panel);
                } else if (layoutResID == R.layout.activity_user_update) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_activity_user_update);
                } else if (layoutResID == R.layout.activity_user_photo) {
                    getSupportActionBar().setSubtitle(R.string.acivity_name_update_photo);
                } else if (layoutResID == R.layout.activity_about) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_about);
                } else if (layoutResID == R.layout.activity_contact) {
                    getSupportActionBar().setSubtitle(R.string.activity_name_contact);
                }
            }
        }
    }

    //Back button
    @Override
    public boolean onSupportNavigateUp() {
        finish();
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
        if (itemId == android.R.id.home) {
            notificationIntentChecker();
            finish();
            return true;
        } else if (itemId == R.id.mSettings && layoutID != R.layout.activity_user_settings) {
            startActivity(new Intent(this, UserSettingsActivity.class));
            finishCurrent();
        } else if (itemId == R.id.mContact && layoutID != R.layout.activity_contact) {
            startActivity(new Intent(this, ContactActivity.class));
            finishCurrent();
        } else if (itemId == R.id.mAbout && layoutID != R.layout.activity_about) {
            startActivity(new Intent(this, AboutActivity.class));
            finishCurrent();
        } else if (itemId == R.id.mLogoff) {
            TokenPreference preferences = new TokenPreference(this);
            preferences.saveToken("");
            startActivity(new Intent(this, LoginActivity.class));
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void clearAppData() {
        try {
            // clearing app data
            //TokenPreference preferences = new TokenPreference(this);
            //preferences.saveToken("");
            this.getSharedPreferences("KEY_COUNT", 0).edit().clear().apply();
            this.getSharedPreferences("KEY_TOKEN", 0).edit().clear().apply();
            String packageName = getApplicationContext().getPackageName();
            Runtime runtime = Runtime.getRuntime();
            runtime.exec("pm clear "+packageName);
        } catch (Exception ex) {
            ex.printStackTrace();
        }
    }

    private void notificationIntentChecker() {
        Intent intent = getIntent();
        if(intent.getExtras() != null) {
            try {
                boolean isNotification = getIntent().getExtras().getBoolean("notification");
                if (isNotification) {
                    String myToken = (intent.getStringExtra("token"));
                    startActivity(new Intent(this,DashboardActivity.class).putExtra("data",myToken));
                }
            } catch (Exception e) {
                Log.i("Back Button exception", "No estas volviendo desde una nofiticaci??n" + e);
                e.printStackTrace();
            }
        }
    }

    private boolean finishCurrent() {
        if (layoutID != R.layout.activity_dashboard) {
            finish();
            return true;
        } else {
            onPause();
            return false;
        }
    }
}
