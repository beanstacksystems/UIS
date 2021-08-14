package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.MasterDAO;
import com.bss.uis.database.entity.MasterData;

import java.util.List;


public class RetrieveMasterDataByType extends AsyncTask<Void, Void, List<MasterData>> {
    private MasterDAO masterDao;
    private String masterType;

    public RetrieveMasterDataByType(MasterDAO masterDao,String  masterType) {
        this.masterDao = masterDao;
        this.masterType = masterType;
    }

    @Override
    protected List<MasterData> doInBackground(Void ...arg) {
         return masterDao.findByMasterdataType(masterType);
    }
}
