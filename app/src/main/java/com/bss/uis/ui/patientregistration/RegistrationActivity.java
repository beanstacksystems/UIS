package com.bss.uis.ui.patientregistration;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;

import com.beanstack.utility.TextWithIconHeaderView;
import com.beanstack.utility.stepprogress.StepProgressBar;
import com.beanstack.utility.stepprogress.StepProgressBar.StateNumber;
import com.beanstack.utility.viewpagerutil.CustomViewPagerNoTouchNoSwipe;
import com.beanstack.utility.viewpagerutil.ZoomOutPageTransformer;
import com.bss.uis.R;
import com.bss.uis.dto.PatientDTO;
import com.bss.uis.ui.UIUtil;
import com.bss.uis.util.AppUtil;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends FragmentActivity implements View.OnClickListener {
    protected String[] descriptionData = {"Person Details", "Address", "Medical History", "Attendant"};
    TextWithIconHeaderView textWithIconHeaderView;
    protected Button nextBtn,backBtn;
    protected StepProgressBar stepProgressBar;
    protected ImageView backArrow;
    private PatientDTO patientDTO;
    private static final int NUM_PAGES = 5;


    private CustomViewPagerNoTouchNoSwipe mPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        patientDTO = AppUtil.getPatientDTO();
        textWithIconHeaderView = findViewById(R.id.fragmentTitle);
        backArrow = findViewById(R.id.back);
        backArrow.setColorFilter(R.color.black);
        backArrow.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
                overridePendingTransition(0,0);
            }
        });
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (CustomViewPagerNoTouchNoSwipe) findViewById(R.id.regpager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        BaseFragment personalDetailFragment = PersonalDetailFragment.newInstance(getResources().getString(R.string.fragment_personDetail_title),"ONE");
        BaseFragment addressFragment = AddressFragment.newInstance(getResources().getString(R.string.fragment_address_title),"TWO");
        BaseFragment medicalFragment = MedicalHistoryFragment.newInstance(getResources().getString(R.string.fragment_medical_title),"THREE");
        BaseFragment attendantFragment = AttendantFragment.newInstance(getResources().getString(R.string.fragment_attendant_title),"FOUR");
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(personalDetailFragment);
        fragmentList.add(addressFragment);
        fragmentList.add(medicalFragment);
        fragmentList.add(attendantFragment);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragmentList);
        mPager.setAdapter(pagerAdapter);
        mPager.setPagingEnabled(false);
        initBaseView();
    }

    @Override
    public void onBackPressed() {
        if (mPager.getCurrentItem() == 0) {
            // If the user is currently looking at the first step, allow the system to handle the
            // Back button. This calls finish() on this activity and pops the back stack.
            super.onBackPressed();
        } else {
            // Otherwise, select the previous step.
            mPager.setCurrentItem(mPager.getCurrentItem() - 1);
        }
    }

    public PatientDTO getPatientDTO()
    {
        return patientDTO;
    }
    @Override
    public void onClick(View v) {
        ScreenSlidePagerAdapter adapter = (ScreenSlidePagerAdapter)mPager.getAdapter();
        BaseFragment curfragment = (BaseFragment) adapter.getItem(mPager.getCurrentItem());
        if(v==nextBtn)
        {
            if(curfragment.isValidDetails())
            {
                curfragment.updatePatientDTO(patientDTO);
                curfragment = (BaseFragment) adapter.getItem(mPager.getCurrentItem()+1);
//                stepProgressBar.setCurrentStateNumber(StateNumber.valueOf(curfragment.getProgressState()));
//                if(curfragment.getFragmentTitle().equals("Address Details") ||
//                        curfragment.getFragmentTitle().equals("Attendant Details"))
//                    curfragment.onFragmentVisible();
                if(((Button) v).getText()=="Submit")
                {
                    Toast.makeText(getApplicationContext(), "submit", Toast.LENGTH_SHORT).show();
                    return;
                }
                updateFragmentView(mPager.getCurrentItem()+1);
            }
           else
               System.out.println("Not ok");
        }
        if(v==backBtn)
        {
            updateFragmentView(mPager.getCurrentItem()-1);
        }

    }

    /**
     * A simple pager adapter that represents 5 ScreenSlidePageFragment objects, in
     * sequence.
     */
    private class ScreenSlidePagerAdapter extends FragmentStatePagerAdapter {
        List<BaseFragment> fragmentList;
        public ScreenSlidePagerAdapter(FragmentManager fm,List<BaseFragment> fragmentList) {
            super(fm);
            this.fragmentList = fragmentList;
        }

        @Override
        public Fragment getItem(int position) {
            return (fragmentList.size()> position)?
                    fragmentList.get(position):null;
        }
        @Override
        public String getPageTitle(int position) {
            return (fragmentList.size()> position)?
                    fragmentList.get(position).getFragmentTitle():"";
        }

        @Override
        public int getCount() {
            return fragmentList.size();
        }
    }
    protected void initBaseView() {
        initButtons();
        stepProgressBar = (StepProgressBar) findViewById(R.id.usage_stateprogressbar);
        stepProgressBar.setStateDescriptionData(descriptionData);
        stepProgressBar.setCurrentStateNumber(StepProgressBar.StateNumber.ONE);
        updateFragmentView(0);
    }
    protected void initButtons() {
        nextBtn = (Button) findViewById(R.id.btnNext);
        nextBtn.setOnClickListener(this);
        backBtn = (Button) findViewById(R.id.btnBack);
        backBtn.setOnClickListener(this);
    }
    public void updateFragmentView(int currentFragmentIndex) {
        UIUtil.updateButtonStatus(nextBtn,true,R.color.colorPrimary,getResources().getString(R.string.next));
        UIUtil.updateButtonStatus(backBtn,true,R.color.colorPrimary,null);
        mPager.getRootView().clearFocus();
        mPager.setCurrentItem(currentFragmentIndex,true);
        textWithIconHeaderView.setHeaderTitle(String.valueOf(mPager.getAdapter().getPageTitle(currentFragmentIndex)));
        if(currentFragmentIndex==mPager.getAdapter().getCount()-1)
            UIUtil.updateButtonStatus(nextBtn,true,R.color.colorPrimary,getResources().getString(R.string.submit));
        if(currentFragmentIndex==0)
            UIUtil.updateButtonStatus(backBtn,false,R.color.colorAccent,null);
    }




}
