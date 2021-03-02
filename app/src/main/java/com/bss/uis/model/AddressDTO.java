package com.bss.uis.model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@AllArgsConstructor
@EqualsAndHashCode
@ToString
@Getter
@Setter
public class AddressDTO {
    @SerializedName("Name")
    @Expose
    public String name;
    @SerializedName("Description")
    @Expose
    public String description;
    @SerializedName("BranchType")
    @Expose
    public String branchType;
    @SerializedName("DeliveryStatus")
    @Expose
    public String deliveryStatus;
    @SerializedName("District")
    @Expose
    public String district;
    @SerializedName("Division")
    @Expose
    public String division;
    @SerializedName("Region")
    @Expose
    public String region;
    @SerializedName("block")
    @Expose
    public String Block;
    @SerializedName("State")
    @Expose
    public String state;
    @SerializedName("Country")
    @Expose
    public String country;
    @SerializedName("Pincode")
    @Expose
    public String pincode;
}

