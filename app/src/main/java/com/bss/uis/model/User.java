package com.bss.uis.model;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements Comparable<User>{
    private String salutation,useremail, username;
    private int userid;
    private String logintype;
    private String gender;
    private List<UserRole> userrole;
    private String imageurl;
    private String dob;
    private String occupation;
    public  int personid;

    @Override
    public int compareTo(User o) {
        if (this == o) return 0;

        return -1;
    }


}