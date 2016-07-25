package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.MyCalendarFragment;
import com.monkeybusiness.jaaar.Fragment.RemarksFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;

import rmn.androidscreenlibrary.ASSL;


public class BaseActivity extends SlidingFragmentActivity implements View.OnClickListener {

    public LinearLayout linearlayoutDashboard;
    public LinearLayout linearlayoutAttendance;
    public LinearLayout linearlayoutMyclass;
    public LinearLayout linearlayoutTest;
    public LinearLayout linearlayoutCalender;
    public LinearLayout linearlayoutNotification;
    public LinearLayout linearlayoutRemarks;
    public LinearLayout linearlayoutMyclassDown;

    public TextView textViewDashboard;
    public TextView textViewAttendance;
    public TextView textViewMyclass;
    public TextView textViewTest;
    public TextView textViewCalendar;
    public TextView textViewRemarks;
    public TextView textViewMyclassDown;

    ImageView profile_image;
    ImageView littleProfilePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.activity_base);
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        initializationMenu();
    }

    void initializationMenu() {

        profile_image = (ImageView) findViewById(R.id.profile_image);
        littleProfilePic = (ImageView) findViewById(R.id.littleProfilePic);

        linearlayoutDashboard = (LinearLayout) findViewById(R.id.linearlayoutDashboard);
        linearlayoutAttendance = (LinearLayout) findViewById(R.id.linearlayoutAttendance);
        linearlayoutMyclass = (LinearLayout) findViewById(R.id.linearlayoutMyclass);
        linearlayoutTest = (LinearLayout) findViewById(R.id.linearlayoutTest);
        linearlayoutCalender = (LinearLayout) findViewById(R.id.linearlayoutCalender);
        linearlayoutNotification = (LinearLayout) findViewById(R.id.linearlayoutNotification);
        linearlayoutRemarks = (LinearLayout) findViewById(R.id.linearlayoutRemarks);
        linearlayoutMyclassDown = (LinearLayout) findViewById(R.id.linearlayoutMyclassDown);

        textViewDashboard = (TextView) findViewById(R.id.textViewDashboard);
        textViewAttendance = (TextView) findViewById(R.id.textViewAttendance);
        textViewMyclass = (TextView) findViewById(R.id.textViewMyclass);
        textViewTest = (TextView) findViewById(R.id.textViewTest);
        textViewCalendar = (TextView) findViewById(R.id.textViewCalendar);
        textViewRemarks = (TextView) findViewById(R.id.textViewRemarks);
        textViewMyclassDown = (TextView) findViewById(R.id.textViewMyclassDown);

        linearlayoutDashboard.setOnClickListener(this);
        linearlayoutMyclass.setOnClickListener(this);
        linearlayoutTest.setOnClickListener(this);
        linearlayoutCalender.setOnClickListener(this);
        linearlayoutNotification.setOnClickListener(this);
        linearlayoutAttendance.setOnClickListener(this);
        linearlayoutRemarks.setOnClickListener(this);
        linearlayoutMyclassDown.setOnClickListener(this);

        SlidingMenu sm = getSlidingMenu();
        sm.setFadeDegree(0.50f);
        getSlidingMenu().setBehindWidth((int) (550 * ASSL.Xscale()));
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.linearlayoutDashboard:
                if (Utils.classFlag != 0) {

                    Intent intent = new Intent(BaseActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
                    toggleLayouts(linearlayoutDashboard, textViewDashboard);
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutAttendance:
                if (Utils.classFlag != 1) {

//                    Intent intent = new Intent(BaseActivity.this, AttendanceFragment.class);
                    Intent intent = new Intent(BaseActivity.this, MyBatchesActivity.class);
                    startActivity(intent);
                    finish();
                    toggleLayouts(linearlayoutAttendance, textViewAttendance);
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutMyclass:

                if (Utils.classFlag != 2) {

                    toggleLayouts(linearlayoutMyclass, textViewMyclass);
                    Intent intent = new Intent(BaseActivity.this, MyLecturesActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutTest:

                if (Utils.classFlag != 3) {

                    toggleLayouts(linearlayoutTest, textViewTest);
                    Intent intent = new Intent(BaseActivity.this, TestActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;

            case R.id.linearlayoutMyclassDown:
                if (Utils.classFlag != 4) {

                    toggleLayouts(linearlayoutMyclassDown, textViewMyclassDown);
                    Intent intent = new Intent(BaseActivity.this, MyClassActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutCalender:

                if (Utils.classFlag != 5) {

                    toggleLayouts(linearlayoutCalender, textViewCalendar);
                    Intent intent = new Intent(BaseActivity.this, MyCalenderActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
//
            case R.id.linearlayoutRemarks:
                if (Utils.classFlag != 6) {

                    toggleLayouts(linearlayoutRemarks, textViewRemarks);
                    Intent intent = new Intent(BaseActivity.this, RemarksFragment.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutNotification:
                Prefs.with(this).save(PrefsKeys.VERIFIED_USER, Constants.UNVERIFIED);
                Intent intent = new Intent(this, LoginActivity.class);
                startActivity(intent);
                finish();
//            case R.id.linearLayoutContactUsMenu:
//
//                updateUI(6);
//
//                if (Utils.classFlag != 6) {
//                    Intent intent = new Intent(BaseActivity.this, ContactUsActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
//                } else {
//                    toggle();
//                }
//                break;
//            case R.id.linearLayoutLogoutMenu:
//                if (Utils.classFlag != 7) {
//                    finish();
//                } else {
//                    toggle();
//                }
//                break;
//        }
        }
    }


    public void toggleLayouts(LinearLayout linearLayout, TextView textView) {
        linearlayoutDashboard.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutAttendance.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutMyclass.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutTest.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutCalender.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutNotification.setBackgroundColor(getResources().getColor(R.color.white));
        linearlayoutRemarks.setBackgroundColor(getResources().getColor(R.color.white));

        linearLayout.setBackgroundColor(getResources().getColor(R.color.primary));

        textViewDashboard.setTextColor(getResources().getColor(R.color.black));
        textViewAttendance.setTextColor(getResources().getColor(R.color.black));
        textViewMyclass.setTextColor(getResources().getColor(R.color.black));
        textViewTest.setTextColor(getResources().getColor(R.color.black));
        textViewCalendar.setTextColor(getResources().getColor(R.color.black));
        textViewRemarks.setTextColor(getResources().getColor(R.color.black));

        textView.setTextColor(getResources().getColor(R.color.white));
    }
}
