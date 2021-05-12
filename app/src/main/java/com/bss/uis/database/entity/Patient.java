package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "Patient")
public class Patient {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "patientId")
    private int patientId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "idproof")
    private String idproof;
    @ColumnInfo(name = "emailId")
    private String emailId;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "gender")
    private String gender;
    @ColumnInfo(name = "date_of_Birth")
    private String dob;
    @ColumnInfo(name = "address")
    private String address;
    @ColumnInfo(name = "bloodGroup")
    private String bloodGroup;
    @ColumnInfo(name = "suffering_from")
    private String diseasesName;
    @ColumnInfo(name = "socialId")
    private String socialId;

}
