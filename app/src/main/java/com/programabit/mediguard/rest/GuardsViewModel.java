package com.programabit.mediguard.rest;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class GuardsViewModel extends AndroidViewModel {
    private GuardRestRepository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> myGuards = new MutableLiveData<List<GuardDto>>();
    private String token;
    private Application myapplication;

    public GuardsViewModel(Application application, String tokenValue)
            throws ExecutionException, InterruptedException {
        super(application);
        token = tokenValue;
        myapplication = application;
        guardRestRepository = new GuardRestRepository(application,tokenValue);
        Log.i("GuardsViewModel","guards rest repo created");
        myGuards.setValue(guardRestRepository.execute(new String[]{token,"getMyGuards"}).get());
        Log.i("GuardsViewModel","got data from api & saved in myGuards MutableLiveData");
        Log.i("GuardsViewModel","view model created");
    }

    public GuardRestRepository getGuardRestRepository() {
        return guardRestRepository;
    }

    public void setGuardRestRepository(GuardRestRepository guardRestRepository) {
        this.guardRestRepository = guardRestRepository;
    }

    public MutableLiveData<List<GuardDto>> getMyGuards() {
        return myGuards;
    }
    public int getNumGuards(){
        return myGuards.getValue().size();
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public void delete(GuardDto myGuard) throws ExecutionException, InterruptedException {
        guardRestRepository = new GuardRestRepository(myapplication,token);
        guardRestRepository.setMyGuard(myGuard);
        guardRestRepository.execute(new String[]{token,"deleteGuards"}).get();
        Log.i("GuardsViewModel", "delete complete");
        guardRestRepository = new GuardRestRepository(myapplication,token);
        myGuards.setValue(guardRestRepository.execute(new String[]{token,"getMyGuards"}).get());
    }
}
