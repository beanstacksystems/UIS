package com.bss.uis.context;

import android.content.Context;
import android.content.SharedPreferences;

import com.facebook.AccessToken;
import com.google.android.gms.auth.api.signin.GoogleSignIn;

public class ContextPreferenceManager {


    public static String getToken()
    {
        SharedPreferences sharedPreferences = UISApplicationContext.getInstance().getContext()
                .getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        return sharedPreferences.getString("token", "");
    }
    public static void saveLoginDetails(String accesstoken,String logintype) {
        SharedPreferences sharedPreferences = UISApplicationContext.getInstance().getContext()
                .getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.putString("token", accesstoken);
        editor.putString("logintype", logintype);
        editor.commit();
    }
    public static void clearLoginInfo()
    {
        SharedPreferences sharedPreferences =
                UISApplicationContext.getInstance().getContext()
                        .getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.commit();
    }
    public static boolean isUserLogedOut() {
        SharedPreferences sharedPreferences = UISApplicationContext.getInstance().getContext()
                .getSharedPreferences("logindetails", Context.MODE_PRIVATE);
        boolean isTokenEmpty = sharedPreferences.getString("token", "").isEmpty();
        String logintype = sharedPreferences.getString("logintype", "");
        if(logintype.equals("facebook"))
            return AccessToken.getCurrentAccessToken() != null;
        if(logintype.equals("google"))
            return (null != GoogleSignIn.getLastSignedInAccount(UISApplicationContext.getInstance().getContext()));
        return isTokenEmpty;
    }
}
