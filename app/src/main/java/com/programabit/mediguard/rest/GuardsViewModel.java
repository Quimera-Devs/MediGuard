package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.rest.GuardDto;
import com.programabit.mediguard.rest.GuardRestRepository;

import java.util.List;

public class GuardsViewModel extends AndroidViewModel {
    private GuardRestRepository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> myGuards;
    private String token;

    private GuardsViewModel(Application application, String tokenValue){
        super(application);
        token = tokenValue;
        guardRestRepository = new GuardRestRepository(application,tokenValue);
        myGuards = guardRestRepository.getMyGuards();
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
}
