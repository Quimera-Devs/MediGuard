package com.programabit.mediguard.rest;

import com.programabit.mediguard.LoginRequest;
import com.programabit.mediguard.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
import retrofit2.http.PUT;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface UserService {

    @POST("api/api_login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);

    @GET("/mis_guardias")
    Call<List<GuardDto>> getMyGuards(@Header("Authorization") String token);

    @GET("/guardias_disponibles")
    Call<List<GuardDto>> getAvailableGuardsGuards(@Header("Authorization") String token);

    @GET("/medico_datos")
    Call<List<MedicDto>> getMedic(@Header("Authorization") String token);

    @PATCH("/modificar_guardia/{id}/")
    Call<GuardDto> editGuard(@Path("id") int Id,
                             @Body GuardDto guard,
                             @Header("Authorization") String token);


}