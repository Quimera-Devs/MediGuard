package com.programabit.mediguard.ui;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.data.preferences.GuardCountPreference;
import com.programabit.mediguard.domain.AvaibleGuardViewModel;
import com.programabit.mediguard.domain.GuardDto;

import java.util.Objects;
import java.util.concurrent.ExecutionException;


public class AvaibleGuardsActivity extends BaseActivity {
    private AvaibleGuardViewModel guardsViewModel;
    private RecyclerView recyclerView;
    private String myToken;
    private TextView tvGuardsMessage;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.activity_avaible_guards);
        GuardCountPreference preference = new GuardCountPreference(this);
        final AvaibleGuardsListAdapter adapter = new AvaibleGuardsListAdapter
                (new AvaibleGuardsListAdapter.guardDiff());

        recyclerView = findViewById(R.id.recyclerViewGuards);
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        Intent intent = getIntent();

        if(intent.getExtras() != null) {
            myToken = (intent.getStringExtra("token"));
        }
        Log.i("My Guards Activity","got token");
        guardsViewModel = new ViewModelProvider(this,
                new AvaibleGuardsFactory(this.getApplication(), myToken))
                .get(AvaibleGuardViewModel.class);
        Log.i("Available Guards Activi","set view model");
        guardsViewModel.getMyGuards().observe(this,
                myGuards->{adapter.submitList(myGuards);});
        Log.i("My Guards Activity","observing my guards list");

        adapter.setOnItemClickListener(new AvaibleGuardsListAdapter.onItemClickListener() {
            @Override
            public void onItemAssign(GuardDto myGuard) throws ExecutionException, InterruptedException {
                try{
                guardsViewModel.assing(myGuard);
                    preference.setCount(preference.getCount()+1);
                } catch (ExecutionException e) {
                    e.printStackTrace();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }

        });
        messageGuardsNotFound();
    }

    @Override
    protected void onResume() {
        super.onResume();
        messageGuardsNotFound();
    }

    private void messageGuardsNotFound() {
        tvGuardsMessage = findViewById(R.id.tvNoGuardsFound);
        if (Objects.requireNonNull(recyclerView.getAdapter()).getItemCount() == 0) {
            tvGuardsMessage.setVisibility(View.VISIBLE);
            recyclerView.setVisibility(View.GONE);
        } else {
            tvGuardsMessage.setVisibility(View.GONE);
            recyclerView.setVisibility(View.VISIBLE);
        }
    }
}
