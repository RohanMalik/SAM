package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.Adapter.AttendanceSlidePagerAdapter;
import com.monkeybusiness.jaaar.Adapter.AttendanceViewPagerAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.utils.DepthPageTransformer;
import com.monkeybusiness.jaaar.utils.Utils;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.ButterKnife;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class AttendanceFragment extends BaseActivity {

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    HollyViewPager hollyViewPager;

    int pageCount = 6;
//    ViewPager viewPagerAttd;

//    AttendanceSlidePagerAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attendance);
        MasterClass.getInstance().setAttendanceFragment(this);

        Utils.classFlag = 1;
        ArrayList<StudentAttdData> studentAttdDatas = new ArrayList<>();

        studentAttdDatas.add(new StudentAttdData("Rohan Malik", "10th", "1", "", Arrays.asList("1", "0", "1", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Jaspreet Arora", "10th", "2", "", Arrays.asList("0", "0", "0", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Ravi Kumar", "10th", "3", "", Arrays.asList("1", "0", "1", "1", "1")));
        studentAttdDatas.add(new StudentAttdData("Salman Khan", "10th", "4", "", Arrays.asList("1", "0", "0", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Shahrukh Khan", "10th", "5", "", Arrays.asList("1", "0", "0", "0", "0")));

        MasterClass.getInstance().setStudentAttdDatas(studentAttdDatas);
//        viewPagerAttd = (ViewPager) findViewById(R.id.viewPagerAttd);

        hollyViewPager = (HollyViewPager) findViewById(R.id.hollyViewPager);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("Attendance");

//        adapter = new AttendanceSlidePagerAdapter(getSupportFragmentManager(),6);

        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {
//                return ((page+4)%10)/10f;
                return 5;
            }

        });

        hollyViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                if (position!=5)
                {
                    return StudentAttendanceCardFargment.newInstance(position,(String) getPageTitle(position));
                }
                else
                {
                    return AttendanceReviewFragment.newInstance(position,(String) getPageTitle(position));
                }
            }

            @Override
            public int getCount() {
                return pageCount;
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return "TITLE 123";
            }
        });

//        viewPagerAttd.setPageTransformer(true, new DepthPageTransformer());
//        viewPagerAttd.setAdapter(adapter);
//
//        viewPagerAttd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 5) {
//                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapter.getItem(position);
//                    reviewAttdInterface.onResumeFragment();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }

}
