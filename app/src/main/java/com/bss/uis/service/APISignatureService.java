package com.bss.uis.service;

import com.bss.uis.model.AddressDTO;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APISignatureService {

    @GET("{pin}")
    Call<List<AddressDTO>> fetchPinData(@Path(value = "pin") String pin);
}
