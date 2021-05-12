package com.bss.uis.ui.registration;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.InputType;
import android.view.ContextThemeWrapper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.TextView;

import androidx.annotation.RequiresApi;

import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.service.impl.APIServiceImpl;
import com.bss.uis.ui.UIUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

public class AddressFragment extends BaseFragment {
    TextInputEditText streetAdd,city,dist,state,pin;
    TextInputLayout streetInputLayout,cityInputLayout,distInputLayout,stateInputLayout,pinLayout;
    String fragmentTitle;
    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance(String fragmentTitle,String progressState) {
        AddressFragment fragment = new AddressFragment();
        fragment.fragmentTitle = fragmentTitle;
        fragment.setProgressState(progressState);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_address, container, false);
        initView(fragmentView);
        return fragmentView;
    }
    @Override
    @RequiresApi(api = VERSION_CODES.M)
    public void onStart() {
        super.onStart();
        if(pin.getVisibility()==View.VISIBLE && pin.getText().toString().isEmpty())
            createPinPopup();
    }
    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    @Override
    public boolean isValidDetails() {
        return true;
    }

    @Override
    public void updateDetails(Patient patient) {
    }


    private void initView(View fragmentView) {
        streetAdd = fragmentView.findViewById(R.id.streetAdd);
        city = fragmentView.findViewById(R.id.city);
        dist = fragmentView.findViewById(R.id.dist);
        state = fragmentView.findViewById(R.id.state);
        pin = fragmentView.findViewById(R.id.pincode);

        streetInputLayout = fragmentView.findViewById(R.id.streetAddLayout);
        cityInputLayout = fragmentView.findViewById(R.id.cityLayout);
        distInputLayout = fragmentView.findViewById(R.id.distLayout);
        stateInputLayout = fragmentView.findViewById(R.id.stateLayout);
        pinLayout = fragmentView.findViewById(R.id.pincodeLayout);
        streetAdd.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (streetInputLayout,"Street Address cannot be empty"));
        streetAdd.addTextChangedListener(new CustomTextValidator(streetAdd) {
            @Override
            public void validate(TextView textView, String text) {
                streetInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    streetInputLayout.setError("Street Address cannot be empty");
                if(!UIUtil.isContainsValidCharacter(text))
                    streetInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        city.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (cityInputLayout,"City cannot be empty"));
        city.addTextChangedListener(new CustomTextValidator(city) {
            @Override
            public void validate(TextView textView, String text) {
                cityInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    cityInputLayout.setError("City cannot be empty");
                if(!UIUtil.isContainsValidCharacter(text))
                    cityInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        dist.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (distInputLayout,"District cannot be empty"));
        dist.addTextChangedListener(new CustomTextValidator(dist) {
            @Override
            public void validate(TextView textView, String text) {
                distInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    distInputLayout.setError("District cannot be empty");
                if(!UIUtil.isContainsValidCharacter(text))
                    distInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        state.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (stateInputLayout,"State cannot be empty"));
        state.addTextChangedListener(new CustomTextValidator(state) {
            @Override
            public void validate(TextView textView, String text) {
                stateInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    stateInputLayout.setError("State cannot be empty");
                if(!UIUtil.isContainsValidCharacter(text))
                    stateInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        pin.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (pinLayout,"Pin cannot be empty"));
        pin.addTextChangedListener(new CustomTextValidator(pin) {
            @Override
            public void validate(TextView textView, String text) {
                pinLayout.setError(null);
                if(null == text || text.isEmpty())
                    pinLayout.setError("pin cannot be empty");
                if(text.length()< 6)
                    pinLayout.setError("Mobile number is not Correct");
                if(text.length() == 6)
                    new APIServiceImpl().fetchPinData(text,state,dist);
            }
        });
    }
    @RequiresApi(api = VERSION_CODES.M)
    private void createPinPopup()
    {
        final AlertDialog.Builder builder = new AlertDialog.Builder(new ContextThemeWrapper(this.getActivity(),null));
        int displayWidth = UIUtil.getscreenwidth(this.getActivity().getWindow());
        int displayHeight = UIUtil.getscreenheight(this.getActivity().getWindow());
        final TextInputLayout textInputLayout = UIUtil.getTextInputLayout(this.getActivity(),
                0,(int)(displayHeight*0.2f),displayWidth-200, InputType.TYPE_CLASS_NUMBER,"PinCode");
        WindowManager.LayoutParams layoutParams = new WindowManager.LayoutParams();
        int dialogWindowWidth = displayWidth-200;
        int dialogWindowHeight = (int) (displayHeight * 1.5f);
        layoutParams.width = dialogWindowWidth;
        layoutParams.height = dialogWindowHeight;
        builder.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick( DialogInterface dialog,int which)
            {
                pin.setText(textInputLayout.getEditText().getText().toString());
            }
        });
        builder.setNegativeButton("SKIP", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog,int which)
            {
                dialog.dismiss();
            }
        });
        AlertDialog dialog = builder.create();
        layoutParams.copyFrom(dialog.getWindow().getAttributes());
        dialog.getWindow().setBackgroundDrawableResource(R.drawable.bg_alert_dialog);
        dialog.getWindow().setAttributes(layoutParams);
        dialog.setView(textInputLayout);
        dialog.show();
    }
}