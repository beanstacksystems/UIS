package com.beanstack.upipayment.model;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Payment implements Serializable {
    private final String currency = "INR";
    private String recieverUPIId;
    private String name;
    private String payeeMerchantCode;
    private String txnId;
    private String txnRefId;
    private String description;
    private String amount;
    private String defaultPackage;
}
