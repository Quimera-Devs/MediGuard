package com.programabit.mediguard.rest;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import androidx.lifecycle.MutableLiveData;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class GuardRestRepository extends AsyncTask<String,Void,List<GuardDto>> {

    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MutableLiveData<List<GuardDto>> availableGuards = new MutableLiveData<>();
    private Application application;
    private String token;

    public GuardRestRepository(Application application, String tokenValue) {
        this.application = application;
        this.token = tokenValue;
        Log.i("GuardsRestRepo","repo created");

    }

    @Override
    protected List<GuardDto> doInBackground(String... strings) {
        Call<List<GuardDto>> call = apiService.getMyGuards("Token " + strings[0]);
        Log.i("GuardsViewModel","background call request");
        Response response = null;
        List<GuardDto> guardsList = null;
        try {
            response = call.execute();
            Log.i("GuardsViewModel","try response");

        } catch (IOException e) {
            Log.i("response ioexception", e.getMessage());
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            guardsList = (List<GuardDto>) response.body();
            Log.i("GuardsViewModel","got data");
            return guardsList;
        }else {
            return null;
        }
    }

    public void loadAvailableGuards() {
        availableGuards.setValue(new ArrayList<>());
        Call<List<GuardDto>> call = apiService.getAvailableGuardsGuards("Token "+this.token);
        call.enqueue(new Callback<List<GuardDto>>() {
            @Override
            public void onResponse(Call<List<GuardDto>> call,
                                   Response<List<GuardDto>> response) {
                List<GuardDto> guardList = response.body();
                if (guardList != null) {
                    availableGuards.setValue(guardList);
                }
            }

            @Override
            public void onFailure(Call<List<GuardDto>> call, Throwable t) {
                Toast.makeText(application.getApplicationContext(), "Error: " + t.getMessage(),
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
