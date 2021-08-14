package com.bss.uis.constant;

public interface APIConstant {
    //GET Api https://api.postalpincode.in/pincode/110001
    public final String BASE_URL = "https://api.uis.byree.in/api/";
//    public final String BASE_URL = "http://192.168.0.106:8000/api/";
    public final String SOCIAL_URL = BASE_URL+"auth"+"/";
    public final String LOGIN_URL = BASE_URL+"login"+"/";
    public final String LOGOUT_URL = BASE_URL+"logout"+"/";
    public final String USER = BASE_URL+"user"+"/";
    public final String USERRIGHTS = BASE_URL+"userrights"+"/";
    public final String RESET_PWD = BASE_URL+"resetPassword"+"/";
    public final String REGISTER_URL = BASE_URL+"register"+"/";
    public final String MASTERS = BASE_URL+"masterAll"+"/";
    public final String pinApi = "https://api.postalpincode.in/pincode/";
}
