package com.programabit.mediguard.ui;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.programabit.mediguard.domain.AvaibleGuardViewModel;

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
           try {
             return (T) new AvaibleGuardViewModel(application,token);
           } catch (Exception e) {
               Log.i("exception", "Exception has ocurre while traying to lajksdkfa");
           }
        }
        return null;
    }
}
