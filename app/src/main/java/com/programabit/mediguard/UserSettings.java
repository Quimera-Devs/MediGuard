package com.programabit.mediguard;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class UserSettings extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);


        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}