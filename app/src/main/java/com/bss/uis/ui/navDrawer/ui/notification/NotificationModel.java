package com.bss.uis.ui.navDrawer.ui.notification;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class NotificationModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public NotificationModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Notification fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}