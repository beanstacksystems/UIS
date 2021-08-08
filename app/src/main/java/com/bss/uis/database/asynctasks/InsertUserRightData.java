package com.bss.uis.database.asynctasks;

import android.os.AsyncTask;

import com.bss.uis.database.dao.UserDAO;
import com.bss.uis.database.entity.UserRightData;

import java.util.Arrays;


public class InsertUserRightData extends AsyncTask<UserRightData, Void, Void> {
    private UserDAO userDAO;

    public InsertUserRightData(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Override
    protected Void doInBackground(UserRightData... userRightData) {
        userDAO.insertUserData(Arrays.asList(userRightData));
         return null;
    }
}
