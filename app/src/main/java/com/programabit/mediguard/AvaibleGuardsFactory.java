package com.programabit.mediguard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.programabit.mediguard.rest.AvaibleGuardViewModel;

import java.util.concurrent.ExecutionException;

public class AvaibleGuardsFactory extends ViewModelProvider.NewInstanceFactory {
    private String token;
    @NonNull
    private final Application application;

    public AvaibleGuardsFactory (@NonNull Application application, String token){
        this.application = application;
        this.token=token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == AvaibleGuardViewModel.class){
           return (T) new AvaibleGuardViewModel(application,token);
        }
        return null;
    }
}
