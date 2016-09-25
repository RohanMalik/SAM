package com.monkeybusiness.jaaar.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

/**
 * Created by rakesh on 1/1/16.
 */
public class AttendanceSlidePagerAdapter extends FragmentPagerAdapter {


    int mNumOfTabs;

    public AttendanceSlidePagerAdapter(FragmentManager fm, int NumOfTabs) {
        super(fm);
        this.mNumOfTabs = NumOfTabs;
    }

    @Override
    public Fragment getItem(int position) {
//        if (position==5)
//        {
//            return AttendanceReviewFragment.newInstance(position);
//        }
//        else
//        {
//            return StudentAttendanceCardFargment.newInstance(position);
//        }
        return null;
    }

    @Override
    public int getCount() {
        return mNumOfTabs;
    }
}
