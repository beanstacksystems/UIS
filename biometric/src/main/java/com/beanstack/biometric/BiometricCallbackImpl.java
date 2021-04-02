package com.beanstack.biometric;

import android.hardware.biometrics.BiometricPrompt;
import android.os.Build;

import androidx.annotation.RequiresApi;


@RequiresApi(api = Build.VERSION_CODES.P)
public class BiometricCallbackImpl extends BiometricPrompt.AuthenticationCallback {

    private BiometricCallback biometricCallback;

    public BiometricCallbackImpl(BiometricCallback biometricCallback) {
        this.biometricCallback = biometricCallback;
    }

    @Override
    public void onAuthenticationSucceeded(BiometricPrompt.AuthenticationResult result) {
        super.onAuthenticationSucceeded(result);
        biometricCallback.onAuthenticationSuccessful();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
        super.onAuthenticationHelp(helpCode, helpString);
        biometricCallback.onAuthenticationHelp(helpCode, helpString);
    }


    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        super.onAuthenticationError(errorCode, errString);
        biometricCallback.onAuthenticationError(errorCode, errString);
    }


    @Override
    public void onAuthenticationFailed() {
        super.onAuthenticationFailed();
        biometricCallback.onAuthenticationFailed();
    }
}
