package com.programabit.mediguard.domain;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.data.AvaibleGuardRestRpository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AvaibleGuardViewModel extends AndroidViewModel {
    private AvaibleGuardRestRpository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> avaibleGuards;
    private String token;

    public AvaibleGuardViewModel(Application application, String tokenValue) throws ExecutionException, InterruptedException {
        super(application);
        token = tokenValue;
        guardRestRepository = new AvaibleGuardRestRpository(application,tokenValue);
        avaibleGuards = new MutableLiveData<List<GuardDto>>();
        avaibleGuards.setValue(guardRestRepository.execute(new String[]{token}).get());
    }

    public AvaibleGuardRestRpository getGuardRestRepository() {
        return guardRestRepository;
    }

    public void setGuardRestRepository(AvaibleGuardRestRpository guardRestRepository) {
        this.guardRestRepository = guardRestRepository;
    }

    public MutableLiveData<List<GuardDto>> getMyGuards() {
        return avaibleGuards;
    }
}
