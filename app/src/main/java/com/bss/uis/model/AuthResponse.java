package com.bss.uis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String accessToken;
    private String token_type = "Bearer";
    private String expires_in;

    public AuthResponse(String accessToken) {
        this.accessToken = accessToken;
    }


}
