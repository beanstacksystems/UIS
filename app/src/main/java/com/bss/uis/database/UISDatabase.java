package com.bss.uis.database;

import androidx.room.Database;
import androidx.room.RoomDatabase;

import com.bss.uis.database.dao.ApplicationDao;
import com.bss.uis.database.dao.PatientDao;
import com.bss.uis.database.entity.AppConfig;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientImages;

@Database(entities = {Patient.class, PatientImages.class, AppConfig.class}, version = 1)
public abstract class UISDatabase extends RoomDatabase {
    public abstract PatientDao patientDao();
    public abstract ApplicationDao applicationDao();
}
