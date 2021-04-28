package com.bss.uis.ui.tabFragment;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.fragment.app.Fragment;

import com.bss.uis.R;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class DynamicTabFragment extends Fragment {
    private String tabTitle,tabTable,tabDescription;
    public DynamicTabFragment() {
    }

    public static DynamicTabFragment newInstance(String tabTitle, String tabTable,String tabDescription) {
        DynamicTabFragment fragment = new DynamicTabFragment();
        fragment.setTabTitle(tabTitle);
        fragment.setTabTable(tabTable);
        fragment.setTabDescription(tabDescription);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root =  inflater.inflate(R.layout.fragment_dynamic_tab, container, false);
        TextView tabDesc = (TextView) root.findViewById(R.id.tabDesc);
        tabDesc.setText(tabDescription);
        return root;
    }


}