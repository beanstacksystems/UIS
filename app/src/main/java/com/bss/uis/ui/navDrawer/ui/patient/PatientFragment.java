package com.bss.uis.ui.navDrawer.ui.patient;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bss.uis.R;
import com.bss.uis.ui.UIUtil;


public class PatientFragment extends Fragment {

    private PatientViewModel patientViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        patientViewModel =
                new ViewModelProvider(this).get(PatientViewModel.class);
        View root = inflater.inflate(R.layout.fragment_patients, container, false);
        final LinearLayout linearLayout = root.findViewById(R.id.linearLayoutPatients);
        linearLayout.removeAllViews();
        patientViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                linearLayout.addView(UIUtil.getButtonWithImg(getContext(),
                        getContext().getResources(). getDrawable( R.drawable.ic_man),
                        "pupu",R.drawable.bg_popup_dialog,linearLayout.getHeight(),linearLayout.getWidth()));
            }
        });
        return root;
    }
}