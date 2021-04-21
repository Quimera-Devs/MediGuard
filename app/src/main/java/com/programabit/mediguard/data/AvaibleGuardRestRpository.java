package com.programabit.mediguard.data;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.domain.GuardDto;
import com.programabit.mediguard.ui.DashboardActivity;

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
    private GuardDto selectedGuard;


    public AvaibleGuardRestRpository(Application application, String tokenValue) {
        this.application = application;
        this.token = tokenValue;
    }

    @Override
    protected List<GuardDto> doInBackground(String... strings) {
        switch (strings[1]){
            case "getAvailableGuards":
                Call<List<GuardDto>> call = apiService.getAvailableGuardsGuards("Token " + strings[0]);
                Log.i("GuardsViewModel", "background call request");
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
                    Log.i("GuardsViewModel", "got data");
                    return guardsList;
                }
            case "assignGuards":
                selectedGuard.setDisponible(false);
                selectedGuard.setMedico(DashboardActivity.getMyself().getCi());
                Call<GuardDto> delcall = apiService.editGuard(selectedGuard.getId(),
                        selectedGuard,
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
          /*  case "assignGuardsNotiff":
                Call<GuardDto> patchcall = apiService.patchGuard(selectedGuard.getId(),
                        selectedGuard.getMedico(),
                        selectedGuard.isDisponible(),
                        "Token " + token);
                response = null;
                guardsList = null;
                try {
                    response = patchcall.execute();
                    Log.i("GuardsRepo", "try assign notiff response");

                } catch (IOException e) {
                    Log.i("response ioexception", e.getMessage());
                    e.printStackTrace();
                }
                if (response.isSuccessful()) {
                    Log.i("GuardsRepo", "delete complete");
                }*/
        }
        return null;
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

    public GuardDto getSelectedGuard() {
        return selectedGuard;
    }

    public void setSelectedGuard(GuardDto selectedGuard) {
        this.selectedGuard = selectedGuard;
    }
}
