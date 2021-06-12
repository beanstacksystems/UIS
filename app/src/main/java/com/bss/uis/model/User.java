package com.bss.uis.model;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements Comparable<User>{
    private String loginToken;
    private String userEmail, userName;
    private String userRole;
    private String loginType;
    private String idUser;


    @Override
    public int compareTo(User o) {
        if (this == o) return 0;

        return -1;
    }


}
