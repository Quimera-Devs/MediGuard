package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class MedicViewModel extends AndroidViewModel {
    private MedicRestRepository medicRestRepository;
    private static MedicDto myself;

    public MedicViewModel(Application application, String token){
        super(application);
        medicRestRepository = new MedicRestRepository(application, token);
        myself = medicRestRepository.getMyself();
    }

    public MedicRestRepository getMedicRestRepository() {
        return medicRestRepository;
    }

    public void setMedicRestRepository(MedicRestRepository medicRestRepository) {
        this.medicRestRepository = medicRestRepository;
    }

    public static MedicDto getMyself() {

        return myself;
    }
}
