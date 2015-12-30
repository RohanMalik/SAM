package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.Adapter.AttendanceViewPagerAdapter;
import com.monkeybusiness.jaaar.R;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class AttendanceFragment extends Fragment {
    ViewPager pager;
    TabLayout tabLayout;
    AttendanceViewPagerAdapter adapter;
    CharSequence titles[] = {"History", "Attendance", "Random"};
    int numTabs = 3;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);

        pager = (ViewPager) rootView.findViewById(R.id.pager);
        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
        adapter = new AttendanceViewPagerAdapter(getFragmentManager(), titles, numTabs);
        pager.setAdapter(adapter);
        tabLayout.setupWithViewPager(pager);
     //   pager.setOffscreenPageLimit(3);
        return rootView;
    }

    static AttendanceFragment instance;
    public static AttendanceFragment getInstance() {
        if( instance == null ) {
            instance = new AttendanceFragment();
        }
        return instance;
    }
}
