package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.entity.MasterData;

import java.util.Arrays;


public class InsertMasterData extends AsyncTask<MasterData, Void, Void> {
    private MasterDAO masterDao;

    public InsertMasterData(MasterDAO masterDao) {
        this.masterDao = masterDao;
    }

    @Override
    protected Void doInBackground(MasterData... masterData) {
         masterDao.insertMasterData(Arrays.asList(masterData));
         return null;
    }
}
