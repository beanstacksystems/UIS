package com.bss.uis.service.impl;

import android.util.Log;
import android.widget.Toast;

import com.bss.uis.R;
import com.bss.uis.constant.APIConstant;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.model.AuthResponse;
import com.bss.uis.model.User;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.service.AuthService;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.UserService;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class UserServiceImpl implements UserService {

    private static final String TAG = "UserServiceImpl";
    private AuthService authService;
    private APISignatureService apiSignatureService;

    @Override
    public void registerWithSocialId(final String idToken, String authCode, String source,
                                     final NavigationService navigationService) {

        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.SOCIAL_URL+source+"/",
                new GsonBuilder().create());
        authService = retrofit.create(AuthService.class);
        Call<AuthResponse> apiCall = authService.registerWithSocialId(source.equals("google")?authCode:idToken);
        apiCall.enqueue(new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                System.out.println(response.body());
                navigationService.finishAndnavigate();
            }

            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                System.out.println(t.getStackTrace());
            }
        });

    }

    @Override
    public void loginUser(String userName, String password, final NavigationService navigationService,final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.LOGIN_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<User> apiCall = apiSignatureService.login(userName,password);
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               if(null != response.body()){
                    UISApplicationContext.getInstance().setUser(response.body());
                    loginSignupActivity.finish();
                    navigationService.navigate();
                    return;
                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            loginSignupActivity.getResources().getString(R.string.loginfailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        loginSignupActivity.getResources().getString(R.string.severconnectionfailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }
    @Override
    public void resetPassword(String userName, String password,
                              final NavigationService navigationService,
                              final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.RESET_PWD,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<User> apiCall = apiSignatureService.resetPassword(userName,password);
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(null != response.body()){
                    UISApplicationContext.getInstance().setUser(response.body());
                    loginSignupActivity.finish();
                    navigationService.navigate();
                    return;
                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            loginSignupActivity.getResources().getString(R.string.loginfailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        loginSignupActivity.getResources().getString(R.string.severconnectionfailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }

    @Override
    public void registerUser(String userName, String userEmail, String password,String loginType,
                             final NavigationService navigationService,final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.REGISTER_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<User> apiCall = apiSignatureService.register(userName,userEmail,password,loginType);
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
               if(null != response.body()){
                    UISApplicationContext.getInstance().setUser(response.body());
                    loginSignupActivity.finish();
                    navigationService.navigate();
                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            loginSignupActivity.getResources().getString(R.string.userregistraionFailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        loginSignupActivity.getResources().getString(R.string.severconnectionfailed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
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
