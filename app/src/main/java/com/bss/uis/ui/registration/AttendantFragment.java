package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.viewpager.widget.ViewPager;

import com.bss.uis.R;
import com.bss.uis.database.entity.Patient;
import com.bss.uis.ui.tabFragment.DynamicTabFragment;
import com.bss.uis.ui.tabFragment.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;

import java.util.ArrayList;
import java.util.List;

public class AttendantFragment extends BaseFragment {

    private String fragmentTitle;
    private TabLayout tabLayout;
    public AttendantFragment() {
        // Required empty public constructor
    }
    public static AttendantFragment newInstance(String fragmentTitle,String progressState) {
        AttendantFragment fragment = new AttendantFragment();
        fragment.fragmentTitle = fragmentTitle;
        fragment.setProgressState(progressState);
        return fragment;
    }
    @Override
    public String getFragmentTitle() {
        return fragmentTitle;
    }

    @Override
    public boolean isValidDetails() {
        return false;
    }

    @Override
    public void updateDetails(Patient patient) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view =  inflater.inflate(R.layout.fragment_attendant_record, container, false);
        tabLayout = view.findViewById(R.id.attendant_tabLayout);
        List<DynamicTabFragment> tabFragList = getTabFragmentList(view,2);
        for(DynamicTabFragment frag:tabFragList) {
            tabLayout.addTab(tabLayout.newTab().setText(frag.getTabTitle()));
        }
        final ViewPager tabViewPager = (ViewPager)view.findViewById(R.id.attendant_tabviewPager);
        TabAdapter tabAdapter = new TabAdapter(getActivity().getSupportFragmentManager(),tabFragList);
        tabViewPager.setAdapter(tabAdapter);
        tabViewPager.setOffscreenPageLimit(1);
        tabViewPager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener(){
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tabViewPager.setCurrentItem(tab.getPosition());
            }
            @Override
            public void onTabUnselected(Tab tab) {
            }
            @Override
            public void onTabReselected(Tab tab) {
            }

        });

        return view;
    }
    private List<DynamicTabFragment> getTabFragmentList(View view,int length)
    {
        List<DynamicTabFragment> tabFragList = new ArrayList<>();
        DynamicTabFragment tabFragment=null;
        for(int i=0; i< length;i++){
            tabFragment = DynamicTabFragment.newInstance("Attendant"+(i+1),"","",true,R.layout.person_basic_details);
            tabFragList.add(tabFragment);
        }
        return tabFragList;
    }
}