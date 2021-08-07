package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.bss.uis.R;
import com.bss.uis.database.relation.PatientDetailData;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseFragment extends Fragment {

    private String progressState;
    public BaseFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_base, container, false);
    }
    public abstract String getFragmentTitle();
    public abstract boolean isValidDetails();
    public abstract void updateDetails(PatientDetailData patientDetailData);
    public void onFragmentVisible(){}

    public void onFragmentHide(){}
}