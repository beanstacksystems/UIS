package com.bss.uis.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bss.uis.database.dao.ApplicationDao;
import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.dao.PatientDao;
import com.bss.uis.database.dao.UserDAO;
import com.bss.uis.database.entity.Address;
import com.bss.uis.database.entity.AppConfig;
import com.bss.uis.database.entity.HomeTabData;
import com.bss.uis.database.entity.MasterData;
import com.bss.uis.database.entity.MedicalHistory;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientAttendant;
import com.bss.uis.database.entity.PatientImages;
import com.bss.uis.database.entity.UserRightData;

@Database(entities = {Patient.class, PatientImages.class, AppConfig.class,
        Address.class, MedicalHistory.class, PatientAttendant.class, HomeTabData.class,
        MasterData.class, UserRightData.class}, version = 1)
public abstract class UISDatabase extends RoomDatabase {
    public abstract MasterDAO masterDAO();
    public abstract UserDAO userDAO();
    public abstract PatientDao patientDao();
    public abstract ApplicationDao applicationDao();
}