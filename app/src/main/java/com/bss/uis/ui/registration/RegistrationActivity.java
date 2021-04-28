package com.bss.uis.ui.registration;

import android.os.Bundle;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnTouchListener;
import android.widget.Button;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;
import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.beanstack.utility.TextWithIconHeaderView;
import com.beanstack.utility.stepprogress.StepProgressBar;
import com.beanstack.utility.stepprogress.StepProgressBar.StateNumber;
import com.bss.uis.R;
import com.bss.uis.ui.viewpageranimation.ZoomOutPageTransformer;

import java.util.ArrayList;
import java.util.List;

public class RegistrationActivity extends FragmentActivity implements View.OnClickListener {
    protected String[] descriptionData = {"Person Details", "Address", "Medical History", "Records"};
    TextWithIconHeaderView textWithIconHeaderView;
    protected Button nextBtn,backBtn;
    protected StepProgressBar stepProgressBar;

    private static final int NUM_PAGES = 5;


    private ViewPager mPager;

    private PagerAdapter pagerAdapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
        textWithIconHeaderView = findViewById(R.id.fragmentTitle);
        // Instantiate a ViewPager and a PagerAdapter.
        mPager = (ViewPager) findViewById(R.id.pager);
        mPager.setPageTransformer(true, new ZoomOutPageTransformer());
        BaseFragment personalDetailFragment = PersonalDetailFragment.newInstance(getResources().getString(R.string.fragment_personDetail_title));
        BaseFragment addressFragment = AddressFragment.newInstance(getResources().getString(R.string.fragment_address_title));
        List<BaseFragment> fragmentList = new ArrayList<>();
        fragmentList.add(personalDetailFragment);
        fragmentList.add(addressFragment);
        pagerAdapter = new ScreenSlidePagerAdapter(getSupportFragmentManager(),fragmentList);
        mPager.setAdapter(pagerAdapter);
        mPager.setOnTouchListener(new OnTouchListener() {
            public boolean onTouch(View arg0, MotionEvent arg1) {
                return true;
            }
        });
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

    @Override
    public void onClick(View v) {
        int state = stepProgressBar.getCurrentStateNumber();
        if(v==nextBtn)
        {
            if(state == 1) {
                stepProgressBar.setCurrentStateNumber(StateNumber.TWO);
                updateFragmentView(mPager.getCurrentItem()+1);
            }
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
        nextBtn.setEnabled(true);
        backBtn.setEnabled(true);
        mPager.setCurrentItem(currentFragmentIndex,true);
        textWithIconHeaderView.setHeaderTitle(String.valueOf(mPager.getAdapter().getPageTitle(currentFragmentIndex)));
        if(currentFragmentIndex==mPager.getAdapter().getCount()-1)
            nextBtn.setEnabled(false);
        if(currentFragmentIndex==0)
            backBtn.setEnabled(false);
    }



}
