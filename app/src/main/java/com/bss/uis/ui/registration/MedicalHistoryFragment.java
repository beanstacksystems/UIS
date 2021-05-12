package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bss.uis.R;
import com.bss.uis.database.entity.Patient;


public class MedicalHistoryFragment extends BaseFragment {

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

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        View fragmentView = inflater.inflate(R.layout.fragment_medical_history, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    private void initView(View fragmentView) {
    }

    @Override
    public boolean isValidDetails() {
        return true;
    }
    @Override
    public void updateDetails(Patient patient) {
    }
}