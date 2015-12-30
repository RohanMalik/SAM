package com.monkeybusiness.jaaar.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.Fragment.ClassHistoryFragment;
import com.monkeybusiness.jaaar.Fragment.RandomFragment;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class AttendanceViewPagerAdapter extends BaseViewPagerFragmentStatAdapter {

    CharSequence Titles[]; // This will Store the Titles of the Tabs which are Going to be passed when ViewPagerAdapter is created
    int NumbOfTabs;

    public AttendanceViewPagerAdapter(FragmentManager fm, CharSequence mTitles[], int mNumOfTabs) {
        super(fm);
        this.Titles = mTitles;
        this.NumbOfTabs = mNumOfTabs;
    }


    @Override
    public Fragment getFragmentItem(int position) {
        switch (position) {
            case 0:
             //   AttendanceFragment attendanceFragment = new AttendanceFragment();
                AttendanceFragment attendanceFragment = AttendanceFragment.getInstance();

                return attendanceFragment;
            case 1:
            //    ClassHistoryFragment classHistoryFragment = new ClassHistoryFragment();
                ClassHistoryFragment classHistoryFragment =ClassHistoryFragment.getInstance();
                return classHistoryFragment;
            case 2:
              //  RandomFragment randomFragment = new RandomFragment();
                RandomFragment randomFragment = RandomFragment.getInstance();
                return randomFragment;
        }
        return null;
    }

    @Override
    public void updateFragmentItem(int position, Fragment fragment) {

    }

    @Override
    public CharSequence getPageTitle(int position) {
        return Titles[position];
    }


    @Override
    public int getCount() {
        return NumbOfTabs;
    }
}
