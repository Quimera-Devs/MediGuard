package com.programabit.mediguard;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface UserService {


    @POST("api/api_login/")
    Call<LoginResponse> userLogin(@Body LoginRequest loginRequest);


}