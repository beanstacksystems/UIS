package com.beanstack.upipayment.listener;

import com.beanstack.upipayment.model.TransactionDetails;

public interface PaymentStatusListener {
    void onTransactionCompleted(TransactionDetails transactionDetails);

    void onTransactionSuccess(TransactionDetails transactionDetails);

    void onTransactionSubmitted(TransactionDetails transactionDetails);

    void onTransactionFailed(TransactionDetails transactionDetails);

    void onTransactionCancelled();

    void onAppNotFound();
}
