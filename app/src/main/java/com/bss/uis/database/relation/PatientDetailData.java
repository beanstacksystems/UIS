package com.bss.uis.database.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.bss.uis.database.entity.Address;
import com.bss.uis.database.entity.MedicalHistory;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientAttendant;
import com.bss.uis.database.entity.PatientImages;

import java.util.List;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class PatientDetailData {
    @Embedded
    public Patient patient;
    @Relation(
            parentColumn = "patientId",
            entityColumn = "imageId"
    )
    public List<PatientImages> imagesList;

    @Relation(
            parentColumn = "patientId",
            entityColumn = "addressId"
    )
    public Address address;
    @Relation(
            parentColumn = "patientId",
            entityColumn = "medicalHistoryId"
    )
    public MedicalHistory medicalHistory;
    @Relation(
            parentColumn = "patientId",
            entityColumn = "attendantId"
    )
    public List<PatientAttendant> patientAttendants;
    public PatientDetailData(Patient patient, List<PatientImages> imagesList,
                             Address address,MedicalHistory medicalHistory,
                             List<PatientAttendant> patientAttendants)
    {
        this.patient = patient;
        this.imagesList = imagesList;
        this.address = address;
        this.medicalHistory = medicalHistory;
        this.patientAttendants = patientAttendants;
    }

}
