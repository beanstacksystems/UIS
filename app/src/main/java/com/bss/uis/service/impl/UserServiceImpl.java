package com.bss.uis.service.impl;

import android.util.Log;
import android.widget.Toast;

import com.beanstack.utility.service.NavigationService;
import com.bss.uis.R;
import com.bss.uis.constant.APIConstant;
import com.bss.uis.context.ContextPreferenceManager;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.dao.MasterDAORepository;
import com.bss.uis.database.dao.UserDAORepository;
import com.bss.uis.database.entity.MasterData;
import com.bss.uis.database.entity.UserRightData;
import com.bss.uis.model.AuthResponse;
import com.bss.uis.model.MasterValueDTO;
import com.bss.uis.model.User;
import com.bss.uis.model.UserRightDTO;
import com.bss.uis.model.UserRole;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.service.AuthService;
import com.bss.uis.service.UserService;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

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
        apiCall.enqueue(getCallBack(navigationService,source,R.string.userregistraionFailed,R.string.severconnectionfailed,true));
    }

    @Override
    public void loginUser(final String useremail, final String password, final NavigationService navigationService, final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.LOGIN_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<AuthResponse> apiCall = apiSignatureService.login(useremail,password);
        apiCall.enqueue(getCallBack(navigationService,"UIS",R.string.loginfailed,R.string.severconnectionfailed,false));

    }
    @Override
    public void resetPassword(final String useremail, final String password,
                              final NavigationService navigationService,
                              final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.RESET_PWD,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<AuthResponse> apiCall = apiSignatureService.resetPassword(useremail,password);
        apiCall.enqueue(getCallBack(navigationService,"UIS",R.string.loginfailed,R.string.severconnectionfailed,false));
    }

    @Override
    public void registerUser(String username, final String useremail, final String password, final String regtype,
                             final NavigationService navigationService, final LoginSignupActivity loginSignupActivity) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.REGISTER_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<AuthResponse> apiCall = apiSignatureService.register(username,useremail,password,regtype);
        apiCall.enqueue(getCallBack(navigationService,"UIS",R.string.userregistraionFailed,R.string.severconnectionfailed,true));
    }


    @Override
    public void getUser(String userId) {

    }

    @Override
    public void updateUser(User user) {

    }
    @Override
    public void logout(NavigationService navigationService) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.LOGOUT_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<String> apiCall = apiSignatureService.logout("Bearer "+ContextPreferenceManager.getToken());
        apiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(null != response.body() && response.body().equals("Success")) {
                    ContextPreferenceManager.clearLoginInfo();
                    navigationService.exitApp();
                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            UISApplicationContext.getInstance().getContext().getResources().getString(R.string.api_expectation_faied),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        UISApplicationContext.getInstance().getContext().getResources().getString(R.string.api_failed),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }

    private void handleSuccessfulLogin(NavigationService navigationService,AuthResponse authResponse,String source,boolean isRegister)
    {
        UISApplicationContext.getInstance().setAuthResponse(authResponse);
        ContextPreferenceManager.saveLoginDetails(authResponse.getToken(),source);
        Log.w(TAG, String.valueOf(authResponse));
        if(isRegister)pullmasterData();
        pullUserData();
        navigationService.finishAndNavigate();
    }
    private Callback<AuthResponse> getCallBack(NavigationService navigationService,String source,int validationMsg,int failedMsg,boolean isRegister)
    {
        return new Callback<AuthResponse>() {
            @Override
            public void onResponse(Call<AuthResponse> call, Response<AuthResponse> response) {
                if(null != response.body())
                    handleSuccessfulLogin(navigationService,response.body(),source,isRegister);
                else
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        UISApplicationContext.getInstance().getContext().getResources().getString(validationMsg),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<AuthResponse> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        UISApplicationContext.getInstance().getContext().getResources().getString(failedMsg),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        };
    }
    private void pullmasterData()
    {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.MASTERS,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<List<MasterValueDTO>> apiCall = apiSignatureService.masters("Bearer "+UISApplicationContext.getInstance().getAuthResponse().getToken());
        apiCall.enqueue(new Callback<List<MasterValueDTO>>() {
            @Override
            public void onResponse(Call<List<MasterValueDTO>> call, Response<List<MasterValueDTO>> response) {
                if(null != response.body())
                    saveToMasterEntity(response.body());
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            "Master api failed",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<List<MasterValueDTO>> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        UISApplicationContext.getInstance().getContext().getResources().getString(1),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }
    private void saveToMasterEntity(List<MasterValueDTO> masterValueDTOList)
    {
        List<MasterData> masterDataList  = new ArrayList<>();
        for(MasterValueDTO masterValueDTO :masterValueDTOList)
        {
            MasterData masterData = new MasterData();
            masterData.setMasterdatadesc(masterValueDTO.getMasterdatadesc());
            masterData.setMasterdataId(masterValueDTO.getMasterdataId());
            masterData.setMasterdataType(masterValueDTO.getMasterdataType());
            masterData.setMasterdataval(masterValueDTO.getMasterdataval());
            masterData.setIsactive(masterValueDTO.getIsactive());
            masterDataList.add(masterData);
        }
        MasterDAORepository masterDAORepository = new MasterDAORepository(UISApplicationContext.getInstance());
        masterDAORepository.insert(masterDataList);
    }
    private void pullUserData()
    {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.USER,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<User> apiCall = apiSignatureService.user("Bearer "+UISApplicationContext.getInstance().getAuthResponse().getToken());
        apiCall.enqueue(new Callback<User>() {
            @Override
            public void onResponse(Call<User> call, Response<User> response) {
                if(null != response.body()) {
                    UISApplicationContext.getInstance().setUser(response.body());
                    for(UserRole userRole : UISApplicationContext.getInstance().getUser().getUserrole()) {
                        if(userRole.getIsdefaultrole().equals("Y"))
                            UISApplicationContext.getInstance().setUserLoginRole(userRole.getUserroleid());
                    }
                    pullUserRights();
                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            "User api failed",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<User> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        "Api Failed.",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }
    private void pullUserRights()
    {
        List<UserRole> rolelist = UISApplicationContext.getInstance().getUser().getUserrole();
        List<Integer> roleids = new ArrayList<>();
        for (UserRole userRole:rolelist) {
            roleids.add(userRole.getUserroleid());
        }
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.USERRIGHTS,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<List<UserRightDTO>> apiCall = apiSignatureService.userrights("Bearer "+UISApplicationContext.getInstance().getAuthResponse().getToken(),roleids);
        apiCall.enqueue(new Callback<List<UserRightDTO>>() {
            @Override
            public void onResponse(Call<List<UserRightDTO>> call, Response<List<UserRightDTO>> response) {
                if(null != response.body())
                    saveUserRights(response.body());
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            "User api failed",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<List<UserRightDTO>> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        UISApplicationContext.getInstance().getContext().getResources().getString(1),Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }
    private void saveUserRights(List<UserRightDTO> userRightDTOList)
    {
        List<UserRightData> userRightDataList  = new ArrayList<>();
        for(UserRightDTO userRightDTO :userRightDTOList)
        {
            UserRightData userRightData = new UserRightData();
            userRightData.setUserRightId(userRightDTO.getRightid());
            userRightData.setUserRoleId(userRightDTO.getRoleid());
            userRightData.setDesc(userRightDTO.getRightdesc());
            userRightData.setUserRightType(userRightDTO.getRighttype());
            userRightDataList.add(userRightData);
        }
        UISApplicationContext.getInstance().setUserRightDataList(userRightDataList);
        UserDAORepository userDAORepository = new UserDAORepository(UISApplicationContext.getInstance());
        userDAORepository.insert(userRightDataList);
    }
}
