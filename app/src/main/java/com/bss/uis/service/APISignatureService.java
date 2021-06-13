package com.bss.uis.service;

import com.bss.uis.model.AddressDTO;
import com.bss.uis.model.User;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface APISignatureService {

    @GET("{pin}")
    Call<List<AddressDTO>> fetchPinData(@Path(value = "pin") String pin);
    @FormUrlEncoded
    @POST(".")
    Call<User> login(@Field("userName") String userName, @Field("password")String password);
    @FormUrlEncoded
    @POST(".")
    Call<User> resetPassword(@Field("userName") String userName, @Field("password")String password);
    @FormUrlEncoded
    @POST(".")
    Call<User> register(@Field("userName") String userName,@Field("userEmail") String userEmail,
                        @Field("password")String password,@Field("loginType") String loginType);
}
