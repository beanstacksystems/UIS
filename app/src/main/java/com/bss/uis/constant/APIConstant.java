package com.bss.uis.constant;

public interface APIConstant {
    //GET Api https://api.postalpincode.in/pincode/110001
    public final String BASE_URL = "http://192.168.0.103:8000/api/";
    public final String LOGIN_URL = BASE_URL+"login"+"/";
    public final String RESET_PWD = BASE_URL+"resetPassword"+"/";
    public final String REGISTER_URL = BASE_URL+"register"+"/";
    public final String pinApi = "https://api.postalpincode.in/pincode/";
}
