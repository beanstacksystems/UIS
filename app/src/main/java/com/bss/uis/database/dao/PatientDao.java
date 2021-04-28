package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientImages;

import java.util.List;

@Dao
public interface PatientDao {
    @Query("SELECT * FROM Patient")
    List<Patient> getAll();

    @Query("SELECT * FROM Patient WHERE patientId Is:patientId")
    List<Patient> findByPatientId(int patientId);
    @Transaction
    @Insert
    long insertPatient(Patient patient);
    @Insert
    void insertPatientImages(List<PatientImages> patientImageList);
    @Transaction
    @Delete
    void deletePatient(Patient patient);
    @Delete
    void deletePatientImages(PatientImages patientImages);
    @Transaction
    @Update
    void updatePatient(Patient patient);
    @Update
    void updatePatientImages(PatientImages patientImages);

}
