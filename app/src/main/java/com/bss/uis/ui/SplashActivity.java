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

import com.beanstack.utility.alertDialog.AppAlertDialog;
import com.beanstack.utility.service.NavigationService;
import com.beanstack.utility.service.impl.NavigationServiceImpl;
import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.ui.loginsignup.LoginSignupActivity;
import com.bss.uis.util.AppUtil;
import com.facebook.FacebookSdk;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    ImageView logoviewPurple,logoviewBlue,logoviewRed,logoviewYellow,logoviewOrange,logoviewGreen;
    private NavigationService navigationService;
    TextView logoText1,logoText2,logoText3,logoText4;
    Animation logoFromRightbottom,logoFromLeftbottom,
            logoFromRightTop,logoFromleftTop,logoFromTop,logoFrombottom;

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
        logoText4 = findViewById(R.id.caption);

        logoviewPurple = findViewById(R.id.imageViewpurple);
        logoviewRed = findViewById(R.id.imageViewred);
        logoviewBlue = findViewById(R.id.imageViewblue);
        logoviewYellow = findViewById(R.id.imageViewYellow);
        logoviewOrange = findViewById(R.id.imageVieworrange);
        logoviewGreen = findViewById(R.id.imageViewGreen);
        updateUI();
        setAnimation();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                endAnimation();
            }
        }, AppConstants.SPLASH_SCREEN_TIME_OUT);
    }

    private void updateUI() {
        navigationService = new NavigationServiceImpl(SplashActivity.this, LoginSignupActivity.class);
    }
    private void setAnimation()
    {
        logoFromTop = AnimationUtils.loadAnimation(this, R.anim.fromtop);
        logoFrombottom = AnimationUtils.loadAnimation(this, R.anim.frombottom);
        logoFromRightTop = AnimationUtils.loadAnimation(this, R.anim.fromtoprightcorner);
        logoFromleftTop = AnimationUtils.loadAnimation(this, R.anim.fromtopleftcorner);
        logoFromRightbottom = AnimationUtils.loadAnimation(this, R.anim.fromrightbottomcorner);
        logoFromLeftbottom = AnimationUtils.loadAnimation(this, R.anim.fromleftbottomcorner);

        logoviewRed.setAnimation(logoFromleftTop);
        logoviewPurple.setAnimation(logoFromLeftbottom);
        logoviewOrange.setAnimation(logoFrombottom);
        logoviewBlue.setAnimation(logoFromTop);
        logoviewGreen.setAnimation(logoFromRightTop);
        logoviewYellow.setAnimation(logoFromRightbottom);

        logoText1.startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_animation));
        logoText2.startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_animation));
        logoText3.startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_animation));
        logoText4.startAnimation(AnimationUtils.loadAnimation(this, R.anim.text_animation));
    }
    private void endAnimation()
    {
        Animation textAnimationright = AnimationUtils.loadAnimation(this, R.anim.text_animation_right);
        Animation textAnimationback = AnimationUtils.loadAnimation(this, R.anim.text_animation_back);
        textAnimationback.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationEnd(Animation arg0) {
                if(!AppUtil.isConnectedToInternet(SplashActivity.this))
                {
                    new AppAlertDialog(SplashActivity.this,new NavigationServiceImpl(null,null){
                        @Override
                        public void buttonAction(String text) {
                            super.buttonAction(text);
                            SplashActivity.this.finishAffinity();
                            System.exit(0);
                        }
                    }).getDialog(0,"Sorry!!!","Data connectivity not available.",true).show();
                    return;
                }
                navigationService.finishAndNavigate();
            }
            @Override
            public void onAnimationRepeat(Animation arg0) {
            }
            @Override
            public void onAnimationStart(Animation arg0) {
            }
        });
        logoText1.startAnimation(textAnimationback);
        logoText2.startAnimation(textAnimationright);
        logoText3.startAnimation(textAnimationback);
        logoText4.startAnimation(textAnimationright);
        logoviewGreen.startAnimation(textAnimationback);
        logoviewYellow.startAnimation(textAnimationback);
        logoviewBlue.startAnimation(textAnimationright);
        logoviewOrange.startAnimation(textAnimationback);
        logoviewPurple.startAnimation(textAnimationright);
        logoviewRed.startAnimation(textAnimationright);

    }
}
