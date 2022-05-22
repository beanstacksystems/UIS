package com.bss.uis.service.impl;

import android.util.Log;
import android.widget.Toast;

import com.bss.uis.constant.APIConstant;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.dto.PatientDTO;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.service.PatientService;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class PatientServiceImpl implements PatientService {
    private static final String TAG = "PatientServiceImpl";
    private APISignatureService apiSignatureService;
    @Override
    public void registerPatient(PatientDTO patientDTO) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.REGISTER_URL,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<PatientDTO> apiCall = apiSignatureService.registerPatient("Bearer "+ UISApplicationContext.getInstance().getAuthResponse().getToken(),patientDTO);
        apiCall.enqueue(new Callback<PatientDTO>() {
            @Override
            public void onResponse(Call<PatientDTO> call, Response<PatientDTO> response) {
                if(null != response.body()) {

                }
                else
                    Toast.makeText(UISApplicationContext.getInstance().getContext(),
                            "Patient registration failed",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(response.body()));
            }
            @Override
            public void onFailure(Call<PatientDTO> call, Throwable t) {
                Toast.makeText(UISApplicationContext.getInstance().getContext(),
                        "Api Failed.",Toast.LENGTH_LONG).show();
                Log.w(TAG, String.valueOf(call));
                Log.e(TAG,t.getMessage());
            }
        });
    }
}
