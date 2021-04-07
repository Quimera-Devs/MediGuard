package com.programabit.mediguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import com.programabit.mediguard.rest.ApiClient;
import com.programabit.mediguard.rest.GuardsViewModel;

import java.util.List;

import retrofit2.Call;

public class AvaibleGuardsActivity extends AppCompatActivity {

    private GuardsViewModel guardsViewModel;
    private RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.avaible_guards);

       // recyclerView = findViewById(R.id.);
        //Call<List<AvaibleGuardsResponse>> response = ApiClient.getUserService().getAvaibleGuards();

    }
}
