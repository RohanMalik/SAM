package com.monkeybusiness.jaaar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.Student;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.StudentDetailsForMarksResponse;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.objectClasses.testMarksResponseData.TestMarksResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class FillMarksActivity extends BaseActivity {

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    HollyViewPager hollyViewPager;
    int pageCount;
//    ViewPager viewPagerAttd;

//    AttendanceSlidePagerAdapter adapter;
    private String TAG = "AttendanceFragment";

    ProgressBar progressBarAttd;

    int batchId;

    Test test;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attendance);

        MasterClass.getInstance().setFillMarksActivity(this);

        test = Prefs.with(this).getObject(PrefsKeys.TEST_DATA,Test.class);

        Log.d(TAG,"Test Data : "+test.getTestName()+" "+test.getTestDate());

//        Utils.classFlag = 1;

//        toggleLayouts(linearlayoutAttendance, textViewAttendance);

//        viewPagerAttd = (ViewPager) findViewById(R.id.viewPagerAttd);

        hollyViewPager = (HollyViewPager) findViewById(R.id.hollyViewPager);

        progressBarAttd = (ProgressBar) findViewById(R.id.progressBarAttd);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText(""+test.getTestName());

        getTestMarksServerCall();

//        adapter = new AttendanceSlidePagerAdapter(getSupportFragmentManager(),6);


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

    public void setUIData(StudentDetailsForMarksResponse studentDetailsForMarksResponse) {

        progressBarAttd.setVisibility(View.GONE);
        hollyViewPager.setVisibility(View.VISIBLE);

        List<Student> students = studentDetailsForMarksResponse.getData().getStudents();
        pageCount = students.size();
        Log.d(TAG, "pageCount : " + pageCount);

        Log.d("sort","before studentInfo : "+new Gson().toJson(students));

        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student self, Student other) {
                return String.valueOf(self.getRollno()).compareTo(String.valueOf(other.getRollno()));
            }
        });

        Prefs.with(FillMarksActivity.this).save(PrefsKeys.STUDENT_DATA_TEST,studentDetailsForMarksResponse);

        Log.d("sort","after students : "+new Gson().toJson(students));

        MasterClass.getInstance().setClassAlias(studentDetailsForMarksResponse.getData().getBatch().getClassAlias());
        MasterClass.getInstance().setStudents(students);

        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {

//                Log.d("attendancePager","Height : "+((page+4)%10)/10f);
//                return ((page+4)%10)/10f;
                return 0.5f;
            }

        });

        AttendancePagerAdapterHolly adapterHolly = new AttendancePagerAdapterHolly(getSupportFragmentManager());

        hollyViewPager.setAdapter(adapterHolly);

        hollyViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "pageSelected : " + position);
                if (position == 10) {
                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapterHolly.getRegisteredFragment(position);
                    reviewAttdInterface.onResumeFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.textViewActionTitle:
//                showDateDialog();
                break;
        }
    }

    public class AttendancePagerAdapterHolly extends FragmentPagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public AttendancePagerAdapterHolly(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position != pageCount) {
                return StudentMarksCardFargment.newInstance(position, (String) getPageTitle(position));
            } else {
                return MarksReviewFragment.newInstance(position, (String) getPageTitle(position));
            }
        }

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

        @Override
        public int getCount() {
            return pageCount + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position != pageCount) {
                return "Roll No. " + (position + 1);
            } else {
                return "Submit";
            }
        }

    }

    private void getTestMarksServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallGetTestMarks(String.valueOf(test.getId()), new Callback<TestMarksResponseData>() {
            @Override
            public void success(TestMarksResponseData testMarksResponseData, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(testMarksResponseData));
                Prefs.with(FillMarksActivity.this).save(PrefsKeys.TEST_MARKS_DATA,testMarksResponseData);
                getTestStudentsServerCall();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : "+error.toString());
            }
        });
    }

    private void getTestStudentsServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallGetTestStudents(String.valueOf(test.getLectureId()), new Callback<StudentDetailsForMarksResponse>() {
            @Override
            public void success(StudentDetailsForMarksResponse studentDetailsForMarksResponse, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(studentDetailsForMarksResponse));

                setUIData(studentDetailsForMarksResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : "+error.toString());
            }
        });

    }
}
