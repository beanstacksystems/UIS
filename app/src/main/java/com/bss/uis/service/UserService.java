package com.bss.uis.service;

import com.beanstack.utility.service.NavigationService;
import com.bss.uis.model.User;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;

public interface UserService {
    void registerWithSocialId(String idToken, String authCode,String serial,String model, String source,NavigationService navigationService);
    void loginUser(String userName, String password, NavigationService navigationService, LoginSignupActivity loginSignupActivity);
    void resetPassword(String userName, String password, NavigationService navigationService, LoginSignupActivity loginSignupActivity);
    void registerUser(String userName, String email,String password,String serial,String model,String loginType,NavigationService navigationService,LoginSignupActivity loginSignupActivity);
    void getUser(String userId);
    void updateUser(User user);
    void pullUserData(NavigationService navigationService);
    void logout(NavigationService navigationService);
    void isValidAccessToken();
}
