package com.bss.uis.database.dao;


import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.InsertPatient;
import com.bss.uis.database.relation.PatientDetailData;


public class PatientRepository {
    private final PatientDao patientDao;
    public PatientRepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context).getUisDatabase();
        patientDao = database.patientDao();
    }
    public void insert(PatientDetailData patientDetailData) {
        new InsertPatient(patientDao).execute(patientDetailData);
    }
}
