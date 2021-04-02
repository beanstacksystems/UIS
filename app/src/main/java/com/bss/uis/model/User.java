package com.bss.uis.model;

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


    @Override
    public int compareTo(User o) {
        if (this == o) return 0;

        return -1;
    }


}
