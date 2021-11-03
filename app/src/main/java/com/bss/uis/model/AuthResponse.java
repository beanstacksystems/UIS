package com.bss.uis.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AuthResponse {
    private String token;
    private String refreshtoken;
    private String token_type = "Bearer";
    private String expires_in;

    public AuthResponse(String accessToken,String refreshtoken) {
        this.token = accessToken;
        this.refreshtoken = refreshtoken;
    }


}
