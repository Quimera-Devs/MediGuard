package com.programabit.mediguard.ui;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.domain.MedicDto;
import com.programabit.mediguard.data.MedicRestRepositoryAsync;

import java.util.concurrent.ExecutionException;

public class UserSettingsActivity extends BaseActivity {
    ImageButton imgUserPhoto;
    ImageView imgUserPicture;
    TextView username;
    TextView ci;
    TextView dir;
    TextView phone;
    TextView account_num;
    TextView ranking;
    TextView department;
    MedicRestRepositoryAsync medicRepo;
    MedicDto myself;
    private String myToken;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_settings);
        username    = findViewById(R.id.tvUserName);
        ci          = findViewById(R.id.tvCedula);
        dir         = findViewById(R.id.tvDir);
        phone         = findViewById(R.id.tvPhone);
        account_num = findViewById(R.id.tvCheckingAccount);
        ranking     = findViewById(R.id.tvRanking);
        department  = findViewById(R.id.tvDepartment);

        // Intent a CAMBIAR FOTO (Ramon)
        imgUserPhoto = findViewById(R.id.userPhoto);
        imgUserPhoto.setOnClickListener(v -> {
            Log.i("Settings","Go to User Photo");
            startActivity(new Intent(UserSettingsActivity.this,UserPhotoActivity.class).putExtra("token",myToken));
        });
    }
}