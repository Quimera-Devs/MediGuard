package com.programabit.mediguard.rest;

import android.app.Application;
import android.os.AsyncTask;
import android.util.Log;
import android.widget.Toast;

import java.io.IOException;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MedicRestRepositoryAsync extends AsyncTask<String,Void,MedicDto> {

    private UserService apiService = ApiClient.getRetrofit().create(UserService.class);
    private MedicDto myself = new MedicDto();
    private Application application;
    private String token;

    public MedicRestRepositoryAsync(Application application, String tokenValue){
        this.application = application;
        Log.i("creating medicrepo","aa");
        token = tokenValue;
        //loadMyself();
        Log.i("created medicrepo","aa");
    }

    @Override
    protected void onPreExecute(){
        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(MedicDto result){
        myself=result;
    }

    @Override
    protected MedicDto doInBackground(String... strings) {
        Call<List<MedicDto>> call = apiService.getMedic("Token " + strings[0]);
        Response response = null;
        List<MedicDto> medicList = null;
        try {
            response = call.execute();
        } catch (IOException e) {
            Log.i("response ioexception", e.getMessage());
            e.printStackTrace();
        }
        if (response.isSuccessful()) {
            medicList = (List<MedicDto>) response.body();
            return medicList.get(0);
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
