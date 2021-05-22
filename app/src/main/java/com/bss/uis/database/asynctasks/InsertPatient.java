package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.PatientDao;
import com.bss.uis.database.entity.PatientAttendant;
import com.bss.uis.database.entity.PatientImages;
import com.bss.uis.database.relation.PatientDetailData;

public class InsertPatient extends AsyncTask<PatientDetailData, Void, Void> {
    private PatientDao patientDao;

    public InsertPatient(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    protected Void doInBackground(PatientDetailData... patientDetailData) {

        long identifier = patientDao.insertPatient(patientDetailData[0].patient);

        for (PatientImages patientImages :patientDetailData[0].imagesList) {
            patientImages.setImageId_patientId((int) identifier);
        }
        patientDao.insertPatientImages(patientDetailData[0].imagesList);
        for (PatientAttendant patientAttendant :patientDetailData[0].patientAttendants) {
            patientAttendant.setAttendantId_patientId((int) identifier);
        }
        patientDao.insertPatientAttendant(patientDetailData[0].patientAttendants);
        patientDao.insertPatientAddress(patientDetailData[0].address);
        patientDao.insertMedicalHistory(patientDetailData[0].medicalHistory);
        return null;
    }
}
