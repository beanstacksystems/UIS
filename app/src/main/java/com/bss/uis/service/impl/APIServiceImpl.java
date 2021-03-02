package com.bss.uis.service.impl;

import com.bss.uis.constant.APIConstant;
import com.bss.uis.model.AddressDTO;
import com.bss.uis.model.PinAddressDTO;
import com.bss.uis.service.APIService;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.util.CustomJsonDesrializer;
import com.bss.uis.util.RetrofitUtil;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APIServiceImpl implements APIService{
    private APISignatureService apiSignatureService;
    @Override
    public List<AddressDTO> fetchPinData(String pincode) {
        final List<AddressDTO> addressDTOList = new ArrayList<>();
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.pinApi,
        new GsonBuilder()
                .registerTypeAdapter(AddressDTO.class, new CustomJsonDesrializer<>(ArrayList.class,"PostOffice"))
                .create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<List<AddressDTO>> apiCall = apiSignatureService.fetchPinData(pincode);
        apiCall.enqueue(new Callback<List<AddressDTO>>() {
            @Override
            public void onResponse(Call<List<AddressDTO>> call, Response<List<AddressDTO>> response) {
                System.out.println(response.body());
                //In this point we got our hero list
                //thats damn easy right ;)
                addressDTOList.addAll(response.body());
                //now we can do whatever we want with this list
            }


            @Override
            public void onFailure(Call<List<AddressDTO>> call, Throwable t) {
                System.out.println(call);
            }
        });
        return addressDTOList;
    }
}
