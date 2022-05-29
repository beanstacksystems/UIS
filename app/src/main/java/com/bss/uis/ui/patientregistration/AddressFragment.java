package com.bss.uis.ui.patientregistration;

import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.text.InputType;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.beanstack.utility.alertDialog.AppAlertDialog;
import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.dto.PatientDTO;
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
        setRetainInstance(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_address, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    @Override
    public void onFragmentHide(){};
    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onFragmentVisible(){
        if(pin.getVisibility()==View.VISIBLE && pin.getText().toString().isEmpty())
            createPinPopup();
    };
    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    @Override
    public boolean isValidDetails() {
        String streettxt = streetAdd.getText().toString();
        String cityTxt = city.getText().toString();
        String distTxtTxt = dist.getText().toString();
        String stateTxt = state.getText().toString();
        String pinTxt = pin.getText().toString();
        if(null == streettxt ||streettxt.isEmpty()|| null == cityTxt ||cityTxt.isEmpty()
                || null == distTxtTxt || distTxtTxt.isEmpty()
                || null == stateTxt || stateTxt.isEmpty()
                ||   null == pinTxt || pinTxt.isEmpty()){
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillvalue),Toast.LENGTH_LONG).show();
            return false;
        }

        if(null != streetInputLayout.getError()||null != cityInputLayout.getError()
                ||null != distInputLayout.getError()
                ||null != stateInputLayout.getError()||null != pinLayout.getError())
            return false;
        return true;
    }

    @Override
    public void updatePatientDTO(PatientDTO patientDTO) {

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
                    pinLayout.setError("Invlid pin");
                if(text.length() == 6)
                    new APIServiceImpl().fetchPinData(text,state,dist,pinLayout);
            }
        });
    }
    @RequiresApi(api = VERSION_CODES.M)
    private void createPinPopup()
    {
        final TextInputLayout textInputLayout = UIUtil.getTextInputLayout(this.getActivity(),
                0, InputType.TYPE_CLASS_NUMBER,"PinCode");
        AppAlertDialog dialog = new AppAlertDialog(this.getActivity());
        dialog.showCustomDialog(4,"Pin",null,false,textInputLayout,new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                Button button = (Button) v;
                if(button.getText().equals("Ok"))
                {
                    pin.setText(textInputLayout.getEditText().getText().toString());
                    dialog.dismiss();
                }
                if(button.getText().equals("Cancel"))
                {
                    dialog.dismiss();
                }
            }
        });
        dialog.show();
        return;
    }
}