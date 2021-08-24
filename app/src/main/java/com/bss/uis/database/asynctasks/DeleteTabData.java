package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;


public class DeleteTabData extends AsyncTask<Void, Void, Void> {
    private MasterDAO masterDao;

    public DeleteTabData(MasterDAO masterDao) {
        this.masterDao = masterDao;
    }

    @Override
    protected Void doInBackground(Void ... data) {
         masterDao.deleteTabData();
         return null;
    }
}
