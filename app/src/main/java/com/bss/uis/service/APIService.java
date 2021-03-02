package com.bss.uis.service;

import com.google.android.material.textfield.TextInputEditText;

public interface APIService {
    void fetchPinData(String pincode, TextInputEditText state, TextInputEditText dist);
}
