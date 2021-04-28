package com.bss.uis.model;

import android.net.Uri;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class User implements Comparable<User>{
    private String deviceId;
    private String email, name;
//    private boolean superAdmin, admin, member, manageingCommittee;
    private long mobileNo;
    private String dob;
    private String idProofNo;
    private String userSigninType;


    @Override
    public int compareTo(User o) {
        if (this == o) return 0;

        return -1;
    }
    class FBUser extends User{
        private final Uri picture;
        private final String id;
        private final String permissions;

        public FBUser(Uri picture, String name, String id, String email, String permissions) {
            this.picture = picture;
            super.name = name;
            this.id = id;
            super.email = email;
            this.permissions = permissions;
            super.userSigninType="facebook";
        }
    }

}
