package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "medicalhistory",foreignKeys = @ForeignKey(entity = Patient.class,
        parentColumns = "patientId",
        childColumns = "medicalHistoryId",
        onDelete = ForeignKey.NO_ACTION))
public class MedicalHistory {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "medicalHistoryId")
    private int medicalHistoryId;
    @ColumnInfo(name = "bloodGroup")
    private String bloodGroup;
    @ColumnInfo(name = "cancerType")
    private String cancerType;
    @ColumnInfo(name = "identifiedOn")
    private String identifiedOn;
    @ColumnInfo(name = "otherDisease")
    private String otherDisease;

    @ColumnInfo(name = "patientId")
    private int medicalHistoryId_patientId;
}
