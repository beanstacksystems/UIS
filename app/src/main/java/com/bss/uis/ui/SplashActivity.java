package com.bss.uis.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.ui.navDrawer.DrawerMainActivity;
import com.facebook.CallbackManager;
import com.facebook.FacebookSdk;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;


import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    ImageView logoview;
    TextView copyright;
    private LoginButton fLoginBtn;
    private static final int RC_SIGN_IN = 9001;
    private LinearLayout fbCustombuttonll, GoogleCustomll;
    private Button fbCustombutton, googleCustomButton;
    private GoogleApiClient googleApiClient;
    private static final String[] EMAIL = {"email", "public_profile", "user_friends"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        FacebookSdk.setApplicationId(getString(R.string.facebook_application_id));
        FacebookSdk.sdkInitialize(getApplicationContext());
        setContentView(R.layout.activity_splash);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        fLoginBtn = findViewById(R.id.facebook_button);
        fbCustombutton = findViewById(R.id.fbCustom);
        googleCustomButton = findViewById(R.id.googleCustom);
        fbCustombuttonll = findViewById(R.id.fbCustomll);
        fbCustombuttonll.setVisibility(View.INVISIBLE);
        GoogleCustomll = findViewById(R.id.GoogleCustomll);
        GoogleCustomll.setVisibility(View.INVISIBLE);
        fLoginBtn.setReadPermissions(Arrays.asList(EMAIL));
        logoview = findViewById(R.id.imageView);
        copyright = findViewById(R.id.copyright);
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        logoview.startAnimation(animation);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // going to login activity
                fbCustombuttonll.setVisibility(View.VISIBLE);
                GoogleCustomll.setVisibility(View.VISIBLE);
                Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation);
                anim.setDuration(AppConstants.ANIMATION_TIMEOUT);
                Animation anim2 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation);
                anim.setDuration(AppConstants.ANIMATION_TIMEOUT);
                GoogleCustomll.startAnimation(anim);
                fbCustombuttonll.startAnimation(anim2);
                Intent intent = new Intent(SplashActivity.this, DrawerMainActivity.class);
                startActivity(intent);
                //invoke the SecondActivity.

                finish();
                //the current activity will get finished.
            }
        }, AppConstants.SPLASH_SCREEN_TIME_OUT);

    }
    public void onClickFacebookButton(View view) {
        if (view == fbCustombutton) {
            fLoginBtn.performClick();
        }
    }

    public void onClickGmailButton(View view) {
        if (view == googleCustomButton) {
            Intent intent = Auth.GoogleSignInApi.getSignInIntent(googleApiClient);
            startActivityForResult(intent, RC_SIGN_IN);
        }
    }
}
