package com.bss.uis.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
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
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity {
    private static final String TAG = "SplashActivity";
    private CallbackManager callbackManager;
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
        callbackManager = CallbackManager.Factory.create();
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        logoview.startAnimation(animation);
        fLoginBtn.registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
            public void onSuccess(final LoginResult loginResult) {
                Log.i(TAG, "onSuccess: logged in successfully");
                GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                    @Override
                    public void onCompleted(JSONObject object, GraphResponse response) {
                        Log.i(TAG, "onCompleted: response: " + response.toString());
                        try {
                            String email = object.getString("email");
                            String userId = object.getString("id");
                            String name = object.getString("name");
                            handleAccessToken(loginResult.getAccessToken().getToken(), email, userId, name, "Facebook");
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }
                    }
                });
                Bundle parameters = new Bundle();
                parameters.putString("fields", "id,name,email,gender,birthday");
                request.setParameters(parameters);
                request.executeAsync();
            }
            @Override
            public void onCancel() {
                Log.d(TAG, "facebook:onCancel");
            }

            @Override
            public void onError(FacebookException error) {
                Log.d(TAG, "facebook:onError", error);
            }
        });
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
              //  Intent intent = new Intent(SplashActivity.this, DrawerMainActivity.class);
               // startActivity(intent);
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
    private void handleAccessToken(String token, String email, String userId, String name, String source) {
        System.out.println(token);

    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
    }
}
