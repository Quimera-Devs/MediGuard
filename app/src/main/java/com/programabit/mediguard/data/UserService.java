package com.programabit.mediguard.data;

import com.programabit.mediguard.domain.GuardDto;
import com.programabit.mediguard.domain.MedicDto;
import com.programabit.mediguard.ui.ResponseClass;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.PATCH;
import retrofit2.http.POST;
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

    @PATCH("/modificar_guardia/{id}/")
    Call<GuardDto> patchGuard(@Path("id") int Id,
                             @Query("idMedic") int idMedic,
                             @Query("available") boolean available,
                             @Header("Authorization") String token);

    @POST("/medico_datos/profile_photos")
    @FormUrlEncoded
    Call<ResponseClass> UploadImage(@Field("name") String name, @Field("image") String image);


}