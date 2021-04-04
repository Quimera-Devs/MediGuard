package com.programabit.mediguard.rest;

import com.programabit.mediguard.LoginRequest;
import com.programabit.mediguard.LoginResponse;
import com.programabit.mediguard.MisGuardiasDto;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface UserService {


    @POST("api/api_login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("/mis_guardias")
    Call<List<MisGuardiasDto>> getMisGuardias();

}