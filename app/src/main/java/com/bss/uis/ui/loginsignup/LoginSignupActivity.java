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
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.beanstack.biometric.BiometricUtils;
import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.dao.ApplicationRepository;
import com.bss.uis.database.entity.AppConfig;
import com.bss.uis.service.NavigationService;
import com.bss.uis.service.UserService;
import com.bss.uis.service.impl.NavigationServiceImpl;
import com.bss.uis.service.impl.UserServiceImpl;
import com.bss.uis.ui.BioMetricActivity;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.ui.navDrawer.DrawerMainActivity;
import com.bss.uis.util.AppUtil;
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
    private TextInputEditText nameTxt,emailTxt,pwdTxt,cnfpwdTxt;
    private Button button;
    private TextView newAccount,resetPwd;
    private TextInputLayout nameLayout,pwdLayout,cnfPwdLayout,emailLayout;
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
        if(!AppUtil.isConnectedToInternet(applicationContext.getContext()))
        {

        }
        updateUI();
        updateLocalDB();
        if(isUserHasValidToken()){
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
        pwdLayout = findViewById(R.id.pwdlayout);
        cnfPwdLayout = findViewById(R.id.cnfpwdlayout);
        emailLayout = findViewById(R.id.etemail);
        nameTxt = findViewById(R.id.nameTxt);
        cnfpwdTxt = findViewById(R.id.cnfpwdTxt);
        emailTxt = findViewById(R.id.etemailTxt);
        pwdTxt = findViewById(R.id.pwdTxt);
        button = findViewById(R.id.login_signup_btn);
        fbLoginButton = findViewById(R.id.fb_login_button);
        fbLoginButton.setPermissions(Arrays.asList(fbPermission));
        newAccount = findViewById(R.id.createnewac);
        resetPwd = findViewById(R.id.forgotpwd);
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
            if(newAccount.getText().toString().equals(getResources().getString(R.string.login))){
                nameLayout.setVisibility(View.INVISIBLE);
                cnfPwdLayout.setVisibility(View.INVISIBLE);
                emailLayout.setHint("Email/UserName");
                button.setText(R.string.login);
                newAccount.setText(R.string.signup);
                return;
            }
            else{
                nameLayout.setVisibility(View.VISIBLE);
                cnfPwdLayout.setVisibility(View.VISIBLE);
                emailLayout.setHint("Email");
                button.setText(R.string.signup);
                newAccount.setText(R.string.login);
            }

        }

    });
        resetPwd.setOnClickListener(new View.OnClickListener(){
        @Override
        public void onClick( View v)
        {
            nameLayout.setVisibility(View.INVISIBLE);
            cnfPwdLayout.setVisibility(View.VISIBLE);
            emailLayout.setHint("Email/UserName");
            button.setText(R.string.resetpwd);
            newAccount.setText(R.string.login);
        }
    });
        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!isValidData())
                    return;
                if(button.getText().toString().equals(getResources().getString(R.string.signup)))
                    registerUser(nameTxt.getText().toString(),emailTxt.getText().toString(),pwdTxt.getText().toString());
                else if(button.getText().toString().equals(getResources().getString(R.string.resetpwd)))
                    resetPwd(emailTxt.getText().toString(),pwdTxt.getText().toString());
                else
                    loginUser(emailTxt.getText().toString(),pwdTxt.getText().toString());
            }
        });
        emailTxt.setText("testme");
        pwdTxt.setText("test123");
        nameTxt.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (nameLayout,"Name cannot be empty"));
        nameTxt.addTextChangedListener(new CustomTextValidator(nameTxt) {
            @Override
            public void validate(TextView textView, String text) {
                if(nameLayout.getVisibility()==View.VISIBLE)
                {
                    nameLayout.setError(null);
                    if(null == text || text.isEmpty())
                        nameLayout.setError("Name cannot be empty");
                    else if(!UIUtil.isContainsValidCharacter(text))
                        nameLayout.setError("Only alphanumeric characters allowed");
                }

            }
        });
        emailTxt.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (emailLayout,"Field cannot be empty"));
        emailTxt.addTextChangedListener(new CustomTextValidator(emailTxt) {
            @Override
            public void validate(TextView textView, String text) {
                boolean isRegister = nameLayout.getVisibility()==View.VISIBLE;
                emailLayout.setError(null);
                if(null == text || text.isEmpty())
                    emailLayout.setError("Field cannot be empty");
                else if((isRegister)?!(UIUtil.isEmailValid(text)):(!UIUtil.isEmailValid(text)
                && !UIUtil.isContainsValidCharacter(text)))
                    emailLayout.setError("Only alphanumeric characters allowed");
            }
        });
        pwdTxt.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (pwdLayout,"Field cannot be empty"));
        pwdTxt.addTextChangedListener(new CustomTextValidator(pwdTxt) {
            @Override
            public void validate(TextView textView, String text) {
                pwdLayout.setError(null);
                if(null == text || text.isEmpty())
                    pwdLayout.setError("Field cannot be empty");
                else if(AppUtil.isCorrectPasswordPolicy(text))
                    pwdLayout.setError("Only alphanumeric characters allowed");
            }
        });
        cnfpwdTxt.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (cnfPwdLayout,"Field cannot be empty"));
        cnfpwdTxt.addTextChangedListener(new CustomTextValidator(cnfpwdTxt) {
            @Override
            public void validate(TextView textView, String text) {
                cnfPwdLayout.setError(null);
                if(null == text || text.isEmpty())
                    cnfPwdLayout.setError("Field cannot be empty");
                else if(!pwdTxt.getText().toString().equals(text))
                    cnfPwdLayout.setError("Password is not same as ConfirmPassword");
            }
        });
    }

    private boolean isValidData() {
        if((nameLayout.getVisibility()==View.VISIBLE && null != nameLayout.getError())
            || null != emailLayout.getError() || null != pwdLayout.getError()
        || (cnfPwdLayout.getVisibility()==View.VISIBLE && null != cnfPwdLayout.getError()))
            return false;
        String name = nameTxt.getText().toString();
        String email = emailTxt.getText().toString();
        String pwd = pwdTxt.getText().toString();
        if((nameLayout.getVisibility()==View.VISIBLE && (null == name ||name.isEmpty()))|| null == email ||email.isEmpty()
                || null == pwd || pwd.isEmpty()){
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillvalue),Toast.LENGTH_LONG).show();
            return false;
        }
        return true;
    }

    private void loginUser(String userName,String password)
    {
        userService = new UserServiceImpl();
        userService.loginUser(userName,password,navigationService,this);
    }
    private void resetPwd(String userName,String password)
    {
        userService = new UserServiceImpl();
        userService.resetPassword(userName,password,navigationService,this);
    }
    private void registerUser(String userName,String email,String password)
    {
        userService = new UserServiceImpl();
        userService.registerUser(userName,email,password,navigationService,this);
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
    @Override
    public void onBackPressed() {
        if(newAccount.getVisibility()== View.INVISIBLE){
            nameLayout.setVisibility(View.INVISIBLE);
            cnfPwdLayout.setVisibility(View.INVISIBLE);
            emailLayout.setHint("Email/UserName");
            newAccount.setVisibility(View.VISIBLE);
            button.setText(R.string.login);
            return;
        }
        else
            finishAffinity();
        System.exit(0);
    }
}