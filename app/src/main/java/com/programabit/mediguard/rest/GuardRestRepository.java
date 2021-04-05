package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.GuardDto;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardRestRepository {
    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MutableLiveData<List<GuardDto>> myGuards = new MutableLiveData<>();

    public GuardRestRepository(Application application){
        loadMyGuards();
    }

    public void loadMyGuards(){
        myGuards.setValue(new ArrayList<>());
        Call<List<GuardDto>> call = apiService.getMisGuardias();
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
}
