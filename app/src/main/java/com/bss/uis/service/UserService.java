package com.bss.uis.service;

import com.bss.uis.model.User;

public interface UserService {
    void registerAndAuthenticateUser(String idToken, String authCode, String source);
    void getUser(String userId);
    void updateUser(User user);
}
