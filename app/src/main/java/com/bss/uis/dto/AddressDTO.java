package com.bss.uis.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDTO {
    public String addressid;
    public String entityid;
    public String entitytypeid;
    public String addresstypeid;
    public String addressline1;
    public String addressline2;
    public String addressline3;
    public String city;
    public String district;
    public String state;
    public String country;
    public String pincode;
    public String isactive;
    public String createddate;
    public String updatedate;
}
