package com.beanstack.upipayment.model;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {
    private String transactionId;
    private String responseCode;
    private String approvalRefNo;
    private String status;
    private String transactionRefId;
    private String amount;
    private String name;
    private String currency;
    private String description;
    private String recieverUpiId;


    public TransactionDetails(
            String transactionId,
            String responseCode,
            String approvalRefNo,
            String status,
            String transactionRefId,
            String amount, String name, String currency, String description, String recieverUpiId
    ) {
        this.transactionId = transactionId;
        this.responseCode = responseCode;
        this.approvalRefNo = approvalRefNo;
        this.status = status;
        this.transactionRefId = transactionRefId;
        this.amount = amount;
        this.name = name;
        this.currency = currency;
        this.description = description;
        this.recieverUpiId = recieverUpiId;
    }

    @NonNull
    @Override
    public String toString() {
        return "transactionId:" + transactionId +
                ", responseCode:" + responseCode +
                ", transactionRefId:" + transactionRefId +
                ", approvalRefNo:" + approvalRefNo +
                ", status:" + status +
                ", amount:" + amount;
    }
}
