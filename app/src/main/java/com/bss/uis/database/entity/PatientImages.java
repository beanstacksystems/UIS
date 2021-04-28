package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity(tableName = "PatientImages",foreignKeys = @ForeignKey(entity = Patient.class,
        parentColumns = "patientId",
        childColumns = "patientId",
        onDelete = ForeignKey.NO_ACTION))
public class PatientImages {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "imageId")
    private int imageId;
    @ColumnInfo(name = "imageType")
    private String imageType;
    @ColumnInfo(name = "imageStr")
    private String imageStr;
    @ColumnInfo(name = "patientId")
    private int imageId_patientId;
}

