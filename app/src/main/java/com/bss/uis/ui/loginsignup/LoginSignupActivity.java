package com.bss.uis.ui.loginsignup;

import android.content.Intent;
import android.content.pm.ActivityInfo;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.beanstack.biometric.BiometricUtils;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.dao.ApplicationRepository;
import com.bss.uis.database.entity.AppConfig;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.UserService;
import com.bss.uis.service.impl.NavigationServiceImpl;
import com.bss.uis.service.impl.UserServiceImpl;
import com.bss.uis.ui.BioMetricActivity;
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
import com.google.android.gms.auth.api.signin.GoogleSignIn;
import com.google.android.gms.auth.api.signin.GoogleSignInAccount;
import com.google.android.gms.auth.api.signin.GoogleSignInClient;
import com.google.android.gms.auth.api.signin.GoogleSignInOptions;
import com.google.android.gms.common.api.ApiException;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

public class LoginSignupActivity extends AppCompatActivity {
    private static final String TAG = "LoginSignupActivity";
    private TextInputEditText nameTxt,emailTxt,pwdTxt;
    private Button button;
    private TextView newAccount;
    private TextInputLayout nameLayout;
    private ImageView fbImage,gImage;
    private GoogleSignInClient googleSignInClient;
    LoginButton fbLoginButton;
    CallbackManager callbackManager;
    private NavigationService navigationService;
    private UserService userService;
    private static final String[] fbPermission = {"email", "public_profile"};
    private static final int RC_SIGN_IN = 9001;
    private UISApplicationContext applicationContext;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        applicationContext = UISApplicationContext.getInstance();
        applicationContext.setContext(getApplicationContext());
        updateUI();
        if(isUserHasValidToken()){
            updateLocalDB();
            navigationService.navigate();
            finish();
        }
        FacebookSdk.setApplicationId(getString(R.string.facebook_app_id));
        setContentView(R.layout.activity_login_signup);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_SECURE, WindowManager.LayoutParams.FLAG_SECURE);
        setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
        callbackManager = CallbackManager.Factory.create();
        LoginManager.getInstance().registerCallback(callbackManager, new CustomFacebookCallBack());
        initGoogleSignin();
        initView();
    }

    private void updateLocalDB() {
        ApplicationRepository applicationRepository = new ApplicationRepository(applicationContext);
        List<AppConfig> appConfigList = applicationRepository.retrieve();
        //get RemoteDB AppConfig.
        List<AppConfig> appConfigListRemote = null;
        if(null == appConfigList || appConfigList.isEmpty())
        {
            //call DB to get Configuration and updateLocalDB
            AppConfig appConfig = new AppConfig();
            appConfig.setConfigKey("noOfPatients");
            appConfig.setConfigValue("2");
            applicationRepository.insert(appConfig);
            updateLocalDB();
        }
        initAppConfig(appConfigList);
    }
    private void initAppConfig(List<AppConfig> appConfigList)
    {
        if(null == applicationContext.getAppConfigMap())
            applicationContext.setAppConfigMap(new HashMap<String, String>());
        for(AppConfig appConfig:appConfigList)
            applicationContext.getAppConfigMap().put(appConfig.getConfigKey(),appConfig.getConfigValue());
    }

    private void initView()
    {
        nameLayout = findViewById(R.id.name_signup);
        nameTxt = findViewById(R.id.nameTxt);
        emailTxt = findViewById(R.id.etemailTxt);
        pwdTxt = findViewById(R.id.pwdTxt);
        button = findViewById(R.id.login_signup_btn);
        fbLoginButton = findViewById(R.id.fb_login_button);
        fbLoginButton.setPermissions(Arrays.asList(fbPermission));
        newAccount = findViewById(R.id.createnewac);
        fbImage = findViewById(R.id.fbCustomButton);
        gImage = findViewById(R.id.googleCustom);
        fbImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v)
            {
                fbLoginButton.performClick();
            }
        });
        gImage.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick( View v)
            {
                Intent signInIntent = googleSignInClient.getSignInIntent();
                startActivityForResult(signInIntent, RC_SIGN_IN);
            }
        });

        newAccount.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick( View v)
        {
            nameLayout.setVisibility(View.VISIBLE);
            v.setVisibility(View.INVISIBLE);
            button.setText(R.string.signup);
        }
    });
    }

    private void handleAccessToken(String token, String authCode, String email, String userId, String name, String source) {
        Log.w(TAG,token);
        userService = new UserServiceImpl();
        userService.registerAndAuthenticateUser(token, authCode, source);
        navigationService.navigate();
        finish();
    }
    private void updateUI() {
        if (BiometricUtils.isFingerprintAvailable(LoginSignupActivity.this))
            navigationService = new NavigationServiceImpl(LoginSignupActivity.this, BioMetricActivity.class);
        else
            navigationService = new NavigationServiceImpl(LoginSignupActivity.this, DrawerMainActivity.class);
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
    private boolean isUserHasValidToken()
    {
        return AccessToken.getCurrentAccessToken() != null;
    }
    private void initGoogleSignin()
    {
        GoogleSignInOptions gso = new GoogleSignInOptions.Builder(GoogleSignInOptions.DEFAULT_SIGN_IN)
                .requestIdToken(getString(R.string.googleClientId))
                .requestServerAuthCode(getString(R.string.googleClientId))
                .requestEmail()
                .build();
        googleSignInClient = GoogleSignIn.getClient(this, gso);
    }
    class CustomFacebookCallBack implements FacebookCallback<LoginResult> {
        @Override
        public void onSuccess(final LoginResult loginResult) {
            GraphRequest request = GraphRequest.newMeRequest(loginResult.getAccessToken(), new GraphRequest.GraphJSONObjectCallback() {
                @Override
                public void onCompleted(JSONObject object, GraphResponse response) {
                    try {
                        String email = object.getString("email");
                        String userId = object.getString("id");
                        String name = object.getString("name");
                        handleAccessToken(loginResult.getAccessToken().getToken(), null, email, userId, name, "Facebook");
                    } catch (JSONException e) {
                        Log.w(TAG,e.getMessage());
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
            Log.w(TAG,"cancel facebook login");
        }
        @Override
        public void onError(FacebookException error) {
            Log.w(TAG,"error in facebook login");
        }
    }
}