package com.programabit.mediguard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.rest.GuardDto;
import com.programabit.mediguard.rest.GuardsViewModel;

import java.util.List;


public class MyGuardsActivity extends AppCompatActivity {

    private GuardsViewModel guardsViewModel;
    private List<GuardDto> myGuardsList;
    private MyGuardsViewHolder adapter;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guards);

        recyclerView = findViewById(R.id.mis_guardias_recycler_id);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        cargarMisGuardiasCards();
    }

    private void cargarMisGuardiasCards() {


    }
}
