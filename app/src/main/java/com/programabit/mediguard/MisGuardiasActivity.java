package com.programabit.mediguard;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

public class MisGuardiasActivity extends AppCompatActivity {

    private List<MisGuardiasModel> misGuardiasModelList;
    private MisGuardiasAdapter adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        recyclerView = findViewById(R.id.mis_guardias_recycler_id);
        misGuardiasModelList = new ArrayList<>();
        adapter = new MisGuardiasAdapter(this, misGuardiasModelList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);

        cargarMisGuardiasCards();

    }

    private void cargarMisGuardiasCards() {


    }
}
