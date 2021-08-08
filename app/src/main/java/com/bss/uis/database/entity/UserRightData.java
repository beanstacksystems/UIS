package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "userrightdata")
public class UserRightData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "userRightDataId")
    private int userRightDataId;
    @ColumnInfo(name = "userRightId")
    private int userRightId;
    @ColumnInfo(name = "userRightType")
    private String userRightType;
    @ColumnInfo(name = "desc")
    private String desc;
    @ColumnInfo(name = "userRoleId")
    private int userRoleId;
}
