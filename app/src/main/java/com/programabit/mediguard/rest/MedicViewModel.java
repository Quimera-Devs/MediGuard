package com.programabit.mediguard.rest;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;

public class MedicViewModel extends AndroidViewModel {
    private MedicRestRepository medicRestRepository;
    private static MedicDto myself;
    private String token;
    private boolean ok;

    public MedicViewModel(Application application, String tokenValue){
        super(application);
        Log.i("creating medicviewmodel","aa");
        token = tokenValue;
        medicRestRepository = new MedicRestRepository(application, token);
        Log.i("created medicviewmodel","aa");
        myself = medicRestRepository.getMyself();
    }

    public MedicRestRepository getMedicRestRepository() {
        return medicRestRepository;
    }

    public void setMedicRestRepository(MedicRestRepository medicRestRepository) {
        this.medicRestRepository = medicRestRepository;
    }

    public static MedicDto getMyself() {
        if(myself == null){
            Log.i("medic dto","No medic dto");
        }
        return myself;
    }

    public boolean isOk() {
        return ok;
    }

    public void setOk(boolean ok) {
        this.ok = ok;
    }
}
