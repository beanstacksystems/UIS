package com.bss.uis.service.impl;

import com.bss.uis.constant.APIConstant;
import com.bss.uis.model.AddressDTO;
import com.bss.uis.service.APIService;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.util.CustomJsonDesrializer;
import com.bss.uis.util.RetrofitUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APIServiceImpl implements APIService{
    private APISignatureService apiSignatureService;
    @Override
    public void fetchPinData(String pincode, final TextInputEditText state, final TextInputEditText dist,
                             final TextInputLayout pin) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.pinApi,
        new GsonBuilder()
                .registerTypeAdapter(AddressDTO.class, new CustomJsonDesrializer<>(AddressDTO.class,"PostOffice"))
                .create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<List<AddressDTO>> apiCall = apiSignatureService.fetchPinData(pincode);
        apiCall.enqueue(new Callback<List<AddressDTO>>() {
            @Override
            public void onResponse(Call<List<AddressDTO>> call, Response<List<AddressDTO>> response) {
                System.out.println(response.body());
                //In this point we got our hero list
                //thats damn easy right ;)
                if(null == response.body().get(0)){
                    pin.setError("No such pin exist");
                    return;
                }

                state.setText(response.body().get(0).getState());
                dist.setText(response.body().get(0).getDistrict());
                //now we can do whatever we want with this list
            }


            @Override
            public void onFailure(Call<List<AddressDTO>> call, Throwable t) {
                System.out.println(call);
            }
        });
    }
}
