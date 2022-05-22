package com.bss.uis.service.impl;

import android.content.Context;

import com.beanstack.utility.alertDialog.AppAlertDialog;
import com.beanstack.utility.service.NavigationService;
import com.beanstack.utility.service.impl.NavigationServiceImpl;
import com.bss.uis.constant.APIConstant;
import com.bss.uis.model.AddressEx;
import com.bss.uis.service.APIService;
import com.bss.uis.service.APISignatureService;
import com.bss.uis.util.CustomJsonDesrializer;
import com.bss.uis.util.RetrofitUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.gson.GsonBuilder;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class APIServiceImpl implements APIService{
    private APISignatureService apiSignatureService;
    @Override
    public void fetchPinData(String pincode, final TextInputEditText state, final TextInputEditText dist,
                             final TextInputLayout pin) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.pinApi,
        new GsonBuilder()
                .registerTypeAdapter(AddressEx.class, new CustomJsonDesrializer<>(AddressEx.class,"PostOffice"))
                .create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<List<AddressEx>> apiCall = apiSignatureService.fetchPinData(pincode);
        apiCall.enqueue(new Callback<List<AddressEx>>() {
            @Override
            public void onResponse(Call<List<AddressEx>> call, Response<List<AddressEx>> response) {
                System.out.println(response.body());
                //In this point we got our hero list
                //thats damn easy right ;)
                if(null == response.body().get(0)){
                    pin.setError("No such pin exist");
                    return;
                }

                state.setText(response.body().get(0).getState());
                dist.setText(response.body().get(0).getDistrict());
                //now we can do whatever we want with this list
            }


            @Override
            public void onFailure(Call<List<AddressEx>> call, Throwable t) {
                System.out.println(call);
            }
        });
    }

    @Override
    public void isserverreachable(NavigationService navigationService, Context context) {
        Retrofit retrofit = RetrofitUtil.getRetrofitClient2(APIConstant.SERVERAVAILABLECHECKAPI,
                new GsonBuilder().create());
        apiSignatureService = retrofit.create(APISignatureService.class);
        Call<String> apiCall = apiSignatureService.isserverreachable();
        apiCall.enqueue(new Callback<String>() {
            @Override
            public void onResponse(Call<String> call, Response<String> response) {
                if(response.code() == 503)
                {
                    new AppAlertDialog(context,new NavigationServiceImpl(null,null){
                        @Override
                        public void buttonAction(String text) {
                            super.buttonAction(text);
                            System.exit(0);
                        }
                    }).getDialog(0,"Sorry",response.message(),true,null).show();
                    return;
                }
                navigationService.finishAndNavigate();
            }
            @Override
            public void onFailure(Call<String> call, Throwable t) {
                new AppAlertDialog(context,new NavigationServiceImpl(null,null){
                    @Override
                    public void buttonAction(String text) {
                        super.buttonAction(text);
                        System.exit(0);
                    }
                }).getDialog(0,"Sorry","Not able to connect Server",true,null).show();
                return;
            }
        });
    }
}
