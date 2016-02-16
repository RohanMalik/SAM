package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Utils;
import com.rey.material.widget.Button;

/**
 * Created by rakesh on 4/2/16.
 */
public class DashboardActivity extends BaseActivity{

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    Button buttonTakeAttd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        Utils.classFlag = 0;

        toggleLayouts(linearlayoutDashboard,textViewDashboard);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        buttonTakeAttd = (Button) findViewById(R.id.buttonTakeAttd);

        textViewActionTitle.setText("DashBoard");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        buttonTakeAttd.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.buttonTakeAttd:
                Intent intent = new Intent(DashboardActivity.this, AttendanceFragment.class);
                startActivity(intent);
                finish();
                break;
        }
    }
}
