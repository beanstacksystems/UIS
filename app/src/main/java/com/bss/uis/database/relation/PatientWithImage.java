package com.bss.uis.database.relation;

import androidx.room.Embedded;
import androidx.room.Relation;

import com.bss.uis.database.entity.Patient;
import com.bss.uis.database.entity.PatientImages;

import java.util.List;

public class PatientWithImage {
    @Embedded
    public Patient patient;
    @Relation(
            parentColumn = "patientId",
            entityColumn = "imageId"
    )
    public List<PatientImages> imagesList;

    public PatientWithImage(Patient patient, List<PatientImages> imagesList) {
        this.patient = patient;
        this.imagesList = imagesList;
    }

}
