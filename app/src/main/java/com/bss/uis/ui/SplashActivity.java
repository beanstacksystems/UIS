package com.bss.uis.ui;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.graphics.Typeface;
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

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.beanstack.biometric.BiometricUtils;
import com.bss.uis.R;
import com.bss.uis.constant.AppConstants;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.UserService;
import com.bss.uis.service.impl.NavigationServiceImpl;
import com.bss.uis.service.impl.UserServiceImpl;
import com.bss.uis.ui.navDrawer.DrawerMainActivity;
import com.facebook.AccessToken;
import com.facebook.CallbackManager;
import com.facebook.FacebookCallback;
import com.facebook.FacebookException;
import com.facebook.FacebookSdk;
import com.facebook.GraphRequest;
import com.facebook.GraphResponse;
import com.facebook.login.LoginManager;
import com.facebook.login.LoginResult;
import com.facebook.login.widget.LoginButton;
import com.google.android.gms.auth.api.Auth;
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.auth.api.signin.GoogleSignInResult;
import com.google.android.gms.common.ConnectionResult;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.common.api.GoogleApiClient;
import com.google.android.gms.common.api.OptionalPendingResult;
import com.google.android.gms.tasks.Task;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;

public class SplashActivity extends AppCompatActivity implements GoogleApiClient.OnConnectionFailedListener{
    private static final String TAG = "SplashActivity";
    private CallbackManager callbackManager;
    ImageView logoview;
    TextView caption,copyright;
    private LoginButton fLoginBtn;
    private static final int RC_SIGN_IN = 9001;
    private LinearLayout fbCustombuttonll, GoogleCustomll;
    private Button fbCustombutton, googleCustomButton;
    private GoogleApiClient googleApiClient;
    private static final String[] EMAIL = {"email", "public_profile"};
    private NavigationService navigationService;
    private UserService userService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
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
        caption = findViewById(R.id.caption);
        caption.setTypeface(Typeface.SERIF, Typeface.ITALIC);
        updateUI();
        callbackManager = CallbackManager.Factory.create();
        Animation animation= AnimationUtils.loadAnimation(getApplicationContext(),R.anim.animation);
        animation.setDuration(800);
        logoview.startAnimation(animation);
        Animation captionAnimation = AnimationUtils.loadAnimation(this, R.anim.fragment_close_exit);
        captionAnimation.setDuration(500);
        caption.startAnimation(captionAnimation);
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleClientId))
                .requestServerAuthCode(getString(R.string.googleClientId))
                .requestEmail()
                .build();

        googleApiClient = new GoogleApiClient.Builder(this)
                .enableAutoManage(this, this)
                .addApi(Auth.GOOGLE_SIGN_IN_API, gso)
                .build();
        LoginManager.getInstance().registerCallback(callbackManager, new FacebookCallback<LoginResult>() {
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
                            handleAccessToken(loginResult.getAccessToken().getToken(), null, email, userId, name, "Facebook");
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
//                if(!isFacebookLoggedIn() && !isGoogleLoggedIn()){
                    fbCustombuttonll.setVisibility(View.VISIBLE);
                    GoogleCustomll.setVisibility(View.VISIBLE);
                    Animation anim = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation);
                    anim.setDuration(AppConstants.ANIMATION_TIMEOUT);
                    Animation anim2 = AnimationUtils.loadAnimation(SplashActivity.this, R.anim.animation);
                    anim.setDuration(AppConstants.ANIMATION_TIMEOUT);
                    GoogleCustomll.startAnimation(anim);
                    fbCustombuttonll.startAnimation(anim2);

//                }
//                navigationService.navigate();
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
    private void handleAccessToken(String token, String authCode, String email, String userId, String name, String source) {
        System.out.println(token);
        userService = new UserServiceImpl();
        userService.registerAndAuthenticateUser(token, authCode, source);
        navigationService.navigate();
    }
    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        callbackManager.onActivityResult(requestCode, resultCode, data);
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RC_SIGN_IN) {
            Task<GoogleSignInAccount> task = GoogleSignIn.getSignedInAccountFromIntent(data);
            try {
                GoogleSignInAccount account = task.getResult(ApiException.class);

                Log.d(TAG, "Google auth Id:" + account.getId());

                handleAccessToken(account.getIdToken(), account.getServerAuthCode(), account.getEmail(), account.getId(), account.getDisplayName(), "google");
            } catch (ApiException e) {
                Log.w(TAG, "Google sign in failed", e);
            }
        }
    }
    @Override
    public void onConnectionFailed(@NonNull ConnectionResult connectionResult) {

    }
    private void updateUI() {
        if (BiometricUtils.isFingerprintAvailable(SplashActivity.this))
            navigationService = new NavigationServiceImpl(SplashActivity.this, BioMetricActivity.class);
        else
            navigationService = new NavigationServiceImpl(SplashActivity.this, DrawerMainActivity.class);
    }
    public boolean isFacebookLoggedIn(){
        return AccessToken.getCurrentAccessToken() != null;
    }
    public boolean isGoogleLoggedIn()
    {
        OptionalPendingResult<GoogleSignInResult> opr = Auth.GoogleSignInApi.silentSignIn(googleApiClient);
        if (opr.isDone()) {
            // If the user's cached credentials are valid, the OptionalPendingResult will be "done"
            // and the GoogleSignInResult will be available instantly.
//            Log.d(TAG, "Got cached sign-in");
//            GoogleSignInResult result = opr.get();
//            handleSignInResult(result);
            return true;
        } else {
            // If the user has not previously signed in on this device or the sign-in has expired,
            // this asynchronous branch will attempt to sign in the user silently.  Cross-device
            // single sign-on will occur in this branch.
//            showProgressDialog();
//            opr.setResultCallback(new ResultCallback<GoogleSignInResult>() {
//                @Override
//                public void onResult(GoogleSignInResult googleSignInResult) {
//                    hideProgressDialog();
//                    handleSignInResult(googleSignInResult);
//                }
//            });
            return false;
        }
    }
}
