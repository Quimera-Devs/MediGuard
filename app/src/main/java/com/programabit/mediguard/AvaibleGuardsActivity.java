package com.programabit.mediguard;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.rest.AvaibleGuardViewModel;
import com.programabit.mediguard.rest.GuardDto;
import com.programabit.mediguard.rest.GuardsViewModel;

import java.util.concurrent.ExecutionException;

public class AvaibleGuardsActivity extends AppCompatActivity {

    private AvaibleGuardViewModel guardsViewModel;
    private RecyclerView recyclerView;
    private String myToken;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_avaible_guards);

        final AvaibleGuardsListAdapter adapter = new AvaibleGuardsListAdapter(new AvaibleGuardsListAdapter.guardDiff());

        RecyclerView recyclerView = findViewById(R.id.recyclerViewGuards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("app token value set"));
        }
        Log.i("My Guards Activity","got token");
        guardsViewModel = new ViewModelProvider(this,
                new AvaibleGuardsFactory(this.getApplication(), myToken)).get(AvaibleGuardViewModel.class);
        Log.i("My Guards Activity","set view model");
        guardsViewModel.getMyGuards().observe(this,
                myGuards->{adapter.submitList(myGuards);});
        Log.i("My Guards Activity","observing my guards list");

        adapter.setOnItemClickListener(new AvaibleGuardsListAdapter.onItemClickListener() {
            @Override
            public void onItemAssign(GuardDto myGuard) throws ExecutionException, InterruptedException {
                guardsViewModel.assing(myGuard);
            }
        });
    }
}
