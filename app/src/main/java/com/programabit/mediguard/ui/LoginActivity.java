package com.programabit.mediguard.ui;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.textfield.TextInputEditText;
import com.programabit.mediguard.R;
import com.programabit.mediguard.data.ApiClient;
import com.programabit.mediguard.data.LoginRequest;
import com.programabit.mediguard.data.LoginResponse;
import com.programabit.mediguard.data.preferences.TokenPreference;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;


public class LoginActivity extends AppCompatActivity{
    TextInputEditText username, password;
    Button btnLogin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        createNotificationChannel();

        setContentView(R.layout.activity_login);username = findViewById(R.id.edUsername);
        password = findViewById(R.id.edPassword);
        btnLogin = findViewById(R.id.btnLogin);

        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {

                if(TextUtils.isEmpty(username.getText().toString()) || TextUtils.isEmpty(password.getText().toString())){
                    Toast.makeText(LoginActivity.this,"Usuario / Contraseña Requeridos", Toast.LENGTH_LONG).show();
                }else{
                    //proceed to login
                    login();
                }

            }
        });
    }
    private void createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            CharSequence name = getString(R.string.channel_name);
            String description = getString(R.string.channel_description);
            int importance = NotificationManager.IMPORTANCE_HIGH;
            NotificationChannel channel = new NotificationChannel("mediguardPush", "MediGuard Notifications", importance);
            channel.setDescription(description);

            NotificationManager notificationManager = getSystemService(NotificationManager.class);
            notificationManager.createNotificationChannel(channel);
        }
    }
    public void login(){
        LoginRequest loginRequest = new LoginRequest();
        loginRequest.setUsername(username.getText().toString());
        loginRequest.setPassword(password.getText().toString());

        Call<LoginResponse> loginResponseCall = ApiClient.getUserService().userLogin(loginRequest);
        loginResponseCall.enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {

                if (response.isSuccessful()) {
                    Toast.makeText(LoginActivity.this, "Acceso Correcto", Toast.LENGTH_LONG).show();
                    LoginResponse loginResponse = response.body();
                    Log.i("login succesfull", loginResponse.getToken());
                    TokenPreference preferences = new TokenPreference(LoginActivity.this);
                    preferences.saveToken(loginResponse.getToken());
                    startActivity(new Intent(LoginActivity.this,DashboardActivity.class).putExtra("data",loginResponse.getToken()));
                    finish();

                }else{
                    Toast.makeText(LoginActivity.this,"Usuario o Contraseña incorrectos", Toast.LENGTH_LONG).show();

                }

            }

            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "Throwable " + t.getLocalizedMessage(), Toast.LENGTH_LONG).show();

            }


        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        TokenPreference preference = new TokenPreference(this);
        if (!preference.getToken().isEmpty()) {
            startActivity(new Intent(LoginActivity.this, DashboardActivity.class).putExtra("data", preference.getToken()));
            finish();
        }
    }
}

