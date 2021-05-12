package com.bss.uis.database.entity;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@Entity(tableName = "AppConfig")
public class AppConfig {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "configId")
    private int configId;
    @ColumnInfo(name = "configKey")
    private String configKey;
    @ColumnInfo(name = "configValue")
    private String configValue;
}
