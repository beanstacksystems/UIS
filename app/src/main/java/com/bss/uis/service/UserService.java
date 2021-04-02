package com.bss.uis.service;

import com.bss.uis.model.User;

public interface UserService {
    void registerUser(String accessToken);
    void getUser(String userId);
    void updateUser(User user);
}
