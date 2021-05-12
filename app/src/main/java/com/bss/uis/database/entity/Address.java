package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "address",foreignKeys = @ForeignKey(entity = Patient.class,
        parentColumns = "patientId",
        childColumns = "addressId",
        onDelete = ForeignKey.NO_ACTION))
public class Address {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "addressId")
    private int addressId;
    @ColumnInfo(name = "street")
    private String street;
    @ColumnInfo(name = "city")
    private String city;
    @ColumnInfo(name = "district")
    private String district;
    @ColumnInfo(name = "state")
    private String state;
    @ColumnInfo(name = "pin")
    private String pin;
    @ColumnInfo(name = "patientId")
    private int addressId_patientId;
}
