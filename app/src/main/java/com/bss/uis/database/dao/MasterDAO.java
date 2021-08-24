package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bss.uis.database.entity.HomeTabData;
import com.bss.uis.database.entity.MasterData;

import java.util.List;

@Dao
public interface MasterDAO {
    @Query("SELECT * FROM MasterData")
    public List<MasterData> findAll();

    @Query("SELECT * FROM HomeTabData")
    public List<HomeTabData> findAllTabData();

    @Query("SELECT * FROM MasterData WHERE masterdataType is:masterdataType")
    public List<MasterData> findByMasterdataType(String masterdataType);

    @Transaction
    @Insert
    void insertMasterData(List<MasterData> masterDataList);

    @Transaction
    @Insert
    void insertTabData(List<HomeTabData> homeTabDataList);

    @Query("DELETE FROM MasterData")
    void delete();

    @Query("DELETE FROM HomeTabData")
    void deleteTabData();
}
