package com.programabit.mediguard.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.domain.GuardDto;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

public class AvaibleGuardRestRpository extends AsyncTask<String,Void,List<GuardDto>> {
    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MutableLiveData<List<GuardDto>> myGuards = new MutableLiveData<>();
    private MutableLiveData<List<GuardDto>> availableGuards = new MutableLiveData<>();
    private Application application;
    private String token;

    public AvaibleGuardRestRpository(Application application, String tokenValue) {
        this.application = application;
        this.token = tokenValue;
    }

    @Override
    protected List<GuardDto> doInBackground(String... strings) {
        Call<List<GuardDto>> call = apiService.getAvailableGuardsGuards("Token "+strings[0]);
        Log.i("GuardsViewModel","background call request");
        Response response = null;
        List<GuardDto> guardsList = null;
        try {
            response = call.execute();
            Log.i("AvaibleGuardsViewModel","try response");

        } catch (IOException e) {
            Log.i("response ioexception", e.getMessage());
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            guardsList = (List<GuardDto>) response.body();
            Log.i("AvaibleGuardsViewModel","got data");
            return guardsList;
        }else {
            return null;
        }
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
