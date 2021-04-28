package com.bss.uis.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;

import androidx.appcompat.app.AppCompatActivity;

import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.impl.NavigationServiceImpl;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;
import com.facebook.FacebookSdk;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    ImageView logoview;
    private NavigationService navigationService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        logoview = findViewById(R.id.imageView);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        animation.setDuration(1000);
        logoview.startAnimation(animation);
        updateUI();
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                navigationService.navigate();
                finish();
            }
        }, AppConstants.SPLASH_SCREEN_TIME_OUT);
    }

    private void updateUI() {
        navigationService = new NavigationServiceImpl(SplashActivity.this, LoginSignupActivity.class);
    }
    private boolean isUserLoggedIn()
    {
        return false;
    }
}
