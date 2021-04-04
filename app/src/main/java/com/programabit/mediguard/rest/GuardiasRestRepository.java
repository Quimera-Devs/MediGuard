package com.programabit.mediguard.rest;

public class GuardiasRestRepository {
    private ApiClient apiClient = (ApiClient) ApiClient.getRetrofit().create(UserService.class);
    
}
