package com.monkeybusiness.jaaar.Adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.SparseArray;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.Fragment.PendingTestFragment;
import com.monkeybusiness.jaaar.Fragment.TestTakenFragment;
import com.monkeybusiness.jaaar.Fragment.UpcomingTest;

/**
 * Created by rakesh on 2/6/16.
 */
public class TestPagerAdapter extends FragmentPagerAdapter {

    private static int NUM_ITEMS = 3;

    public TestPagerAdapter(FragmentManager fm) {
        super(fm);
    }

    @Override
    public Fragment getItem(int position) {

        switch (position)
        {
            case 0:
                return UpcomingTest.newInstance(0, "Upcoming Test");
            case 1:
                return PendingTestFragment.newInstance(1, "Pending Test");
            case 2:
                return TestTakenFragment.newInstance(2, "Completed");
        }
        return null;
    }

    @Override
    public int getCount() {
        return NUM_ITEMS;
    }

    @Override
    public CharSequence getPageTitle(int position) {
        switch (position)
        {
            case 0:
                return  "Upcoming";
            case 1:
                return "Pending";
            case 2:
                return "Completed";
        }
        return "";
    }

    SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

    @Override
    public Object instantiateItem(ViewGroup container, int position) {
        Fragment fragment = (Fragment) super.instantiateItem(container, position);
        registeredFragments.put(position, fragment);
        return fragment;
    }

    @Override
    public void destroyItem(ViewGroup container, int position, Object object) {
        registeredFragments.remove(position);
        super.destroyItem(container, position, object);
    }

    public Fragment getRegisteredFragment(int position) {
        return registeredFragments.get(position);
    }

}
