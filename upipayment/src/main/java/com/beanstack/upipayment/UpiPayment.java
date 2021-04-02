package com.beanstack.upipayment;

import android.app.Activity;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;

import com.beanstack.upipayment.listener.PaymentStatusListener;
import com.beanstack.upipayment.model.Payment;
import com.beanstack.upipayment.model.PaymentApps;
import com.beanstack.upipayment.model.TransactionDetails;

import java.util.HashMap;
import java.util.Map;

public final class UpiPayment {
    public static final String TAG = UpiPayment.class.getSimpleName();
    private Activity mActivity;
    private Payment mPayment;
    private Singleton singleton;

    private UpiPayment(@NonNull final Activity mActivity, @NonNull Payment mPayment) {
        this.mActivity = mActivity;
        this.mPayment = mPayment;
        singleton = Singleton.getInstance();
    }

    public Uri getPaymentUri() {
        Uri.Builder payUri = new Uri.Builder();
        payUri.scheme("upi").authority("pay");
        payUri.appendQueryParameter("pa", mPayment.getRecieverUPIId());
        payUri.appendQueryParameter("pn", mPayment.getName());
        payUri.appendQueryParameter("tid", mPayment.getTxnId());
        if (mPayment.getPayeeMerchantCode() != null) {
            payUri.appendQueryParameter("mc", mPayment.getPayeeMerchantCode());
        }
        payUri.appendQueryParameter("tr", mPayment.getTxnRefId());
        payUri.appendQueryParameter("tn", mPayment.getDescription());
        payUri.appendQueryParameter("am", mPayment.getAmount());
        payUri.appendQueryParameter("cu", mPayment.getCurrency());
        // Build URI
        Uri uri = payUri.build();
        return uri;
    }

    public void handleResponseData(String data) {
        if (data != null) {
            try {
                TransactionDetails transactionDetails = getTransactionDetails(data);
                singleton.getListener().onTransactionCompleted(transactionDetails);
                String status = transactionDetails.getStatus().toLowerCase();
                if (status.equals("success")) {
                    singleton.getListener().onTransactionSuccess(transactionDetails);
                } else if (status.equals("submitted")) {
                    singleton.getListener().onTransactionSubmitted(transactionDetails);
                } else {
                    singleton.getListener().onTransactionFailed(transactionDetails);
                }
            } catch (Exception e) {
                singleton.getListener().onTransactionCancelled();
                singleton.getListener().onTransactionFailed(null);
            }

        } else {
            Log.e(TAG, "Intent Data is null. User cancelled");
            singleton.getListener().onTransactionCancelled();
        }
    }

    /**
     * Sets the PaymentStatusListener.
     *
     * @param mListener Implementation of PaymentStatusListener
     */
    public void setPaymentStatusListener(@NonNull PaymentStatusListener mListener) {
        Singleton singleton = Singleton.getInstance();
        singleton.setListener(mListener);
    }


    public void setDefaultPaymentApp(@NonNull PaymentApps mPaymentApp) {
        boolean isInstalled = isPackageInstalled(mPaymentApp.getMPackageName(),
                mActivity.getPackageManager());

        if (mPaymentApp == PaymentApps.NONE) {
            mPayment.setDefaultPackage(null);
            return;
        }
        // If app isn't exist, log error and return
        if (!isInstalled) {
            Log.e(TAG, "UPI App with package '" + mPayment.getDefaultPackage() +
                    "' is not installed on this device.");

            // Listener Callback
            if (Singleton.getInstance().isListenerRegistered()) {
                Singleton.getInstance().getListener().onAppNotFound();
            }
            // Set NONE package
            mPayment.setDefaultPackage(PaymentApps.NONE.getMPackageName());

            return;
        }

        mPayment.setDefaultPackage(mPaymentApp.getMPackageName());
    }

    /**
     * Removes the PaymentStatusListener which is already registered.
     */
    public void detachListener() {
        Singleton.getInstance().detachListener();
    }

    private boolean isPackageInstalled(String packageName, PackageManager packageManager) {
        try {
            packageManager.getPackageInfo(packageName, 0);
            return true;
        } catch (PackageManager.NameNotFoundException e) {
            return false;
        }
    }

    public boolean isDefaultAppExist() {
        if (mPayment.getDefaultPackage() != null) {
            return !isPackageInstalled(mPayment.getDefaultPackage(), mActivity.getPackageManager());
        } else {
            Log.w(TAG, "Default app is not specified. Specify it using " +
                    "'setDefaultApp()' method of Builder class");
            return false;
        }
    }

    private Map<String, String> getQueryString(String url) {
        String[] params = url.split("&");
        Map<String, String> map = new HashMap<>();
        for (String param : params) {
            String name = param.split("=")[0];
            String value = param.split("=")[1];
            map.put(name, value);
        }
        return map;
    }

    // Make TransactionDetails object from response string
    private TransactionDetails getTransactionDetails(String response) {
        Map<String, String> map = getQueryString(response);
        String transactionId = map.get("txnId");
        String responseCode = map.get("responseCode");
        String approvalRefNo = map.get("ApprovalRefNo");
        String status = map.get("Status");
        String transactionRefId = map.get("txnRef");
        return new TransactionDetails(
                transactionId,
                responseCode,
                approvalRefNo,
                status,
                transactionRefId,
                mPayment.getAmount(),
                mPayment.getName(),
                mPayment.getCurrency(),
                mPayment.getDescription(),
                mPayment.getRecieverUPIId()
        );
    }

    public static final class UPIContainer {
        private Activity activity;
        private Payment payment;

        /**
         * Binds the Activity with Payment.
         *
         * @param activity where payment is to be implemented.
         * @return this, for chaining.
         */
        @NonNull
        public UPIContainer with(@NonNull Activity activity) {
            this.activity = activity;
            payment = new Payment();
            return this;
        }

        @NonNull
        public UPIContainer setPayeeUPIId(@NonNull String vpa) {
            if (!vpa.contains("@")) {
                throw new IllegalStateException("Payee VPA address should be valid (For e.g. example@vpa)");
            }

            payment.setRecieverUPIId(vpa);
            return this;
        }

        @NonNull
        public UPIContainer setPayeeName(@NonNull String name) {
            if (name.trim().length() == 0) {
                throw new IllegalStateException("Payee Name Should be Valid!");
            }
            payment.setName(name);
            return this;
        }

        @NonNull
        public UPIContainer setPayeeMerchantCode(@NonNull String merchantCode) {
            if (merchantCode.trim().length() == 0) {
                throw new IllegalStateException("Merchant Code Should be Valid!");
            }
            payment.setPayeeMerchantCode(merchantCode);
            return this;
        }

        @NonNull
        public UPIContainer setTransactionId(@NonNull String id) {
            if (id.trim().length() == 0) {
                throw new IllegalStateException("Transaction ID Should be Valid!");
            }
            payment.setTxnId(id);
            return this;
        }

        @NonNull
        public UPIContainer setTransactionRefId(@NonNull String refId) {
            if (refId.trim().length() == 0) {
                throw new IllegalStateException("RefId Should be Valid!");
            }
            payment.setTxnRefId(refId);
            return this;
        }


        @NonNull
        public UPIContainer setDescription(@NonNull String description) {
            if (description.trim().length() == 0) {
                throw new IllegalStateException("Description Should be Valid!");
            }

            payment.setDescription(description);
            return this;
        }

        @NonNull
        public UPIContainer setAmount(@NonNull String amount) {
//            if (!amount.contains(".")) {
//                throw new IllegalStateException("Amount should be in decimal format XX.XX (For e.g. 100.00)");
//            }

            payment.setAmount(amount);
            return this;
        }

        @NonNull
        public UpiPayment build() {
            if (activity == null) {
                throw new IllegalStateException("Activity must be specified using with() call before build()");
            }

            if (payment == null) {
                throw new IllegalStateException("Payment Details must be initialized before build()");
            }

            if (payment.getRecieverUPIId() == null) {
                throw new IllegalStateException("Must call setPayeeVpa() before build().");
            }

            if (payment.getTxnId() == null) {
                throw new IllegalStateException("Must call setTransactionId() before build");
            }

            if (payment.getTxnRefId() == null) {
                throw new IllegalStateException("Must call setTransactionRefId() before build");
            }

            if (payment.getName() == null) {
                throw new IllegalStateException("Must call setPayeeName() before build().");
            }

            if (payment.getAmount() == null) {
                throw new IllegalStateException("Must call setAmount() before build().");
            }

            if (payment.getDescription() == null) {
                throw new IllegalStateException("Must call setDescription() before build().");
            }
            return new UpiPayment(activity, payment);
        }
    }
}
