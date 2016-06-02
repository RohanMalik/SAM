package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rakesh on 4/2/16.
 */
public class DashboardActivity extends BaseActivity {

    final String TAG = "DashboardActivity";

    LinearLayout linearLayoutMainDash;

    RelativeLayout relativeLayoutMenu;

    TextView textViewActionTitle;
    TextView textViewName;
    TextView textViewClass;
    TextView textViewContact;
    TextView textViewEmail;

    Button buttonTakeAttd;

    ProgressBar progressBarDash;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_dashboard);
        Utils.classFlag = 0;

        toggleLayouts(linearlayoutDashboard, textViewDashboard);

        initialization();
        dashBoardServerCall();
    }

    public void initialization() {
        linearLayoutMainDash = (LinearLayout) findViewById(R.id.linearLayoutMainDash);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);

        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewName = (TextView) findViewById(R.id.textViewName);
        textViewClass = (TextView) findViewById(R.id.textViewClass);
        textViewContact = (TextView) findViewById(R.id.textViewContact);
        textViewEmail = (TextView) findViewById(R.id.textViewEmail);

        buttonTakeAttd = (Button) findViewById(R.id.buttonTakeAttd);

        progressBarDash = (ProgressBar) findViewById(R.id.progressBarDash);

        textViewActionTitle.setText("DashBoard");

        linearLayoutMainDash.setVisibility(View.GONE);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        buttonTakeAttd.setOnClickListener(this);
    }

    private void setUIData() {

        CheckLoginResponse checkLoginResponse = Prefs.with(this).getObject(PrefsKeys.CHECK_LOGIN_DATA,CheckLoginResponse.class);

        progressBarDash.setVisibility(View.GONE);
        linearLayoutMainDash.setVisibility(View.VISIBLE);

        if (checkLoginResponse!=null)
        {
            textViewName.setText("Hi "+checkLoginResponse.getData().getUserTypeDetails().getTeacherName());
//            textViewClass.setText(checkLoginResponse.getData().get);
            textViewContact.setText("Contact No : "+checkLoginResponse.getData().getUserTypeDetails().getContactInfo());
            textViewEmail.setText("Email : "+checkLoginResponse.getData().getUserInfo().getEmail());
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
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

    private void dashBoardServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallCheckLogin(new Callback<CheckLoginResponse>() {
            @Override
            public void success(CheckLoginResponse checkLoginResponse, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(checkLoginResponse));

                if (checkLoginResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes"))
                {
                    Prefs.with(DashboardActivity.this).save(PrefsKeys.CHECK_LOGIN_DATA,checkLoginResponse);
                    setUIData();
                }
                else
                {
                    Intent intent = new Intent(DashboardActivity.this,LoginActivity.class);
                    startActivity(intent);
                    Prefs.with(DashboardActivity.this).save(PrefsKeys.VERIFIED_USER, Constants.UNVERIFIED);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"Response-err : "+error.toString());
            }
        });
    }

}
