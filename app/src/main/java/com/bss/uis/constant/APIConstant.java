package com.bss.uis.constant;

public interface APIConstant {
//    public final String BASE_URL = "https://api.uis.byree.in/api/";
    public final String BASE_URL = "http://192.168.193.247:8000/api/";
    public final String SOCIAL_URL = BASE_URL+"auth"+"/";
    public final String LOGIN_URL = BASE_URL+"login"+"/";
    public final String LOGOUT_URL = BASE_URL+"logout"+"/";
    public final String USER = BASE_URL+"user"+"/";
    public final String ISVALIDACCESSTOKEN = BASE_URL+"isValidAccessToken"+"/";
    public final String USERRIGHTS = BASE_URL+"userrights"+"/";
    public final String RESET_PWD = BASE_URL+"resetPassword"+"/";
    public final String REGISTER_URL = BASE_URL+"register"+"/";
    public final String REGISTER_PATIENT_URL = BASE_URL+"registerpatient"+"/";
    public final String MASTERS = BASE_URL+"masterAll"+"/";
    public final String TABS = BASE_URL+"tabdata"+"/";
    public final String pinApi = "https://api.postalpincode.in/pincode/";
    public final String SERVERAVAILABLECHECKAPI = BASE_URL+"isserverreachable"+"/";
}
