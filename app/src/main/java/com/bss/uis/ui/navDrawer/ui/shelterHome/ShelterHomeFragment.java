package com.bss.uis.ui.navDrawer.ui.shelterHome;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import com.bss.uis.R;


public class ShelterHomeFragment extends Fragment {

    private ShelterHomeViewModel shelterHomeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        shelterHomeViewModel =
                new ViewModelProvider(this).get(ShelterHomeViewModel.class);
        View root = inflater.inflate(R.layout.fragment_shelterhome, container, false);
        final TextView textView = root.findViewById(R.id.text_shelterHome);
        shelterHomeViewModel.getText().observe(getViewLifecycleOwner(), new Observer<String>() {
            @Override
            public void onChanged(@Nullable String s) {
                textView.setText(s);
            }
        });
        return root;
    }
}