package com.bss.uis.ui.navDrawer.ui.patient;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

public class PatientViewModel extends ViewModel {

    private MutableLiveData<String> mText;

    public PatientViewModel() {
        mText = new MutableLiveData<>();
        mText.setValue("This is Patient fragment");
    }

    public LiveData<String> getText() {
        return mText;
    }
}