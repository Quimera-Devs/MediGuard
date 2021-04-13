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

import java.util.concurrent.ExecutionException;


public class MyGuardsActivity extends AppCompatActivity {

    private GuardsViewModel guardsViewModel;
    private RecyclerView recyclerView;
    private String myToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guards);
        Log.i("My Guards Activity","start activity");
        final MyGuardsListAdapter adapter = new MyGuardsListAdapter(new MyGuardsListAdapter.guardDiff());

        recyclerView = findViewById(R.id.mis_guardias_recycler_id);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("token"));
        }
        Log.i("My Guards Activity","got token");
        guardsViewModel = new ViewModelProvider(this,
                new GuardsFactory(this.getApplication(), myToken)).get(GuardsViewModel.class);
        Log.i("My Guards Activity","set view model");
        guardsViewModel.getMyGuards().observe(this,
                myGuards->{adapter.submitList(myGuards);});

        Log.i("My Guards Activity","observing my guards list");

        adapter.setOnItemClickListener(new MyGuardsListAdapter.onItemClickListener() {
            @Override
            public void onItemDelete(GuardDto myGuard) throws ExecutionException, InterruptedException {
                //guardsViewModel.delete(myGuard);
            }
        });
    }

}
