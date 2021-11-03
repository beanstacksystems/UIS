package com.bss.uis.service;

import com.bss.uis.model.AddressDTO;
import com.bss.uis.model.AuthResponse;
import com.bss.uis.model.MasterValueDTO;
import com.bss.uis.model.TabValueDTO;
import com.bss.uis.model.User;
import com.bss.uis.model.UserRightDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APISignatureService {

    @GET("{pin}")
    Call<List<AddressDTO>> fetchPinData(@Path(value = "pin") String pin);

    @FormUrlEncoded
    @POST(".")
    Call<AuthResponse> login(@Field("useremail") String userName, @Field("password")String password);
    @FormUrlEncoded
    @POST(".")
    Call<AuthResponse> resetPassword(@Field("username") String userName, @Field("password")String password);
    @FormUrlEncoded
    @POST(".")
    Call<AuthResponse> register(@Field("username") String userName,@Field("useremail") String userEmail,
                        @Field("password")String password,@Field("deviceid")String serial,@Field("devicemodel")String model,@Field("regtype") String loginType);
    @POST(".")
    Call<List<MasterValueDTO>> masters(@Header("Authorization")String token);
    @POST(".")
    Call<List<TabValueDTO>> tabs(@Header("Authorization")String token);
    @POST(".")
    Call<User> user(@Header("Authorization")String token);
    @FormUrlEncoded
    @POST(".")
    Call<List<UserRightDTO>> userrights(@Header("Authorization")String token, @Field("roleidlist[]")List<Integer> roleidlist);
    @POST(".")
    Call<String> logout(@Header("Authorization")String token);
    @POST(".")
    Call<String> isserverreachable();
}
