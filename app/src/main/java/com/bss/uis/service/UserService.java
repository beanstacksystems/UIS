package com.bss.uis.service;

import com.bss.uis.model.User;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;

public interface UserService {
    void registerAndAuthenticateUser(String idToken, String authCode, String source);
    void loginUser(String userName, String password, NavigationService navigationService, LoginSignupActivity loginSignupActivity);
    void resetPassword(String userName, String password, NavigationService navigationService, LoginSignupActivity loginSignupActivity);
    void registerUser(String userName, String email,String password,String loginType,NavigationService navigationService,LoginSignupActivity loginSignupActivity);
    void getUser(String userId);
    void updateUser(User user);
}
