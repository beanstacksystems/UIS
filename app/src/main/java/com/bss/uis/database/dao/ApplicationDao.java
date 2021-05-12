package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bss.uis.database.entity.AppConfig;

import java.util.List;

@Dao
public interface ApplicationDao {
    @Query("SELECT * FROM AppConfig")
    public List<AppConfig> findAll();

    @Transaction
    @Insert
    long insertAppConfig(AppConfig appConfig);
}
