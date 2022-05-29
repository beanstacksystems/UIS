package com.bss.uis.ui.patientregistration;

import android.app.Dialog;
import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;

import com.beanstack.utility.listener.DateChooserFocusChangeListener;
import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.dto.PatientDTO;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.util.AppUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;


public class MedicalHistoryFragment extends BaseFragment {
    TextInputEditText cancerType,dateOfIdentification,otherdiseasetxt,bloodGrp;
    TextInputLayout cancerTypeInputLayout,dateOfIdentificationInputLayout,otherdiseasetxtInputLayout,bloodGrpInputLayout;
    String fragmentTitle;
    public MedicalHistoryFragment() {
        // Required empty public constructor
    }

    public static MedicalHistoryFragment newInstance(String fragmentTitle,String progressState) {
        MedicalHistoryFragment fragment = new MedicalHistoryFragment();
        fragment.fragmentTitle = fragmentTitle;
        fragment.setProgressState(progressState);
        return fragment;
    }
    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_medical_history, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private void initView(View fragmentView) {
        otherdiseasetxt = fragmentView.findViewById(R.id.otherdiseasetxt);;
        otherdiseasetxtInputLayout = fragmentView.findViewById(R.id.otherdiseasetxtLayout);

        otherdiseasetxt.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (otherdiseasetxtInputLayout,"Field cannot be empty"));
        otherdiseasetxt.addTextChangedListener(new CustomTextValidator(otherdiseasetxt) {
            @Override
            public void validate(TextView textView, String text) {
                otherdiseasetxtInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    otherdiseasetxtInputLayout.setError("Field cannot be empty");
                else if(!UIUtil.isContainsValidCharacter(text))
                    otherdiseasetxtInputLayout.setError("Only alphanumeric characters allowed");
            }
        });
        initDateOfIdentification(fragmentView);
        initBloodGroupView(fragmentView);
        initCancerTypeView(fragmentView);
    }
    private void initDateOfIdentification(View fragmentView) {
        dateOfIdentification = fragmentView.findViewById(R.id.dateOfidentification);
        dateOfIdentificationInputLayout = fragmentView.findViewById(R.id.dateOfidentificationLayout);
        dateOfIdentification.addTextChangedListener(new CustomTextValidator(dateOfIdentification) {
            @Override
            public void validate(TextView textView, String text) {
                dateOfIdentificationInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    dateOfIdentificationInputLayout.setError("Date cannot be empty");
            }
        });
        dateOfIdentification.setOnFocusChangeListener(new DateChooserFocusChangeListener
                (dateOfIdentificationInputLayout,"Date cannot be empty"));
    }

    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private void initCancerTypeView(View fragmentView)
    {
        cancerType = fragmentView.findViewById(R.id.cancerTypetxt);
        cancerTypeInputLayout = fragmentView.findViewById(R.id.cancerTypetxtLayout);
        cancerType.setShowSoftInputOnFocus(false);
        ArrayList<String> illnessTypes = AppUtil.getMasterByType("illnesstype");
        cancerType.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                cancerTypeInputLayout.setError(null);
                TextInputEditText textInputEditText = (TextInputEditText)v;
                String text = textInputEditText.getText().toString();
                if(null == text || text.isEmpty())
                    cancerTypeInputLayout.setError("CancerType can not be empty.");
                if(hasFocus) {
                    Dialog dialog = UIUtil.getSelectPopupDialog(getActivity(),
                            "CancerType", illnessTypes.toArray(new String[illnessTypes.size()]), cancerType,cancerTypeInputLayout);
                    dialog.show();
                }
            }
        });
    }
    @RequiresApi(api = VERSION_CODES.LOLLIPOP)
    private void initBloodGroupView(View fragmentView)
    {
        bloodGrp = fragmentView.findViewById(R.id.blood_group);
        bloodGrpInputLayout = fragmentView.findViewById(R.id.spinner_blood_group);
        bloodGrp.setShowSoftInputOnFocus(false);
        ArrayList<String> bloodGroups = AppUtil.getMasterByType("bloodgroup");
        bloodGrp.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                bloodGrpInputLayout.setError(null);
                TextInputEditText textInputEditText = (TextInputEditText)v;
                String text = textInputEditText.getText().toString();
                if(null == text || text.isEmpty())
                    bloodGrpInputLayout.setError("BloodGroup can not be empty.");
                if(hasFocus) {
                    Dialog dialog = UIUtil.getSelectPopupDialog(getActivity(),
                            "BloodGroup", bloodGroups.toArray(new String[bloodGroups.size()]), bloodGrp,bloodGrpInputLayout);
                    dialog.show();
                }
            }
        });
    }


    @Override
    public boolean isValidDetails() {
        String bloodGrpTxt = bloodGrp.getText().toString();
        String cancerTypeTxt = cancerType.getText().toString();
        String  otherdisease= otherdiseasetxt.getText().toString();
        String dateTxt = dateOfIdentification.getText().toString();
        if(null == bloodGrpTxt ||bloodGrpTxt.isEmpty()|| null == cancerTypeTxt ||cancerTypeTxt.isEmpty()
                || null == otherdisease || otherdisease.isEmpty()
                || null == dateTxt || dateTxt.isEmpty()){
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillvalue),Toast.LENGTH_LONG).show();
            return false;
        }

        if(null != bloodGrpInputLayout.getError()||null != cancerTypeInputLayout.getError()
                ||null != otherdiseasetxtInputLayout.getError()
                ||null != dateOfIdentificationInputLayout.getError())
            return false;
        return true;
    }

    @Override
    public void updatePatientDTO(PatientDTO patientDTO) {

    }

}