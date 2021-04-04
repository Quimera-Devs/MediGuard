package com.programabit.mediguard;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.LinearLayoutManager;
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
        setContentView(R.layout.activity_mis_guardias_recycler);

        recyclerView = findViewById(R.id.mis_guardias_recycler_id);
        final MisGuardiasAdapter adapter = new MisGuardiasAdapter(new MisGuardiasAdapter.MyViewHolder);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        GuardiasViewModel = new ViewModelProvider(this,new MisGuardiasActivity(getApplication()).get(GuardiasViewModel.class));

        GuardiasViewModel.getMisGuardias().observe(this,misGuardias->{adapter.submitList(misGuardias)});


        /*misGuardiasModelList = new ArrayList<>();
        adapter = new MisGuardiasAdapter(this, misGuardiasModelList);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
        */
        cargarMisGuardiasCards();

    }

    private void cargarMisGuardiasCards() {


    }
}
