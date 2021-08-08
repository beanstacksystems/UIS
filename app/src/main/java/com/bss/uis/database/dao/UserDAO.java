package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;

import com.bss.uis.database.entity.UserRightData;

import java.util.List;

@Dao
public interface UserDAO {
    @Query("SELECT * FROM UserRightData")
    public List<UserRightData> findAll();

    @Query("SELECT * FROM UserRightData WHERE userRoleId is:userRoleId")
    public List<UserRightData> findByUserRightdataRoleType(int userRoleId);

    @Transaction
    @Insert
    void insertUserData(List<UserRightData> userRightDataList);
}
