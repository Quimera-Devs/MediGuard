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
    private GuardDto myGuard;
    private Application application;
    private String token;

    public GuardRestRepository(Application application, String tokenValue) {
        this.application = application;
        this.token = tokenValue;
        Log.i("GuardsRestRepo","repo created");

    }

    @Override
    protected List<GuardDto> doInBackground(String... strings) {
        switch (strings[1]){
            case "getMyGuards":
            Call<List<GuardDto>> call = apiService.getMyGuards("Token " + strings[0]);
            Log.i("GuardsViewModel", "background call request");
            Response response = null;
            List<GuardDto> guardsList = null;
            try {
                response = call.execute();
                Log.i("GuardsViewModel", "try response");

            } catch (IOException e) {
                Log.i("response ioexception", e.getMessage());
                e.printStackTrace();
            }
            if (response.isSuccessful()) {
                guardsList = (List<GuardDto>) response.body();
                Log.i("GuardsViewModel", "got data");
                return guardsList;
            }
            case "deleteGuards":
                myGuard.setDisponible(true);
                Call<GuardDto> delcall = apiService.editGuard(myGuard.getId(),
                        //myGuard,
                        myGuard.getId(),
                        myGuard.getFecha(),
                        myGuard.getTurno(),
                        true,
                        myGuard.getDepartamento(),
                        myGuard.getMin_ranking(),
                        myGuard.getCentroSalud(),
                        myGuard.getMedico(),
                        "Token " + token);
                Log.i("GuardsRepo", "background delete call request");
                 response = null;
                guardsList = null;
                try {
                    response = delcall.execute();
                    Log.i("GuardsRepo", "try delete response");

                } catch (IOException e) {
                    Log.i("response ioexception", e.getMessage());
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    Log.i("GuardsRepo", "delete complete");
                }
        }
        return null;
    }

    public void deleteGuard(GuardDto myGuard){

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

    public GuardDto getMyGuard() {
        return myGuard;
    }

    public void setMyGuard(GuardDto myGuard) {
        this.myGuard = myGuard;
    }
}
