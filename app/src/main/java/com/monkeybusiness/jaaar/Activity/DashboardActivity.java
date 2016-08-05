package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.EventsListAdapter;
import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.addEventResponse.AddEventResponseData;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.objectClasses.eventResponse.EventResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 4/2/16.
 */
public class DashboardActivity extends BaseActivity {

    final String TAG = "DashboardActivity";

    LinearLayout linearLayoutMainDash;
//
    RelativeLayout relativeLayoutMenu;

//    TextView textViewActionTitle;
    TextView textViewName;
//    TextView textViewClass;
//    TextView textViewContact;
//    TextView textViewEmailStudent;

    ImageView imageViewProfilePic;

//    Button buttonTakeAttd;

//    ProgressBar progressBarDash;
    FrameLayout frameLayoutAttd;
    RelativeLayout relativeLayoutAdd;

    RelativeLayout relativeLayoutNodataFound;

    ListView listViewEvents;
    private EventsListAdapter eventsListAdapter;

    ProgressBar progressBarEvents;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        Utils.classFlag = 0;

        toggleLayouts(linearlayoutDashboard, textViewDashboard);

        initialization();
        dashBoardServerCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getEventsServerCallDay("day");
            }
        },200);
    }

    public void initialization() {
        linearLayoutMainDash = (LinearLayout) findViewById(R.id.root);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);


//        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewName = (TextView) findViewById(R.id.textViewName);
//        textViewClass = (TextView) findViewById(R.id.textViewClass);
//        textViewContact = (TextView) findViewById(R.id.textViewContact);
//        textViewEmailStudent = (TextView) findViewById(R.id.textViewEmailStudent);

        relativeLayoutAdd = (RelativeLayout) findViewById(R.id.relativeLayoutAdd);

        imageViewProfilePic = (ImageView) findViewById(R.id.imageViewProfilePic);

        progressBarEvents = (ProgressBar) findViewById(R.id.progressBarEvents);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        progressBarEvents.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);

//        buttonTakeAttd = (Button) findViewById(R.id.buttonTakeAttd);
//        frameLayoutAttd = (FrameLayout) findViewById(R.id.frameLayoutAttd);


//        progressBarDash = (ProgressBar) findViewById(R.id.progressBarDash);

//        textViewActionTitle.setText("DashBoard");

        linearLayoutMainDash.setVisibility(View.GONE);

        eventsListAdapter = new EventsListAdapter(this);
        listViewEvents.setAdapter(eventsListAdapter);

        relativeLayoutMenu.setOnClickListener(this);
//        textViewActionTitle.setOnClickListener(this);
//        buttonTakeAttd.setOnClickListener(this);
        relativeLayoutAdd.setOnClickListener(this);
    }

    private void setUIData() {

        CheckLoginResponse checkLoginResponse = Prefs.with(this).getObject(PrefsKeys.CHECK_LOGIN_DATA,CheckLoginResponse.class);

//        progressBarDash.setVisibility(View.GONE);
        linearLayoutMainDash.setVisibility(View.VISIBLE);

        if (checkLoginResponse!=null)
        {
            textViewName.setText("Hi!  "+checkLoginResponse.getData().getUserTypeDetails().getTeacherName());
//            textViewClass.setVisibility(View.GONE);
//            textViewContact.setText("Contact No : "+checkLoginResponse.getData().getUserTypeDetails().getContactInfo());
//            textViewEmailStudent.setText("Email : "+checkLoginResponse.getData().getUserInfo().getEmail());

            if (checkLoginResponse.getData().getUserTypeDetails().getPicture()!=null){

//                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(imageViewProfilePic);
//                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(profile_image);
//                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(littleProfilePic);
            }
        }

    }

    public void setUiDataForListView(EventResponseData eventResponseData) {

        progressBarEvents.setVisibility(View.GONE);
        if (eventResponseData.getData().getEvents() != null) {
            if (!eventResponseData.getData().getEvents().isEmpty()) {
                listViewEvents.setVisibility(View.VISIBLE);
                relativeLayoutNodataFound.setVisibility(View.GONE);
                eventsListAdapter.setData(eventResponseData.getData().getEvents());
            } else {
                listViewEvents.setVisibility(View.GONE);
                relativeLayoutNodataFound.setVisibility(View.VISIBLE);
            }
        } else {
            listViewEvents.setVisibility(View.GONE);
            relativeLayoutNodataFound.setVisibility(View.VISIBLE);
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.relativeLayoutAdd:
                Intent intent = new Intent(DashboardActivity.this, MyBatchesActivity.class);
                startActivity(intent);
//                finish();
                break;
        }
    }

    private void dashBoardServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        LoadingBox.showLoadingDialog(this,"Loading Data...");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallCheckLogin(new Callback<CheckLoginResponse>() {
            @Override
            public void success(CheckLoginResponse checkLoginResponse, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(checkLoginResponse));

                if (LoadingBox.isDialogShowing()){
                    LoadingBox.dismissLoadingDialog();
                }
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
                if (LoadingBox.isDialogShowing()){
                    LoadingBox.dismissLoadingDialog();
                }
                Log.d(TAG,"Response-err : "+error.toString());
            }
        });
    }

    public void getEventsServerCallDay(String type) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String fromTime = "";
        String toTime = "";

        if (type.equalsIgnoreCase("day")) {

            Calendar start = Calendar.getInstance();
            Date pickedDate = new Date();
            start.set(pickedDate.getYear()+1900, pickedDate.getMonth(), pickedDate.getDate(), 0, 0);

            Calendar end = Calendar.getInstance();
            end.set(pickedDate.getYear()+1900, pickedDate.getMonth(), pickedDate.getDate(), 23, 59);

            fromTime = ISO8601.fromCalendar(start);
            toTime = ISO8601.fromCalendar(end);

        }

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetEvents(fromTime, toTime, new Callback<EventResponseData>() {
            @Override
            public void success(EventResponseData eventResponseData, Response response) {
                android.util.Log.d(TAG, "Response : " + new Gson().toJson(eventResponseData));

                Prefs.with(DashboardActivity.this).save(PrefsKeys.EVENT_RESPONSE_DATA, eventResponseData);
                setUiDataForListView(eventResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        Utils.classFlag = 0;
    }
}
