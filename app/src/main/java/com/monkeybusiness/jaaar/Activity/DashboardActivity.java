package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Utils;

/**
 * Created by rakesh on 4/2/16.
 */
public class DashboardActivity extends BaseActivity{

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        Utils.classFlag = 0;

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        textViewActionTitle.setText("DashBoard");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
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
