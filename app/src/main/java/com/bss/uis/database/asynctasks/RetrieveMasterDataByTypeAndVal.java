package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.entity.MasterData;

public class RetrieveMasterDataByTypeAndVal extends AsyncTask<Void, Void, MasterData> {
    private MasterDAO masterDao;
    private String masterType;
    private String masterVal;

    public RetrieveMasterDataByTypeAndVal(MasterDAO masterDao,String  masterType,String masterVal) {
        this.masterDao = masterDao;
        this.masterType = masterType;
        this.masterVal = masterVal;
    }

    @Override
    protected MasterData doInBackground(Void ...arg) {
        return masterDao.findByMasterdataTypeAndVal(masterType,masterVal);
    }
}
