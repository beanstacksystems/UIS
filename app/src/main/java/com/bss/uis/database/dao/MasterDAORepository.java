package com.bss.uis.database.dao;

import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.InsertMasterData;
import com.bss.uis.database.entity.MasterData;

import java.util.List;

public class MasterDAORepository {
    private MasterDAO masterDAO;
    public MasterDAORepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context).getUisDatabase();
        masterDAO = database.masterDAO();
    }
    public void insert(List<MasterData> masterDataList) {
        new InsertMasterData(masterDAO).execute(masterDataList.toArray(new MasterData[0]));
    }
}
