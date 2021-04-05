package com.programabit.mediguard.rest;

import android.app.Application;
import android.widget.Toast;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicRestRepository {
    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MedicDto myself = new MedicDto();
    private Application application;

    public MedicRestRepository(Application application){
        this.application = application;
        loadMyself();
    }

    public void loadMyself(){
        myself=null;
        Call<MedicDto> call = apiService.getMedic();
        call.enqueue(new Callback<MedicDto>() {
            @Override
            public void onResponse(Call<MedicDto> call,
                                   Response<MedicDto> response) {
                MedicDto thisMedic = response.body();
                if(thisMedic != null){
                    myself = thisMedic;
                }
            }

            @Override
            public void onFailure(Call<MedicDto> call, Throwable t) {
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

    public MedicDto getMyself() {
        return myself;
    }

    public void setMyself(MedicDto myself) {
        this.myself = myself;
    }

    public Application getApplication() {
        return application;
    }

    public void setApplication(Application application) {
        this.application = application;
    }
}
