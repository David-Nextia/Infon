package com.nextia.micuentainfonavit.ui.savings.tabs;
/**
 * adapter where each view page fragment will be
 */

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

import java.util.ArrayList;
import java.util.List;

public class ViewPagerAdapter extends FragmentStatePagerAdapter {

    private final List<Fragment> mFragmentList = new ArrayList<>();
    private final List<String> mFragmentTitleList = new ArrayList<>();

    //setting viewpage behavior
    public ViewPagerAdapter(@NonNull FragmentManager fm) {
        super(fm, BEHAVIOR_RESUME_ONLY_CURRENT_FRAGMENT);
    }

   //getItem method for adapter
    @NonNull
    @Override
    public Fragment getItem(int position) {
       return mFragmentList.get(position);
    }

    //getCount method for adapter
    @Override
    public int getCount() {
        return mFragmentList.size();
    }

    //method to add a view of some fragment
    public void addFragment(Fragment fragment, String title) {
        mFragmentList.add(fragment);
        mFragmentTitleList.add(title);
    }

    //getting title of page
    @Override
    public CharSequence getPageTitle(int position) {
        return mFragmentTitleList.get(position);
    }
}
