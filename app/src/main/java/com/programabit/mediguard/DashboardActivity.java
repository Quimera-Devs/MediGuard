package com.programabit.mediguard;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.TextView;

import com.programabit.mediguard.rest.MedicDto;
import com.programabit.mediguard.rest.MedicViewModel;

public class DashboardActivity extends AppCompatActivity {
    MedicViewModel medicViewModel;
    TextView username;
    String myToken = "";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_dashboard);
        username = findViewById(R.id.username);

        Intent intent = getIntent();

        if(intent.getExtras() != null){
            myToken = (intent.getStringExtra("data"));
            Log.i("app token value set: ",myToken);
            medicViewModel = new MedicViewModel(this.getApplication(), myToken);
            Log.i("medicviewmodel created","ok");

            MedicDto myself = medicViewModel.getMyself();

            Log.i("loged in user",myself.toString());
            //if(myself !=null){
                //Log.i("Token test", myself.getDepartamento());
                //username.setText("Welcome "+ myself.getNombre_apellido());
            //}
        }
    }

    public String getMyTokenValue() {
        return this.myToken;
    }
}