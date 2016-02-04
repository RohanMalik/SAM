package com.monkeybusiness.jaaar.Activity;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.Fragment.FriendsActivity;
import com.monkeybusiness.jaaar.Fragment.RemarksFragment;
import com.monkeybusiness.jaaar.Fragment.TestListFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Utils;
import com.slidingmenu.lib.SlidingMenu;
import com.slidingmenu.lib.app.SlidingFragmentActivity;



public class BaseActivity extends SlidingFragmentActivity implements View.OnClickListener {

    LinearLayout linearlayoutDashboard;
    LinearLayout linearlayoutAttendance;
    LinearLayout linearlayoutMyclass;
    LinearLayout linearlayoutTest;
    LinearLayout linearlayoutCalender;
    LinearLayout linearlayoutNotification;
    LinearLayout linearlayoutRemarks;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setBehindContentView(R.layout.activity_base);

        initializationMenu();
    }

    void initializationMenu() {

        linearlayoutDashboard = (LinearLayout) findViewById(R.id.linearlayoutDashboard);
        linearlayoutAttendance = (LinearLayout) findViewById(R.id.linearlayoutAttendance);
        linearlayoutMyclass = (LinearLayout) findViewById(R.id.linearlayoutMyclass);
        linearlayoutTest = (LinearLayout) findViewById(R.id.linearlayoutTest);
        linearlayoutCalender = (LinearLayout) findViewById(R.id.linearlayoutCalender);
        linearlayoutNotification = (LinearLayout) findViewById(R.id.linearlayoutNotification);
        linearlayoutRemarks = (LinearLayout) findViewById(R.id.linearlayoutRemarks);

        linearlayoutDashboard.setOnClickListener(this);
        linearlayoutMyclass.setOnClickListener(this);
        linearlayoutTest.setOnClickListener(this);
        linearlayoutCalender.setOnClickListener(this);
        linearlayoutNotification.setOnClickListener(this);
        linearlayoutAttendance.setOnClickListener(this);
        linearlayoutRemarks.setOnClickListener(this);

        SlidingMenu sm = getSlidingMenu();
        sm.setFadeDegree(0.50f);
        getSlidingMenu().setBehindWidth(800);
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()) {

            case R.id.linearlayoutDashboard:
                if (Utils.classFlag != 0) {

                    Intent intent = new Intent(BaseActivity.this, DashboardActivity.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutAttendance:
                if (Utils.classFlag != 1) {
                    Intent intent = new Intent(BaseActivity.this, AttendanceFragment.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
            case R.id.linearlayoutMyclass:

                if (Utils.classFlag != 2) {
                    Intent intent = new Intent(BaseActivity.this, FriendsActivity.class);
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
                    Intent intent = new Intent(BaseActivity.this, TestListFragment.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
//            case R.id.linearLayoutNotificationMenu:
//
//                updateUI(4);
//
//                if (Utils.classFlag != 4) {
//                    Intent intent = new Intent(BaseActivity.this, NotificationsActivity.class);
//                    startActivity(intent);
//                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
//                } else {
//                    toggle();
//                }
//                break;
//
            case R.id.linearlayoutRemarks:
                if (Utils.classFlag != 5) {
                    Intent intent = new Intent(BaseActivity.this, RemarksFragment.class);
                    startActivity(intent);
                    finish();
//                    overridePendingTransition(R.anim.slide_in_right,
//                            R.anim.slide_out_left);
                } else {
                    toggle();
                }
                break;
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
}
