package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "staticData")
public class StaticData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "dataId")
    private int dataId;
    @ColumnInfo(name = "dataType")
    private String dataType;
    @ColumnInfo(name = "dataValue1")
    private String dataValue1;
    @ColumnInfo(name = "dataValue2")
    private String dataValue2;
    @ColumnInfo(name = "dataValue3")
    private String dataValue3;
    @ColumnInfo(name = "dataImage")
    private int dataImage;
}
