package com.bss.uis.service.impl;

import com.bss.uis.model.AuthResponse;
import com.bss.uis.model.User;
import com.bss.uis.service.AuthService;
import com.bss.uis.service.UserService;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserServiceImpl implements UserService {

    public static final String BASE_URL = "http://192.168.0.101:8080/";

    private AuthService authService;

    @Override
    public void registerAndAuthenticateUser(final String idToken, String authCode, String source) {

        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(BASE_URL,
                new GsonBuilder()
//                        .registerTypeAdapter(AddressDTO.class, new CustomJsonDesrializer<>(AddressDTO.class,"PostOffice"))
                        .create());
        authService = retrofit.create(AuthService.class);
        Call<AuthResponse> apiCall = authService.validate(idToken, authCode, source);
        apiCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                System.out.println(response.body());
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                System.out.println(t.getStackTrace());
            }
        });

    }

    @Override
    public void getUser(String userId) {

    }

    @Override
    public void updateUser(User user) {

    }
}
