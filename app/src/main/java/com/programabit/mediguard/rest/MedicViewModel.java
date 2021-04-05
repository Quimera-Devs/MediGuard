package com.programabit.mediguard.rest;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;

public class MedicViewModel extends AndroidViewModel {
    private MedicRestRepository medicRestRepository;
    private final MedicDto myself;

    private MedicViewModel(Application application){
        super(application);
        medicRestRepository = new MedicRestRepository(application);
        myself = medicRestRepository.getMyself();
    }

    public MedicRestRepository getMedicRestRepository() {
        return medicRestRepository;
    }

    public void setMedicRestRepository(MedicRestRepository medicRestRepository) {
        this.medicRestRepository = medicRestRepository;
    }

    public MedicDto getMyself() {
        return myself;
    }
}
