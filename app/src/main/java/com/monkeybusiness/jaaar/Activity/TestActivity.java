package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.PagerTabStrip;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.TestPagerAdapter;
import com.monkeybusiness.jaaar.Fragment.TestListFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.TestFragmentListner;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.TestListResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TestActivity extends BaseActivity implements View.OnClickListener {

    final String TAG = "TestActivity";

    ViewPager viewPager;

    RelativeLayout relativeLayoutMenu;

    ArrayList<TestData> testDatas = new ArrayList<>();

    TextView textViewActionTitle;

    PagerTabStrip pager_header;
    private TestPagerAdapter fragmentPagerAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Utils.classFlag = 3;

        toggleLayouts(linearlayoutTest, textViewTest);

        initialization();

    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);

        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        viewPager = (ViewPager) findViewById(R.id.viewPager);

        pager_header = (PagerTabStrip) findViewById(R.id.pager_header);

        pager_header.setBackgroundColor(getResources().getColor(R.color.primary_lighter));

        pager_header.setDrawFullUnderline(false);

        pager_header.setTabIndicatorColor(getResources().getColor(R.color.white));

        pager_header.setTextColor(getResources().getColor(R.color.white));

        textViewActionTitle.setText("TEST");

        viewPager.setOffscreenPageLimit(3);

        relativeLayoutMenu.setOnClickListener(this);
    }

    public void setUiData() {
        fragmentPagerAdapter = new TestPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        TestListResponse testListResponse = Prefs.with(this).getObject(PrefsKeys.TEST_RESPONSE_DATA, TestListResponse.class);

        List<Test> testList = testListResponse.getData().getTests();

        List<Test> testListUpcoming = new ArrayList<>();

        for (Test test : testList) {
            if (test.getTestStatus().equalsIgnoreCase(Constants.UPCOMING)) {
                testListUpcoming.add(test);
            }
        }

        List<Test> testListPending = new ArrayList<>();

        for (Test test : testList) {
            if (test.getTestStatus().equalsIgnoreCase(Constants.PENDING_TEST)) {
                testListPending.add(test);
            }
        }

        List<Test> testListCompleted = new ArrayList<>();

        for (Test test : testList) {
            if (test.getTestStatus().equalsIgnoreCase(Constants.COMPLETED_TEST)) {
                testListCompleted.add(test);
            }
        }

        if (!testListUpcoming.isEmpty())
        {
            TestFragmentListner listner = (TestFragmentListner) fragmentPagerAdapter.getRegisteredFragment(0);
            listner.onResumeFragment(this, testListUpcoming);
        }

        if (!testListPending.isEmpty())
        {
            TestFragmentListner listner = (TestFragmentListner) fragmentPagerAdapter.getRegisteredFragment(1);
            listner.onResumeFragment(this, testListPending);
        }

        if (!testListCompleted.isEmpty())
        {
            TestFragmentListner listner = (TestFragmentListner) fragmentPagerAdapter.getRegisteredFragment(2);
            listner.onResumeFragment(this, testListCompleted);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }

    private void testListServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallTestList(new Callback<TestListResponse>() {
            @Override
            public void success(TestListResponse testListResponse, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(testListResponse));
                Prefs.with(TestActivity.this).save(PrefsKeys.TEST_RESPONSE_DATA, testListResponse);
                setUiData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        testListServerCall();
    }
}