package com.bss.uis.service;

import com.bss.uis.model.AddressDTO;
import com.bss.uis.model.PinAddressDTO;

import java.util.List;

public interface APIService {
    List<AddressDTO> fetchPinData(String pincode);
}
