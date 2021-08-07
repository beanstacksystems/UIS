package com.bss.uis.constant;

public interface APIConstant {
    //GET Api https://api.postalpincode.in/pincode/110001
    String BASE_URL = "http://192.168.0.105:8000/api/";
    String SOCIAL_URL = BASE_URL+"auth"+"/";
    String LOGIN_URL = BASE_URL+"login"+"/";
    String LOGOUT_URL = BASE_URL+"logout"+"/";
    String USER = BASE_URL+"user"+"/";
    String RESET_PWD = BASE_URL+"resetPassword"+"/";
    String REGISTER_URL = BASE_URL+"register"+"/";
    String MASTERS = BASE_URL+"masterAll"+"/";
    String pinApi = "https://api.postalpincode.in/pincode/";
}
