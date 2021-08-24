package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "hometabdata")
public class HomeTabData {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "tabid")
    private int tabid;
    @ColumnInfo(name = "tabname")
    private String tabname;
    @ColumnInfo(name = "tabdesc")
    private String tabdesc;
    @ColumnInfo(name = "tabdata")
    private String tabdata;
    @ColumnInfo(name = "tabseq")
    private int tabseq;
}
