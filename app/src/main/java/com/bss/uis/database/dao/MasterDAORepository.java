package com.bss.uis.database.dao;

import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.DeleteMasterData;
import com.bss.uis.database.asynctasks.DeleteTabData;
import com.bss.uis.database.asynctasks.InsertMasterData;
import com.bss.uis.database.asynctasks.InsertTabData;
import com.bss.uis.database.asynctasks.RetrieveMasterDataByType;
import com.bss.uis.database.asynctasks.RetrieveMasterDataByTypeAndVal;
import com.bss.uis.database.asynctasks.RetrieveTabData;
import com.bss.uis.database.entity.HomeTabData;
import com.bss.uis.database.entity.MasterData;

import java.util.List;

import lombok.SneakyThrows;

public class MasterDAORepository {
    private MasterDAO masterDAO;
    public MasterDAORepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context).getUisDatabase();
        masterDAO = database.masterDAO();
    }
    public void insert(List<MasterData> masterDataList) {
        new InsertMasterData(masterDAO).execute(masterDataList.toArray(new MasterData[0]));
    }
    public void insertTab(List<HomeTabData> tabList) {
        new InsertTabData(masterDAO).execute(tabList.toArray(new HomeTabData[0]));
    }
    @SneakyThrows
    public List<MasterData> getMasterByType(String masterType) {
        return new RetrieveMasterDataByType(masterDAO,masterType).execute().get();
    }
    @SneakyThrows
    public MasterData getMasterByTypeAndVal(String masterType,String masterVal) {
        return new RetrieveMasterDataByTypeAndVal(masterDAO,masterType,masterVal).execute().get();
    }
    @SneakyThrows
    public List<HomeTabData> getTabData() {
        return new RetrieveTabData(masterDAO).execute().get();
    }
    public void deleteMaster()
    {
        new DeleteMasterData(masterDAO).execute();
    }
    public void deleteTabData()
    {
        new DeleteTabData(masterDAO).execute();
    }
}
