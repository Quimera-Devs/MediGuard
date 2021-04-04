package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.MisGuardiasDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardiasRestRepository {
    private ApiClient apiClient = (ApiClient) ApiClient.getRetrofit().create(UserService.class);
    private MutableLiveData<List<MisGuardiasDto>> misGuardias = new MutableLiveData<>();

    public GuardiasRestRepository(Application application){

    }

    public MutableLiveData<List<MisGuardiasDto>> loadMisGuardias(){
        misGuardias.setValue(new ArrayList<>());
        Call<List<MisGuardiasDto>> call = UserService.getMisGuardias();
        call.enqueue(new Callback<List<MisGuardiasDto>>() {
            @Override
            public void onResponse(Call<List<MisGuardiasDto>> call, Response<List<MisGuardiasDto>> response) {
                List<MisGuardiasDto> misGuardiasList = response.body();
                if(misGuardiasList != null){
                    misGuardias.setValue(misGuardiasList);
                }
            }

            @Override
            public void onFailure(Call<List<MisGuardiasDto>> call, Throwable t) {

            }
        });
        return misGuardias;
    }
}
