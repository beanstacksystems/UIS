package com.bss.uis.ui.tabFragment;

import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.List;

public class TabAdapter extends FragmentStatePagerAdapter {
    List<DynamicTabFragment> tabFragmentList;

    public TabAdapter(FragmentManager fm, List<DynamicTabFragment> tabFragmentList) {
        super(fm);
        this.tabFragmentList=tabFragmentList;
    }

    @Override
    public Fragment getItem(int position) {
            return tabFragmentList.get(position);
    }

    @Override
    public int getCount() {
        return tabFragmentList.size();
    }
}