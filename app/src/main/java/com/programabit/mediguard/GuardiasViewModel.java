package com.programabit.mediguard;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.rest.GuardiasRestRepository;

import java.util.List;

public class GuardiasViewModel extends AndroidViewModel {
    private GuardiasRestRepository guardiasRestRepository;
    private final MutableLiveData<List<MisGuardiasDto>> misGuardias;

    public GuardiasViewModel(Application application){
        super(application);
        guardiasRestRepository = new GuardiasRestRepository(application);
        misGuardias = guardiasRestRepository.loadMisGuardias();
    }

    public GuardiasRestRepository getGuardiasRestRepository() {
        return guardiasRestRepository;
    }

    public void setGuardiasRestRepository(GuardiasRestRepository guardiasRestRepository) {
        this.guardiasRestRepository = guardiasRestRepository;
    }

    public MutableLiveData<List<MisGuardiasDto>> getMisGuardias() {
        return misGuardias;
    }
}
