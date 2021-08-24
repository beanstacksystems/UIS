package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.entity.HomeTabData;

import java.util.Arrays;


public class InsertTabData extends AsyncTask<HomeTabData, Void, Void> {
    private MasterDAO masterDao;

    public InsertTabData(MasterDAO masterDao) {
        this.masterDao = masterDao;
    }

    @Override
    protected Void doInBackground(HomeTabData... homeTabData) {
         masterDao.insertTabData(Arrays.asList(homeTabData));
         return null;
    }
}
