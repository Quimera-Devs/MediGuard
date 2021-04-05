package com.programabit.mediguard;

import android.os.Bundle;
import androidx.appcompat.app.AppCompatActivity;

import com.programabit.mediguard.rest.ApiClient;

import java.util.List;

import retrofit2.Call;

public class AvaibleGuards extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle saveInstanceState) {
        super.onCreate(saveInstanceState);
        setContentView(R.layout.avaible_guards);

        Call<List<AvaibleGuardsResponse>> response = ApiClient.getUserService().getAvaibleGuards();

    }
}
