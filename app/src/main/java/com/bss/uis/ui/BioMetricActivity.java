package com.bss.uis.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.WindowManager;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.annotation.RequiresApi;
import androidx.appcompat.app.AppCompatActivity;

import com.beanstack.biometric.BiometricCallback;
import com.beanstack.biometric.BiometricManager;
import com.bss.uis.R;
import com.bss.uis.ui.navDrawer.DrawerMainActivity;

public class BioMetricActivity extends AppCompatActivity implements BiometricCallback {
    BiometricManager mBiometricManager;

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE,WindowManager.LayoutParams.FLAG_SECURE);
        setContentView(R.layout.activity_biometric);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LOCKED);
        mBiometricManager = new BiometricManager.BiometricBuilder(com.bss.uis.ui.BioMetricActivity.this)
                .setTitle(getString(R.string.biometric_title))
                .setSubtitle(getString(R.string.biometric_subtitle))
                .setDescription(getString(R.string.biometric_description))
                .setNegativeButtonText(getString(R.string.biometric_negative_button_text))
                .build();

        //start authentication
        mBiometricManager.authenticate(com.bss.uis.ui.BioMetricActivity.this);
    }

    @Override
    public void onSdkVersionNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_sdk_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotSupported() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_hardware_not_supported), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationNotAvailable() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_fingerprint_not_available), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationPermissionNotGranted() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_error_permission_not_granted), Toast.LENGTH_LONG).show();
    }

    @Override
    public void onBiometricAuthenticationInternalError(String error) {
        Toast.makeText(getApplicationContext(), error, Toast.LENGTH_LONG).show();
    }

    @Override
    public void onAuthenticationFailed() {
//        Toast.makeText(getApplicationContext(), getString(R.string.biometric_failure), Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onAuthenticationCancelled() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_cancelled), Toast.LENGTH_LONG).show();
        cancellBiomatricAuth();
    }

    @Override
    public void onAuthenticationSuccessful() {
        Toast.makeText(getApplicationContext(), getString(R.string.biometric_success), Toast.LENGTH_LONG).show();
        boolean isNewUser = getIntent().getBooleanExtra("isNewUser", false);
        Intent intent = new Intent(com.bss.uis.ui.BioMetricActivity.this, DrawerMainActivity.class);
        intent.putExtra("isNewUser", isNewUser);
        startActivity(intent);
        finish();
    }

    @Override
    public void onAuthenticationHelp(int helpCode, CharSequence helpString) {
//        Toast.makeText(getApplicationContext(), helpString, Toast.LENGTH_LONG).show();
    }

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onAuthenticationError(int errorCode, CharSequence errString) {
        Toast.makeText(getApplicationContext(), "Cancelled !", Toast.LENGTH_LONG).show();
        cancellBiomatricAuth();
    }

    @RequiresApi(api = VERSION_CODES.M)
    public void cancellBiomatricAuth() {
        mBiometricManager.cancelAuthentication();
        Intent a = new Intent(Intent.ACTION_MAIN);
        a.addCategory(Intent.CATEGORY_HOME);
        a.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(a);
        finish();
        System.exit(0);
    }
}
