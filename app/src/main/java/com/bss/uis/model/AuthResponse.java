package com.bss.uis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private final String token;
    private final String token_type = "Bearer";
    private String expires_in;
    private Object respData;

    public AuthResponse(String accessToken) {
        this.token = accessToken;
    }


}
