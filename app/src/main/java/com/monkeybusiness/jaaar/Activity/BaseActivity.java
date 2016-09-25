package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.RemarksFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;
import com.squareup.picasso.Picasso;

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
    public LinearLayout linearlayoutMyclassExams;


    public TextView textViewDashboard;
    public TextView textViewAttendance;
    public TextView textViewMyclass;
    public TextView textViewTest;
    public TextView textViewCalendar;
    public TextView textViewRemarks;
    public TextView textViewMyclassDown;
    public TextView textViewMyclassExams;
    public TextView textViewNotification;
    public TextView username;


    ImageView profile_image;
    ImageView littleProfilePic;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.activity_base);
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        initializationMenu();
        setFont();

        CheckLoginResponse checkLoginResponse = Prefs.with(this).getObject(PrefsKeys.CHECK_LOGIN_DATA, CheckLoginResponse.class);
        if (checkLoginResponse != null) {
            if (checkLoginResponse.getData().getUserTypeDetails().getPicture() != null) {
                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(profile_image);
                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(littleProfilePic);
            }
        }
    }

    private void setFont() {

        textViewDashboard.setTypeface(FontClass.proximaBold(this));
        textViewAttendance.setTypeface(FontClass.proximaBold(this));
        textViewMyclass.setTypeface(FontClass.proximaBold(this));
        textViewTest.setTypeface(FontClass.proximaBold(this));
        textViewCalendar.setTypeface(FontClass.proximaBold(this));
        textViewRemarks.setTypeface(FontClass.proximaBold(this));
        textViewMyclassDown.setTypeface(FontClass.proximaBold(this));
        textViewMyclassExams.setTypeface(FontClass.proximaBold(this));
        textViewNotification.setTypeface(FontClass.proximaBold(this));
        username.setTypeface(FontClass.proximaBold(this));
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
        linearlayoutMyclassExams = (LinearLayout) findViewById(R.id.linearlayoutMyclassExams);

        textViewDashboard = (TextView) findViewById(R.id.textViewDashboard);
        textViewAttendance = (TextView) findViewById(R.id.textViewAttendance);
        textViewMyclass = (TextView) findViewById(R.id.textViewMyclass);
        textViewTest = (TextView) findViewById(R.id.textViewTest);
        textViewCalendar = (TextView) findViewById(R.id.textViewCalendar);
        textViewRemarks = (TextView) findViewById(R.id.textViewRemarks);
        textViewMyclassDown = (TextView) findViewById(R.id.textViewMyclassDown);
        textViewMyclassExams = (TextView) findViewById(R.id.textViewMyclassExams);
        textViewNotification = (TextView) findViewById(R.id.textViewNotification);
        username = (TextView) findViewById(R.id.username);

        linearlayoutDashboard.setOnClickListener(this);
        linearlayoutMyclass.setOnClickListener(this);
        linearlayoutTest.setOnClickListener(this);
        linearlayoutCalender.setOnClickListener(this);
        linearlayoutNotification.setOnClickListener(this);
        linearlayoutAttendance.setOnClickListener(this);
        linearlayoutRemarks.setOnClickListener(this);
        linearlayoutMyclassDown.setOnClickListener(this);
        linearlayoutMyclassExams.setOnClickListener(this);

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
            case R.id.linearlayoutMyclassExams:
                if (Utils.classFlag != 5) {

                    toggleLayouts(linearlayoutMyclassExams, textViewMyclassExams);
                    Intent intent = new Intent(BaseActivity.this, MyClassExamsActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutCalender:

                if (Utils.classFlag != 6) {

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
                if (Utils.classFlag != 7) {

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
                finishAffinity();
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
        linearlayoutMyclassExams.setBackgroundColor(getResources().getColor(R.color.white));

        linearLayout.setBackgroundColor(getResources().getColor(R.color.primary));

        textViewDashboard.setTextColor(getResources().getColor(R.color.black));
        textViewAttendance.setTextColor(getResources().getColor(R.color.black));
        textViewMyclass.setTextColor(getResources().getColor(R.color.black));
        textViewTest.setTextColor(getResources().getColor(R.color.black));
        textViewCalendar.setTextColor(getResources().getColor(R.color.black));
        textViewRemarks.setTextColor(getResources().getColor(R.color.black));
        textViewMyclassExams.setTextColor(getResources().getColor(R.color.black));

        textView.setTextColor(getResources().getColor(R.color.white));
    }
}
