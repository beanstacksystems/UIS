package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.entity.HomeTabData;

import java.util.List;


public class RetrieveTabData extends AsyncTask<Void, Void, List<HomeTabData>> {
    private MasterDAO masterDao;

    public RetrieveTabData(MasterDAO masterDao) {
        this.masterDao = masterDao;
    }

    @Override
    protected List<HomeTabData> doInBackground(Void ...arg) {
         return masterDao.findAllTabData();
    }
}
