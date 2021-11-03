package com.bss.uis.service;

import android.content.Context;

import com.beanstack.utility.service.NavigationService;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public interface APIService {

    void fetchPinData(String pincode, TextInputEditText state, TextInputEditText dist, TextInputLayout pin);
    void isserverreachable(NavigationService navigationService, Context context);
}
