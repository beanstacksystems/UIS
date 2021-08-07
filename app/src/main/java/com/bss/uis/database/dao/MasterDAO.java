package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bss.uis.database.entity.MasterData;

import java.util.List;

@Dao
public interface MasterDAO {
    @Query("SELECT * FROM MasterData")
    List<MasterData> findAll();

    @Query("SELECT * FROM MasterData WHERE masterdataType is:masterdataType")
    List<MasterData> findByMasterdataType(String masterdataType);

    @Transaction
    @Insert
    void insertMasterData(List<MasterData> masterDataList);
}
