package com.bss.uis.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Entity
@Setter
@Getter
public class Patient {
    @PrimaryKey(autoGenerate = true)
    private String patientId;
    private String name;
    private String emailId;
    private String contact;
    private String gender;
    private String dob;
    private String address;

}
