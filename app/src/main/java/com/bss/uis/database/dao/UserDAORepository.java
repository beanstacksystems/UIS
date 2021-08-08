package com.bss.uis.database.dao;

import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.UISDataBaseClient;
import com.bss.uis.database.UISDatabase;
import com.bss.uis.database.asynctasks.InsertUserRightData;
import com.bss.uis.database.entity.UserRightData;

import java.util.List;

public class UserDAORepository {
    private UserDAO userDAO;
    public UserDAORepository(UISApplicationContext context) {
        UISDatabase database = UISDataBaseClient.getInstance(context).getUisDatabase();
        userDAO = database.userDAO();
    }
    public void insert(List<UserRightData> userRightDataList) {
        new InsertUserRightData(userDAO).execute(userRightDataList.toArray(new UserRightData[0]));
    }
}
