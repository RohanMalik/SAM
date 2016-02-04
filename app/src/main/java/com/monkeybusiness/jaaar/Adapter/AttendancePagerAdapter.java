package com.monkeybusiness.jaaar.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentStatePagerAdapter;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.Fragment.ClassHistoryFragment;
import com.monkeybusiness.jaaar.Fragment.RandomFragment;

/**
 * Created by rohanmalik on 30/12/15.
 */
public class AttendancePagerAdapter extends FragmentStatePagerAdapter {
    int mNumOfTabs;

    public AttendancePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {

        switch (position) {
            case 0:
//                AttendanceFragment tab1 = new AttendanceFragment();
//                return tab1;
            case 1:
                ClassHistoryFragment tab2 = new ClassHistoryFragment();
                return tab2;
            case 2:
                RandomFragment tab3 = new RandomFragment();
                return tab3;
            default:
                return null;
        }
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}