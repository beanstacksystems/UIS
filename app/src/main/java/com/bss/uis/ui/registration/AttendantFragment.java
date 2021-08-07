package com.bss.uis.ui.registration;

import android.os.Build.VERSION_CODES;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.RequiresApi;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;
import androidx.viewpager.widget.ViewPager;

import com.beanstack.utility.listener.TextInputLayoutFocusChangeListener;
import com.beanstack.utility.validators.AutoCompleteTextValidtor;
import com.beanstack.utility.validators.CustomTextValidator;
import com.bss.uis.R;
import com.bss.uis.context.UISApplicationContext;
import com.bss.uis.database.relation.PatientDetailData;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.ui.image.ProfileImageFragment;
import com.bss.uis.ui.tabFragment.DynamicTabFragment;
import com.bss.uis.ui.tabFragment.TabAdapter;
import com.google.android.material.tabs.TabLayout;
import com.google.android.material.tabs.TabLayout.Tab;
import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;

import java.util.ArrayList;
import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class AttendantFragment extends BaseFragment {
    TextInputEditText name,email,contact,dob,panadhar;
    TextInputLayout occupationInputLayout,eMailInputLayout,contactInputLayout,
            dobInputLayout,genderLayout,panadhartxtLayout,nameLayout;
    AutoCompleteTextView occupation,gender;
    private String fragmentTitle;
    private TabLayout tabLayout;
    List<DynamicTabFragment> tabFragList = null;
    private boolean isTabContentInitialized = false;
    CircleImageView profile_image;
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
        return attview;
    }
    @Override
    public void onFragmentHide(){}

    @RequiresApi(api = VERSION_CODES.M)
    @Override
    public void onFragmentVisible(){
        if(!isTabContentInitialized)
        {
            for(DynamicTabFragment frag:tabFragList) {
                initTabView(frag,0);
            }
            isTabContentInitialized = true;
        }
    }

    private void initTabView(Fragment dynamicTabFragment, int index)
    {
        View tabview = dynamicTabFragment.getView();
        profile_image = tabview.findViewById(R.id.profile_image_pd);
        name = tabview.findViewById(R.id.personName_persondetail);
        nameLayout = tabview.findViewById(R.id.personName_persondetail_layout);
        email = tabview.findViewById(R.id.Email_et_persondetail);
        eMailInputLayout = tabview.findViewById(R.id.Email_etLayout_persondetail);
        contact = tabview.findViewById(R.id.contact_et_persondetail);
        contactInputLayout = tabview.findViewById(R.id.contactInputLayout_persondetail);
        panadhar = tabview.findViewById(R.id.panAdharPdetail);
        name.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (nameLayout,"Name cannot be empty"));
        name.addTextChangedListener(new CustomTextValidator(name) {
            @Override
            public void validate(TextView textView, String text) {
                nameLayout.setError(null);
                if(null == text || text.isEmpty())
                    nameLayout.setError("Name cannot be empty");
                else if(!UIUtil.isContainsValidCharacter(text))
                    nameLayout.setError("Only alphanumeric characters allowed");
            }
        });
        email.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (eMailInputLayout,"Email cannot be empty"));
        email.addTextChangedListener(new CustomTextValidator(email) {
            @Override
            public void validate(TextView textView, String text) {
                eMailInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    eMailInputLayout.setError("Email cannot be empty");
                else if(!UIUtil.isEmailValid(text))
                    eMailInputLayout.setError("Invalid E-Mail Id");
            }
        });
        contact.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (contactInputLayout,"Mobile no cannot be empty"));
        contact.addTextChangedListener(new CustomTextValidator(contact) {
            @Override
            public void validate(TextView textView, String text) {
                contactInputLayout.setError(null);
                if(null == text || text.isEmpty())
                    contactInputLayout.setError("Mobile no cannot be empty");
                else if(text.length()< 10)
                    contactInputLayout.setError("Mobile number is not Correct");
            }
        });
        panadhartxtLayout = tabview.findViewById(R.id.panAdharPdetailLayout_pDetail);
        panadhar.setOnFocusChangeListener(new TextInputLayoutFocusChangeListener
                (panadhartxtLayout,"Pan/Aadhar no cannot be empty"));
        panadhar.addTextChangedListener(new CustomTextValidator(panadhar) {
            @Override
            public void validate(TextView textView, String text) {
                panadhartxtLayout.setError(null);
                if(null == text || text.isEmpty())
                    panadhartxtLayout.setError("Pan/Aadhar no cannot be empty");
                else if(text.length()< 10)
                    panadhartxtLayout.setError("Pan/Aadhar is not Correct");
            }
        });
        initSpinnerView(tabview);
    }
    private void initSpinnerView(View fragmentView)
    {
        occupation = fragmentView.findViewById(R.id.spinner_occupation);
        occupationInputLayout = fragmentView.findViewById(R.id.spinner_occupation_layout);
        ArrayList<String> occupationList = new ArrayList<>();
        occupationList.add("Others");
        occupationList.add("Self employed");
        occupationList.add("House wife");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(getActivity(), android.R.layout.simple_spinner_item, occupationList);
        occupation.setAdapter(adapter);
        occupation.setValidator(new AutoCompleteTextValidtor(occupationInputLayout,occupationList));
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
    public ProfileImageFragment getProfileImageFragment(Boolean bool, int id) {
        ProfileImageFragment fragment = ProfileImageFragment.newInstance(profile_image);
        FragmentTransaction transaction = getChildFragmentManager().beginTransaction();
        transaction.replace(id, fragment);
        if (bool)
            transaction.addToBackStack(null);
        transaction.commit();
        return fragment;
    }
}