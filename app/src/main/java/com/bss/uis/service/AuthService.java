package com.bss.uis.service;

import com.bss.uis.model.AuthResponse;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface AuthService {

    @POST("auth/validate/{registrationId}")
    Call<AuthResponse> validate(@Header("idToken") String idToken, @Header("authCode") String authCode, @Path(value = "registrationId") String registrationId);
    @FormUrlEncoded
    @POST(".")
    Call<AuthResponse> registerWithSocialId(@Field("token") String token,@Field("deviceid") String serial,@Field("devicemodel") String model);
}
