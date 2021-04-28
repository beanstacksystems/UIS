package com.bss.uis.ui.registration;

import android.app.DatePickerDialog;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.DatePicker;

import com.bss.uis.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.ArrayList;
import java.util.Calendar;


public class PersonalDetailFragment extends BaseFragment{

    TextInputEditText name,email,contact,dob,gender;
    DatePickerDialog picker;
    String fragmentTitle;
    public PersonalDetailFragment() {
        // Required empty public constructor
    }

    public static PersonalDetailFragment newInstance(String fragmentTitle) {
        PersonalDetailFragment fragment = new PersonalDetailFragment();
        fragment.fragmentTitle = fragmentTitle;
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_personal_detail, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    private void initView(View fragmentView) {
        name = fragmentView.findViewById(R.id.personName);
        email = fragmentView.findViewById(R.id.Email_et);
        contact = fragmentView.findViewById(R.id.contact_et);
        initDOB(fragmentView);
        initGenderView(fragmentView);
    }
    private void initDOB(View fragmentView) {
        dob = fragmentView.findViewById(R.id.dateOfBirth);
        dob.setOnClickListener(new View.OnClickListener() {
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
                                dob.setText(dayOfMonth + "/" + (monthOfYear + 1) + "/" + year);
                            }
                        }, year, month, day);
                picker.show();
            }
        });
    }

    private void initGenderView(View fragmentView)
    {
        final AutoCompleteTextView genderAutoTV = fragmentView.findViewById(R.id.spinner_gender);
        ArrayList<String> genderValue = new ArrayList<>();
        genderValue.add("Female");
        genderValue.add("Male");
        genderValue.add("ThirdGender");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, genderValue);
        genderAutoTV.setAdapter(adapter);
    }
    @Override
    public boolean isValidDetails() {
        return false;
    }
}