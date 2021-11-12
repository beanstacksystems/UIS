package com.bss.uis.service.impl;

import com.bss.uis.constant.APIConstant;
import com.bss.uis.model.PatientDTO;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.service.PatientService;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Retrofit;

public class PatientServiceImpl implements PatientService {
    private APISignatureService apiSignatureService;
    @Override
    public void registerPatient() {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.REGISTER_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<PatientDTO> apiCall = apiSignatureService.registerPatient("");
       // apiCall.enqueue(getCallBack(navigationService,"UIS", R.string.userregistraionFailed,R.string.severconnectionfailed,true));
    }
}
