package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "masterData")
public class MasterData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "masterdatapkId")
    private int masterdatapkId;
    @ColumnInfo(name = "masterdataType")
    private String masterdataType;
    @ColumnInfo(name = "masterdataId")
    private int masterdataId;
    @ColumnInfo(name = "masterdataval")
    private String masterdataval;
    @ColumnInfo(name = "isactive")
    private String isactive;
    @ColumnInfo(name = "masterdatadesc")
    private String masterdatadesc;
}
