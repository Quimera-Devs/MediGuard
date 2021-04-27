package com.programabit.mediguard.ui;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

import com.programabit.mediguard.R;

public class AboutActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about);
        init();

    }

    private void init() {
        findViewById(R.id.repository).setOnClickListener(v -> {
            Intent intent = new Intent();
            intent.setAction(Intent.ACTION_VIEW);
            intent.setData(Uri.parse("https://github.com/Quimera-Devs/MediGuard"));
            startActivity(intent);
        });
    }

    public void changeLogo(View view) {
        ImageView logo = findViewById(R.id.logo);
        logo.setOnClickListener(v -> logo.setImageResource(R.drawable.chimera_devs));
    }
}