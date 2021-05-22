package com.bss.uis.database.dao;

import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Transaction;
import androidx.room.Update;

import com.bss.uis.database.entity.Address;
import com.bss.uis.database.entity.MedicalHistory;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientAttendant;
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
    @Insert
    void insertPatientAddress(Address address);
    @Insert
    void insertMedicalHistory(MedicalHistory medicalHistory);
    @Insert
    void insertPatientAttendant(List<PatientAttendant> patientAttendants);
    @Transaction
    @Delete
    void deletePatient(Patient patient);
    @Delete
    void deletePatientImages(List<PatientImages> patientImageList);
    @Delete
    void deletePatientAddress(Address address);
    @Delete
    void deleteMedicalHistory(MedicalHistory medicalHistory);
    @Delete
    void deletePatientAttendant(List<PatientAttendant> patientAttendants);
    @Transaction
    @Update
    void updatePatient(Patient patient);
    @Update
    void updatePatientImages(PatientImages patientImages);

}
