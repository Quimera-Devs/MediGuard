package com.programabit.mediguard.domain;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.programabit.mediguard.data.AvaibleGuardRestRpository;

import java.util.List;
import java.util.concurrent.ExecutionException;

public class AvaibleGuardViewModel extends AndroidViewModel {
    private AvaibleGuardRestRpository guardRestRepository;
    private final MutableLiveData<List<GuardDto>> avaibleGuards;
    private String token;
    private Application myapplication;


    public AvaibleGuardViewModel(Application application, String tokenValue)
            throws ExecutionException, InterruptedException {
        super(application);
        token = tokenValue;
        myapplication = application;
        guardRestRepository = new AvaibleGuardRestRpository(application,tokenValue);
        avaibleGuards = new MutableLiveData<List<GuardDto>>();
        avaibleGuards.setValue(guardRestRepository.execute(new String[]{token,"getAvailableGuards"}).get());
    }

    public void assing(GuardDto selectedGuard) throws ExecutionException, InterruptedException {
        guardRestRepository = new AvaibleGuardRestRpository(myapplication,token);
        guardRestRepository.setSelectedGuard(selectedGuard);
        guardRestRepository.execute(new String[]{token,"assignGuards"}).get();
        Log.i("GuardsViewModel", "assign complete");
        guardRestRepository = new AvaibleGuardRestRpository(myapplication,token);
        avaibleGuards.setValue(guardRestRepository.execute(new String[]{token,"getAvailableGuards"}).get());
    }

    public void assingFromNotify(int idGuard, int idMedic, boolean isAvailable)
            throws ExecutionException, InterruptedException {

        GuardDto notiffGuard = new GuardDto();
        notiffGuard.setId(idGuard);
        notiffGuard.setMedico(idMedic);
        notiffGuard.setDisponible(isAvailable);
        guardRestRepository = new AvaibleGuardRestRpository(myapplication,token);
        guardRestRepository.setSelectedGuard(notiffGuard);
        guardRestRepository.execute(new String[]{token,"assignGuardsNotiff"}).get();
        Log.i("GuardsViewModel", "assign complete");
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
