package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class AvaibleGuardViewModel extends AndroidViewModel {
    private AvaibleGuardRestRpository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> myGuards;
    private String token;

    private AvaibleGuardViewModel(Application application, String tokenValue){
        super(application);
        token = tokenValue;
        guardRestRepository = new AvaibleGuardRestRpository(application,tokenValue);
        myGuards = guardRestRepository.getMyGuards();
    }

    public AvaibleGuardRestRpository getGuardRestRepository() {
        return guardRestRepository;
    }

    public void setGuardRestRepository(AvaibleGuardRestRpository guardRestRepository) {
        this.guardRestRepository = guardRestRepository;
    }

    public MutableLiveData<List<GuardDto>> getMyGuards() {
        return myGuards;
    }
}
