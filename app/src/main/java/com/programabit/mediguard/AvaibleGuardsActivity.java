package com.programabit.mediguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.rest.GuardsViewModel;

public class AvaibleGuardsActivity extends AppCompatActivity {

    private GuardsViewModel guardsViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_avaible_guards);

        RecyclerView recyclerView = findViewById(R.id.recyclerViewGuards);
        final AvaibleGuardsListAdapter adapter = new AvaibleGuardsListAdapter(new AvaibleGuardsListAdapter.guardDiff());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));

    }
}
