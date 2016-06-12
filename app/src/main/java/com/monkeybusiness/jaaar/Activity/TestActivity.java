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
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.TestListResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.ArrayList;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class TestActivity extends BaseActivity implements View.OnClickListener{

    final String TAG = "TestActivity";

    ViewPager viewPager;
    FragmentPagerAdapter fragmentPagerAdapter;

    RelativeLayout relativeLayoutMenu;

    ArrayList<TestData> testDatas = new ArrayList<>();

    TextView textViewActionTitle;

    PagerTabStrip pager_header;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test);

        Utils.classFlag = 3;

        toggleLayouts(linearlayoutTest, textViewTest);

        initialization();

        testListServerCall();

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

        fragmentPagerAdapter = new TestPagerAdapter(getSupportFragmentManager());
        viewPager.setAdapter(fragmentPagerAdapter);

        relativeLayoutMenu.setOnClickListener(this);
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

    private void testListServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallTestList(new Callback<TestListResponse>() {
            @Override
            public void success(TestListResponse testListResponse, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(testListResponse));
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : "+error.toString());
            }
        });
    }

    public void addTestServerCall()
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

//        RestClient.getApiService(xCookies,aCookies).apiCallPostTest();
    }
}
