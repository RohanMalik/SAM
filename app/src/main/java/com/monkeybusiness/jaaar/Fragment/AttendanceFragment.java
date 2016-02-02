package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.Adapter.AttendanceSlidePagerAdapter;
import com.monkeybusiness.jaaar.Adapter.AttendanceViewPagerAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.utils.DepthPageTransformer;

import butterknife.ButterKnife;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class AttendanceFragment extends Fragment {


    ViewPager viewPagerAttd;

    AttendanceSlidePagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_attendance, container, false);
        ButterKnife.bind(this, rootView);

        MasterClass.getInstance().setAttendanceFragment(this);

        viewPagerAttd = (ViewPager) rootView.findViewById(R.id.viewPagerAttd);

        adapter = new AttendanceSlidePagerAdapter(getActivity().getSupportFragmentManager(),6);

        viewPagerAttd.setPageTransformer(true,new DepthPageTransformer());
        viewPagerAttd.setAdapter(adapter);

        viewPagerAttd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                if (position==5)
                {
                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapter.getItem(position);
                    reviewAttdInterface.onResumeFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });

//        tabLayout.addTab(tabLayout.newTab().setText("Attendance"));
//        tabLayout.addTab(tabLayout.newTab().setText("History"));
//        tabLayout.addTab(tabLayout.newTab().setText("Random"));
//        tabLayout.setTabGravity(TabLayout.GRAVITY_FILL);
//
//
//        final PagerAdapter adapter = new AttendancePagerAdapter
//                (getChildFragmentManager(), tabLayout.getTabCount());
//        pager.setAdapter(adapter);
//        pager.addOnPageChangeListener(new TabLayout.TabLayoutOnPageChangeListener(tabLayout));
//        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
//            @Override
//            public void onTabSelected(TabLayout.Tab tab) {
//                pager.setCurrentItem(tab.getPosition());
//            }
//
//            @Override
//            public void onTabUnselected(TabLayout.Tab tab) {
//
//            }
//
//            @Override
//            public void onTabReselected(TabLayout.Tab tab) {
//
//            }
//        });

//        pager = (ViewPager) rootView.findViewById(R.id.pager);
//        tabLayout = (TabLayout) rootView.findViewById(R.id.tabLayout);
//        adapter = new AttendanceViewPagerAdapter(getChildFragmentManager(), titles, numTabs);
//        pager.setAdapter(adapter);
//        tabLayout.setupWithViewPager(pager);
//     //   pager.setOffscreenPageLimit(3);

        return rootView;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        ButterKnife.unbind(this);
    }

//    static AttendanceFragment instance;
//    public static AttendanceFragment getInstance() {
//        if( instance == null ) {
//            instance = new AttendanceFragment();
//        }
//        return instance;
//    }
}
