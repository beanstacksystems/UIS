package com.bss.uis.database.dao;


import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.InsertPatient;
import com.bss.uis.database.relation.PatientWithImage;


public class PatientRepository {
    private PatientDao patientDao;
    public PatientRepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context).getUisDatabase();
        patientDao = database.patientDao();
    }
    public void insert(PatientWithImage patientWithImage) {
        new InsertPatient(patientDao).execute(patientWithImage);
    }
}
