package com.programabit.mediguard.ui;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.R;
import com.programabit.mediguard.domain.GuardDto;
import com.programabit.mediguard.domain.GuardsViewModel;

import java.util.concurrent.ExecutionException;


public class MyGuardsActivity extends BaseActivity {

    private GuardsViewModel guardsViewModel;
    private RecyclerView recyclerView;
    private String myToken;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_guards);
        Log.i("My Guards Activity","start activity");
        final MyGuardsListAdapter adapter = new MyGuardsListAdapter(
                new MyGuardsListAdapter.guardDiff());

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
            public void onItemDelete(GuardDto myGuard)
                    throws ExecutionException, InterruptedException {
                new AlertDialog.Builder(MyGuardsActivity.this)
                        .setIcon(android.R.drawable.ic_dialog_alert)
                        .setTitle(getString(R.string.delete_confirmation_title))
                        .setMessage(R.string.delete_confirmation_msg)
                        .setPositiveButton(R.string.delete_confirmation_yes, new DialogInterface.OnClickListener()
                        {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                try {
                                    guardsViewModel.delete(myGuard);
                                } catch (ExecutionException e) {
                                    e.printStackTrace();
                                } catch (InterruptedException e) {
                                    e.printStackTrace();
                                }
                            }

                        })
                        .setNegativeButton(R.string.delete_confirmation_no, null)
                        .show();
                //
            }
        });
    }
}
