package com.programabit.mediguard.rest;

import com.programabit.mediguard.LoginRequest;
import com.programabit.mediguard.LoginResponse;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
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

    @PUT("/modificar_guardia/{id}/")
    Call<GuardDto> editGuard(@Path("id") int Id,
                             //@Body GuardDto guard,
                             @Query("id") int id,
                             @Query("fecha") String fecha,
                             @Query("turno") String turno,
                             @Query("disponible") boolean disponible,
                             @Query("departamento") String departamento,
                             @Query("min_ranking") int min_ranking,
                             @Query("centroSalud") String centro_salud,
                             @Query("medico") int medico,
                             @Header("Authorization") String token);


}