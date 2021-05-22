package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.viewpager.widget.ViewPager;

import com.beanstack.utility.validators.AutoCompleteTextValidtor;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.relation.PatientDetailData;
import com.bss.uis.ui.tabFragment.DynamicTabFragment;
import com.bss.uis.ui.tabFragment.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

public class AttendantFragment extends BaseFragment {
    TextInputEditText name,email,contact,dob,panadhar;
    TextInputLayout occupationInputLayout,eMailInputLayout,contactInputLayout,
            dobInputLayout,genderLayout,panadhartxtLayout;
    AutoCompleteTextView occupation,gender;
    private String fragmentTitle;
    private TabLayout tabLayout;
    List<DynamicTabFragment> tabFragList = null;
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
        String att_name = name.getText().toString();
        String att_email = email.getText().toString();
        String  att_contact= contact.getText().toString();
        String att_panadhar = panadhar.getText().toString();
        if(null == att_name ||att_name.isEmpty()|| null == att_email ||att_email.isEmpty()
                || null == att_contact || att_contact.isEmpty()
                || null == att_panadhar || att_panadhar.isEmpty()){
            Toast.makeText(UISApplicationContext.getInstance().getContext(),
                    getResources().getString(R.string.fillvalue),Toast.LENGTH_LONG).show();
            return false;
        }

//        if(null != bloodGrpInputLayout.getError()||null != cancerTypeInputLayout.getError()
//                ||null != otherdiseasetxtInputLayout.getError()
//                ||null != dateOfIdentificationInputLayout.getError())
//            return false;
        return true;
    }

    @Override
    public void updateDetails(PatientDetailData patientDetailData) {
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View attview =  inflater.inflate(R.layout.fragment_attendant_record, container, false);
        tabLayout = attview.findViewById(R.id.attendant_tabLayout);
        tabFragList = getTabFragmentList(attview,1);
        for(DynamicTabFragment frag:tabFragList) {
            tabLayout.addTab(tabLayout.newTab().setText(frag.getTabTitle()));
        }
        final ViewPager tabViewPager = (ViewPager)attview.findViewById(R.id.attendant_tabviewPager);
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
        int index = 0;
        for(DynamicTabFragment frag:tabFragList) {
            initTabView(tabAdapter.getItem(index),index);
            index++;
        }


        return attview;
    }
    private void initTabView(Fragment dynamicTabFragment, int index)
    {
        View tabview = dynamicTabFragment.getView();
        name = tabview.findViewById(R.id.personName_persondetail);
        email = tabview.findViewById(R.id.Email_et_persondetail);
        contact = tabview.findViewById(R.id.contact_et_persondetail);
        panadhar = tabview.findViewById(R.id.panAdharPdetail);
        initSpinnerView(tabview);
    }
    private void initSpinnerView(View fragmentView)
    {
        occupation = fragmentView.findViewById(R.id.spinner_occupation);
        occupationInputLayout = fragmentView.findViewById(R.id.spinner_occupation_layout);
        ArrayList<String> bloodGroups = new ArrayList<>();
        bloodGroups.add("Others");
        bloodGroups.add("Self employed");
        bloodGroups.add("House wife");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, bloodGroups);
        occupation.setAdapter(adapter);
        occupation.setValidator(new AutoCompleteTextValidtor(occupationInputLayout,bloodGroups));
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