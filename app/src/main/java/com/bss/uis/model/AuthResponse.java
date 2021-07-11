package com.bss.uis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String token_type = "Bearer";
    private String expires_in;

    public AuthResponse(String token) {
        this.token = token;
    }


}
