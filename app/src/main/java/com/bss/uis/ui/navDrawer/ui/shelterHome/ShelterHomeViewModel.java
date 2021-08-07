package com.bss.uis.ui.navDrawer.ui.shelterHome;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class ShelterHomeViewModel extends ViewModel {

    private final MutableLiveData<String> mText;

    public ShelterHomeViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is shelterHome fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}