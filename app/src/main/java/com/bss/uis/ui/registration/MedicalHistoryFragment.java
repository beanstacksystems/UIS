package com.bss.uis.ui.registration;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;
import android.widget.TextView;
import android.widget.Toast;

import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.AutoCompleteTextValidtor;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.entity.MedicalHistory;
import com.bss.uis.database.relation.PatientDetailData;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.util.AppUtil;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.Calendar;


public class MedicalHistoryFragment extends BaseFragment {
    TextInputEditText cancerType,dateOfIdentification,otherdiseasetxt;
    AutoCompleteTextView bloodGrp;
    TextInputLayout cancerTypeInputLayout,dateOfIdentificationInputLayout,otherdiseasetxtInputLayout,bloodGrpInputLayout;
    String fragmentTitle;
    DatePickerDialog picker;
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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_medical_history, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    private void initView(View fragmentView) {
        cancerType = fragmentView.findViewById(R.id.cancerTypetxt);
        otherdiseasetxt = fragmentView.findViewById(R.id.otherdiseasetxt);;
        cancerTypeInputLayout = fragmentView.findViewById(R.id.cancerTypetxtLayout);
        otherdiseasetxtInputLayout = fragmentView.findViewById(R.id.otherdiseasetxtLayout);
        cancerType.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (cancerTypeInputLayout,"Field cannot be empty"));
        cancerType.addTextChangedListener(new CustomTextValidator(cancerType) {
            @Override
            public void validate(TextView textView, String text) {
                cancerTypeInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    cancerTypeInputLayout.setError("Field cannot be empty");
                else if(!UIUtil.isContainsValidCharacter(text))
                    cancerTypeInputLayout.setError("Only alphanumeric characters allowed");
            }
        });

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
    }
    private void initDateOfIdentification(View fragmentView) {
        dateOfIdentification = fragmentView.findViewById(R.id.dateOfidentification);
        dateOfIdentificationInputLayout = fragmentView.findViewById(R.id.dateOfidentificationLayout);
        dateOfIdentification.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final Calendar cldr = Calendar.getInstance();
                int day = cldr.get(Calendar.DAY_OF_MONTH);
                int month = cldr.get(Calendar.MONTH);
                int year = cldr.get(Calendar.YEAR);
                // date picker dialog
                picker = new DatePickerDialog(getActivity(),
                        new DatePickerDialog.OnDateSetListener() {
                            @Override
                            public void onDateSet(DatePicker view, int year, int monthOfYear, int dayOfMonth) {
                                dateOfIdentification.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
        dateOfIdentification.addTextChangedListener(new CustomTextValidator(dateOfIdentification) {
            @Override
            public void validate(TextView textView, String text) {
                dateOfIdentificationInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    dateOfIdentificationInputLayout.setError("Date cannot be empty");
            }
        });
        dateOfIdentification.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (dateOfIdentificationInputLayout,"Date cannot be empty"));
    }

    private void initBloodGroupView(View fragmentView)
    {
        bloodGrp = fragmentView.findViewById(R.id.blood_group);
        bloodGrpInputLayout = fragmentView.findViewById(R.id.spinner_blood_group);
        ArrayList<String> bloodGroups = AppUtil.getMasterByType("bloodGrp");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, bloodGroups);
        bloodGrp.setAdapter(adapter);
        bloodGrp.setValidator(new AutoCompleteTextValidtor(bloodGrpInputLayout,bloodGroups));
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
    public void updateDetails(PatientDetailData  patientDetailData) {
        MedicalHistory medicalHistory = patientDetailData.getMedicalHistory();
        medicalHistory.setCancerType(cancerType.getText().toString());
        medicalHistory.setIdentifiedOn(dateOfIdentification.getText().toString());
        medicalHistory.setBloodGroup(bloodGrp.getText().toString());
        medicalHistory.setOtherDisease(otherdiseasetxt.getText().toString());
    }
}