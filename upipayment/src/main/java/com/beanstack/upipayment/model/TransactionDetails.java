package com.beanstack.upipayment.model;

import androidx.annotation.NonNull;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class TransactionDetails {
    private final String transactionId;
    private final String responseCode;
    private final String approvalRefNo;
    private final String status;
    private final String transactionRefId;
    private final String amount;
    private final String name;
    private final String currency;
    private final String description;
    private final String recieverUpiId;


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
