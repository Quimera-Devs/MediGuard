package com.programabit.mediguard.rest;

import android.app.Application;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class AvaibleGuardRestRpository {
    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MutableLiveData<List<GuardDto>> myGuards = new MutableLiveData<>();
    private MutableLiveData<List<GuardDto>> availableGuards = new MutableLiveData<>();
    private Application application;
    private String token;

    public AvaibleGuardRestRpository(Application application, String tokenValue) {
        this.application = application;
        this.token = tokenValue;
    }

    public void loadMyGuards(){
        myGuards.setValue(new ArrayList<>());
        Call<List<GuardDto>> call = apiService.getMyGuards("Token "+this.token);
        call.enqueue(new Callback<List<GuardDto>>() {
            @Override
            public void onResponse(Call<List<GuardDto>> call,
                                   Response<List<GuardDto>> response) {
                List<GuardDto> misGuardiasList = response.body();
                if(misGuardiasList != null){
                    myGuards.setValue(misGuardiasList);
                }
            }

            @Override
            public void onFailure(Call<List<GuardDto>> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),"Error: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public void loadAvailableGuards(){
        availableGuards.setValue(new ArrayList<>());
        Call<List<GuardDto>> call = apiService.getAvailableGuardsGuards("Token "+this.token);
        call.enqueue(new Callback<List<GuardDto>>() {
            @Override
            public void onResponse(Call<List<GuardDto>> call,
                                   Response<List<GuardDto>> response) {
                List<GuardDto> guardList = response.body();
                if(guardList != null){
                    availableGuards.setValue(guardList);
                }
            }

            @Override
            public void onFailure(Call<List<GuardDto>> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(),"Error: "+t.getMessage(),
                        Toast.LENGTH_LONG).show();
            }
        });
    }

    public UserService getApiService() {
        return apiService;
    }

    public void setApiService(UserService apiService) {
        this.apiService = apiService;
    }

    public MutableLiveData<List<GuardDto>> getMyGuards() {
        return myGuards;
    }

    public void setMyGuards(MutableLiveData<List<GuardDto>> myGuards) {
        this.myGuards = myGuards;
    }

    public MutableLiveData<List<GuardDto>> getAvailableGuards() {
        return availableGuards;
    }

    public void setAvailableGuards(MutableLiveData<List<GuardDto>> availableGuards) {
        this.availableGuards = availableGuards;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}