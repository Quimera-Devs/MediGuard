package com.programabit.mediguard;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Application;
import android.content.Intent;
import android.os.Bundle;
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
    }
}