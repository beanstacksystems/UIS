package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "attendant",foreignKeys = @ForeignKey(entity = Patient.class,
        parentColumns = "patientId",
        childColumns = "attendantId",
        onDelete = ForeignKey.NO_ACTION))
public class PatientAttendant {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "attendantId")
    private int attendantId;
    @ColumnInfo(name = "name")
    private String name;
    @ColumnInfo(name = "idproof")
    private String idproof;
    @ColumnInfo(name = "emailId")
    private String emailId;
    @ColumnInfo(name = "contact")
    private String contact;
    @ColumnInfo(name = "relation")
    private String relation;
    @ColumnInfo(name = "occupation")
    private String occupation;
    @ColumnInfo(name = "yearlyIncome")
    private String yearlyIncome;
    @ColumnInfo(name = "imageStr")
    private String imageStr;
    @ColumnInfo(name = "patientId")
    private int attendantId_patientId;
}
