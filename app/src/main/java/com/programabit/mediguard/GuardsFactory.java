package com.programabit.mediguard;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.programabit.mediguard.rest.GuardsViewModel;

import java.util.concurrent.ExecutionException;

public class GuardsFactory extends ViewModelProvider.NewInstanceFactory {
    private String token;
    @NonNull
    private final Application application;

    public GuardsFactory (@NonNull Application application, String token){
        this.application = application;
        this.token=token;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        if(modelClass == GuardsViewModel.class){
            try {
                return (T) new GuardsViewModel(application,token);
            } catch (ExecutionException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        return null;
    }

}



