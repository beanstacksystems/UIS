package com.bss.uis.context;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class ContextPreferenceManager {
    Context context;

    public ContextPreferenceManager() {
        this.context = UISApplicationContext.getInstance().getContext();
    }

    public void saveLoginDetails(String accesstoken,String logintype) {
        SharedPreferences sharedPreferences = context.getSharedPreferences("loginDetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("accesstoken", accesstoken);
        editor.putString("logintype", logintype);
        editor.commit();
    }

    public boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = context.getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        boolean isTokenEmpty = sharedPreferences.getString("accesstoken", "").isEmpty();
        String logintype = sharedPreferences.getString("logintype", "");
        if(logintype.equals("facebook"))
            return AccessToken.getCurrentAccessToken() != null;
        if(logintype.equals("google"))
            return (null != GoogleSignIn.getLastSignedInAccount(context));
        return isTokenEmpty;
    }
}
