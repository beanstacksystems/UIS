package com.bss.uis.ui.navDrawer.ui.members;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class MembersViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public MembersViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is member fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}