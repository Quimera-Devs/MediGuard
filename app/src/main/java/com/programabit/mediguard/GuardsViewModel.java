package com.programabit.mediguard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.rest.GuardRestRepository;

import java.util.List;

public class GuardsViewModel extends AndroidViewModel {
    private GuardRestRepository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> myGuards;

    private GuardsViewModel(Application application){
        super(application);
        guardRestRepository = new GuardRestRepository(application);
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
