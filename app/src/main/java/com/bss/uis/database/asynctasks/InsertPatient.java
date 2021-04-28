package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.PatientDao;
import com.bss.uis.database.entity.PatientImages;
import com.bss.uis.database.relation.PatientWithImage;

public class InsertPatient extends AsyncTask<PatientWithImage, Void, Void> {
    private PatientDao patientDao;

    public InsertPatient(PatientDao patientDao) {
        this.patientDao = patientDao;
    }

    @Override
    protected Void doInBackground(PatientWithImage... patientWithImages) {

        long identifier = patientDao.insertPatient(patientWithImages[0].patient);

        for (PatientImages patientImages :patientWithImages[0].imagesList) {
            patientImages.setImageId_patientId((int) identifier);
        }
        patientDao.insertPatientImages(patientWithImages[0].imagesList);
        return null;
    }
}
