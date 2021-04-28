package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.bss.uis.R;
import com.google.android.material.textfield.TextInputEditText;

public class AddressFragment extends BaseFragment {
    TextInputEditText streetAdd,city,dist,state,pin;
    String fragmentTitle;
    public AddressFragment() {
        // Required empty public constructor
    }

    public static AddressFragment newInstance(String fragmentTitle) {
        AddressFragment fragment = new AddressFragment();
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
        View fragmentView = inflater.inflate(R.layout.fragment_address, container, false);
        initView(fragmentView);
        return fragmentView;
    }

    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    @Override
    public boolean isValidDetails() {
        return false;
    }

    private void initView(View fragmentView) {
        streetAdd = fragmentView.findViewById(R.id.streetAdd);
        city = fragmentView.findViewById(R.id.city);
        dist = fragmentView.findViewById(R.id.dist);
        state = fragmentView.findViewById(R.id.state);
        pin = fragmentView.findViewById(R.id.pincode);
    }
}