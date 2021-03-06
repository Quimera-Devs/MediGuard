package com.programabit.mediguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.TextView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;
import com.programabit.mediguard.data.preferences.TokenPreference;
import com.programabit.mediguard.domain.MedicDto;
import com.squareup.picasso.Picasso;

import java.util.concurrent.ExecutionException;

import de.hdodenhof.circleimageview.CircleImageView;

public class UserSettingsActivity extends BaseActivity {
    private CircleImageView userPhoto;
    private TextView username;
    private TextView ci;
    private TextView dir;
    private TextView phone;
    private TextView account_num;
    private TextView ranking;
    private TextView department;
    private MedicRestRepositoryAsync medicRepo;
    private MedicDto myself;
    protected String myToken;


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
        userPhoto = findViewById(R.id.imgUserPhoto);
        TokenPreference preference = new TokenPreference(this);
        myToken = preference.getToken();

        // Setear token y usuario (Matias)
        if (!myToken.isEmpty()) {
            Log.i("Settings", "got token correctly");
            medicRepo = new MedicRestRepositoryAsync(this.getApplication(), myToken);
            medicRepo.execute(myToken);
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
                username.setText(myself.getNombre_apellido());
                ci.setText(String.valueOf(myself.getCi()));
                dir.setText(myself.getDireccion());
                phone.setText(String.valueOf(myself.getTelefono()));
                account_num.setText(String.valueOf(myself.getNroCaja()));
                ranking.setText(String.format("%s??", myself.getRanking()));
                department.setText(myself.getDepartamento());
                Log.i("Settings", "filled user data correctly");
                try {
                    Log.i("imagen", myself.getImagen());
                    Picasso.with(this)
                            .load("https://alduxsan.pythonanywhere.com/"+myself.getImagen())
                            .into(userPhoto);
                } catch (Exception e) {
                    Log.i("Cant Load UserPhoto", e.getMessage());
                    e.printStackTrace();
                }
            }
        }
    }

    // Intent a CAMBIAR FOTO (Ramon)
    public void uploadPhoto(View view) {
        Log.i("Settings", "Go to User Photo");
        startActivity(new Intent(UserSettingsActivity.this, UserPhotoActivity.class).putExtra("token", myToken));
    }

    public void goToeditUser(View view) {
        Log.i("Dashboard", "Go to User Update Activity");
        startActivity(new Intent(this, UserUpdateActivity.class));
    }
}