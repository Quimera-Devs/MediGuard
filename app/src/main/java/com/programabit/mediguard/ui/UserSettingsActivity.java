package com.programabit.mediguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageButton;
import android.widget.TextView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.domain.MedicDto;

import java.util.concurrent.ExecutionException;

public class UserSettingsActivity extends BaseActivity {
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


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        username = findViewById(R.id.tvUserName);
        ci = findViewById(R.id.tvCedula);
        dir = findViewById(R.id.tvDir);
        phone = findViewById(R.id.tvPhone);
        account_num = findViewById(R.id.tvCheckingAccount);
        ranking = findViewById(R.id.tvRanking);
        department = findViewById(R.id.tvDepartment);
        TokenPreference preference = new TokenPreference(this);
        String myToken = preference.getToken();

        // Intent a CAMBIAR FOTO (Ramon)
        imgUserPhoto = findViewById(R.id.userPhoto);
        imgUserPhoto.setOnClickListener(v -> {
            Log.i("Settings", "Go to User Photo");
            startActivity(new Intent(UserSettingsActivity.this, UserPhotoActivity.class).putExtra("token", myToken));
        });


        // Setear token y usuario (Matias)
        if (!myToken.isEmpty()) {
            Log.i("Settings", "got token correctly");

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
                Integer cii = myself.getCi();
                Integer phonee = myself.getTelefono();
                Integer account = myself.getNroCaja();
                Integer rank = myself.getRanking();

                username.setText(myself.getNombre_apellido());
                ci.setText(cii.toString());
                dir.setText(myself.getDireccion());
                phone.setText(phonee.toString());
                account_num.setText(account.toString());
                ranking.setText(rank.toString());
                department.setText(myself.getDepartamento());
                Log.i("Settings", "filled user data correctly");
            }
        }


    }
}