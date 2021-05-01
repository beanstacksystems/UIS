package com.bss.uis.ui;

import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.impl.NavigationServiceImpl;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;
import com.facebook.FacebookSdk;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    ImageView logoviewPurple,logoviewBlue,logoviewRed,logoviewYellow,logoviewOrange,logoviewGreen;
    private NavigationService navigationService;
    TextView logoText1,logoText2,logoText3;
    Animation logoFromRightbottom,logoFromLeftbottom,
            logoFromRightTop,logoFromleftTop,logoFromTop,logoFrombottom;
    Animation appTitleFade,appTitlezoomin;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);

        logoText1 = findViewById(R.id.SplashLogoTitle1);
        logoText2 = findViewById(R.id.SplashLogoTitle2);
        logoText3 = findViewById(R.id.SplashLogoTitle3);

        logoviewPurple = findViewById(R.id.imageViewpurple);
        logoviewRed = findViewById(R.id.imageViewred);
        logoviewBlue = findViewById(R.id.imageViewblue);
        logoviewYellow = findViewById(R.id.imageViewYellow);
        logoviewOrange = findViewById(R.id.imageVieworrange);
        logoviewGreen = findViewById(R.id.imageViewGreen);

        logoFromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        logoFrombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        logoFromRightTop = AnimationUtils.loadAnimation(this, R.anim.fromtoprightcorner);
        logoFromleftTop = AnimationUtils.loadAnimation(this, R.anim.fromtopleftcorner);
        logoFromRightbottom = AnimationUtils.loadAnimation(this, R.anim.fromrightbottomcorner);
        logoFromLeftbottom = AnimationUtils.loadAnimation(this, R.anim.fromleftbottomcorner);
        appTitleFade = AnimationUtils.loadAnimation(this, R.anim.textfadeout);
        appTitlezoomin = AnimationUtils.loadAnimation(this, R.anim.textzoomin);

        logoviewRed.setAnimation(logoFromleftTop);
        logoviewPurple.setAnimation(logoFromLeftbottom);
        logoviewOrange.setAnimation(logoFrombottom);
        logoviewBlue.setAnimation(logoFromTop);
        logoviewGreen.setAnimation(logoFromRightTop);
        logoviewYellow.setAnimation(logoFromRightbottom);

        logoText1.startAnimation(appTitlezoomin);
        logoText2.startAnimation(appTitlezoomin);
        logoText3.startAnimation(appTitlezoomin);
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
