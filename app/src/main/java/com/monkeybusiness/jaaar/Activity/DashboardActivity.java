package com.monkeybusiness.jaaar.Activity;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AccelerateDecelerateInterpolator;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.EventsListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.objectClasses.eventResponse.EventResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.squareup.picasso.Picasso;

import java.util.Calendar;
import java.util.Date;

import io.codetail.animation.SupportAnimator;
import io.codetail.animation.ViewAnimationUtils;
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
    RelativeLayout relativeLayoutLeftMenu;
    ImageView imageViewProfilePic;
    TextView textViewName;

//    TextView textViewActionTitle;

//    TextView textViewClass;
//    TextView textViewContact;
//    TextView textViewEmailStudent;
    TextView textViewGreetings;
    TextView textViewAttdText;
    TextView textViewAttdTitle;
    TextView textViewEventsText;
    //    ProgressBar progressBarDash;
    FrameLayout frameLayoutAttd;
    RelativeLayout relativeLayoutAdd;

//    Button buttonTakeAttd;
    RelativeLayout relativeLayoutNodataFound;
    ListView listViewEvents;
    ProgressBar progressBarEvents;
    LinearLayout btnAnnounce;
    LinearLayout btnRemarks;
    private LinearLayout attachmentLayout;
    private boolean isHidden = true;
    private EventsListAdapter eventsListAdapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        Utils.classFlag = 0;

        toggleLayouts(linearlayoutDashboard, textViewDashboard);

        initialization();
        setFont();
        dashBoardServerCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                getEventsServerCallDay("day");
            }
        }, 200);
    }

    public void initialization() {
        linearLayoutMainDash = (LinearLayout) findViewById(R.id.root);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        listViewEvents = (ListView) findViewById(R.id.listViewEvents);
        relativeLayoutLeftMenu = (RelativeLayout) findViewById(R.id.relativeLayoutLeftMenu);

//        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewName = (TextView) findViewById(R.id.textViewName);
        attachmentLayout = (LinearLayout) findViewById(R.id.menu_attachments);
//        textViewClass = (TextView) findViewById(R.id.textViewClass);
//        textViewContact = (TextView) findViewById(R.id.textViewContact);
//        textViewEmailStudent = (TextView) findViewById(R.id.textViewEmailStudent);
        textViewGreetings = (TextView) findViewById(R.id.textViewGreetings);
        textViewAttdText = (TextView) findViewById(R.id.textViewAttdText);
        textViewAttdTitle = (TextView) findViewById(R.id.textViewAttdTitle);
        textViewEventsText = (TextView) findViewById(R.id.textViewEventsText);

        relativeLayoutAdd = (RelativeLayout) findViewById(R.id.relativeLayoutAdd);

        imageViewProfilePic = (ImageView) findViewById(R.id.imageViewProfilePic);

        progressBarEvents = (ProgressBar) findViewById(R.id.progressBarEvents);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        progressBarEvents.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);

        btnAnnounce = (LinearLayout) findViewById(R.id.btnAnnounce);
        btnRemarks = (LinearLayout) findViewById(R.id.btnRemarks);

//        buttonTakeAttd = (Button) findViewById(R.id.buttonTakeAttd);
//        frameLayoutAttd = (FrameLayout) findViewById(R.id.frameLayoutAttd);


//        progressBarDash = (ProgressBar) findViewById(R.id.progressBarDash);

//        textViewActionTitle.setText("DashBoard");

        linearLayoutMainDash.setVisibility(View.GONE);

        eventsListAdapter = new EventsListAdapter(this);
        listViewEvents.setAdapter(eventsListAdapter);

        relativeLayoutMenu.setOnClickListener(this);
        relativeLayoutLeftMenu.setOnClickListener(this);
        btnAnnounce.setOnClickListener(this);
        btnRemarks.setOnClickListener(this);

//        textViewActionTitle.setOnClickListener(this);
//        buttonTakeAttd.setOnClickListener(this);
        relativeLayoutAdd.setOnClickListener(this);
    }

    public void setFont() {
        textViewName.setTypeface(FontClass.proximaRegular(this));
        textViewGreetings.setTypeface(FontClass.proximaBold(this));
        textViewAttdText.setTypeface(FontClass.proximaRegular(this));
        textViewAttdTitle.setTypeface(FontClass.proximaRegular(this));
        textViewEventsText.setTypeface(FontClass.proximaRegular(this));
    }

    private void setUIData() {

        CheckLoginResponse checkLoginResponse = Prefs.with(this).getObject(PrefsKeys.CHECK_LOGIN_DATA, CheckLoginResponse.class);

//        progressBarDash.setVisibility(View.GONE);
        linearLayoutMainDash.setVisibility(View.VISIBLE);

        if (checkLoginResponse != null) {
            textViewName.setText("Hi!  " + checkLoginResponse.getData().getUserTypeDetails().getTeacherName());
//            textViewClass.setVisibility(View.GONE);
//            textViewContact.setText("Contact No : "+checkLoginResponse.getData().getUserTypeDetails().getContactInfo());
//            textViewEmailStudent.setText("Email : "+checkLoginResponse.getData().getUserInfo().getEmail());

            if (checkLoginResponse.getData().getUserTypeDetails().getPicture() != null) {

                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(imageViewProfilePic);
                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(profile_image);
                Picasso.with(this).load(checkLoginResponse.getData().getUserTypeDetails().getPicture().getUrl()).into(littleProfilePic);
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
        Log.d(TAG, "Clicked menu");
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
            case R.id.relativeLayoutLeftMenu:
                if (isHidden) {
                    if (Build.VERSION.SDK_INT < Build.VERSION_CODES.LOLLIPOP)
                        showMenuBelowLollipop();
                    else
                        showMenu();
                } else {
                    hideMenu();
                }
                break;
            case R.id.btnAnnounce:
                Intent annIntent = new Intent(this, AnnouncementsActivity.class);
                startActivity(annIntent);
                hideMenu();
                break;
            case R.id.btnRemarks:
                break;
        }
    }

    private void dashBoardServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        LoadingBox.showLoadingDialog(this, "Loading Data...");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallCheckLogin(new Callback<CheckLoginResponse>() {
            @Override
            public void success(CheckLoginResponse checkLoginResponse, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(checkLoginResponse));

                if (LoadingBox.isDialogShowing()) {
                    LoadingBox.dismissLoadingDialog();
                }
                if (checkLoginResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Prefs.with(DashboardActivity.this).save(PrefsKeys.CHECK_LOGIN_DATA, checkLoginResponse);
                    setUIData();
                } else {
                    Intent intent = new Intent(DashboardActivity.this, LoginActivity.class);
                    startActivity(intent);
                    Prefs.with(DashboardActivity.this).save(PrefsKeys.VERIFIED_USER, Constants.UNVERIFIED);
                    finish();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                if (LoadingBox.isDialogShowing()) {
                    LoadingBox.dismissLoadingDialog();
                }
                Log.d(TAG, "Response-err : " + error.toString());
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
            start.set(pickedDate.getYear() + 1900, pickedDate.getMonth(), pickedDate.getDate(), 0, 0);

            Calendar end = Calendar.getInstance();
            end.set(pickedDate.getYear() + 1900, pickedDate.getMonth(), pickedDate.getDate(), 23, 59);

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

    void showMenuBelowLollipop() {
        int cx = (attachmentLayout.getLeft() + attachmentLayout.getRight());
        int cy = attachmentLayout.getTop();
        int radius = Math.max(attachmentLayout.getWidth(), attachmentLayout.getHeight());

        try {
            SupportAnimator animator = ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, 0, radius);
            animator.setInterpolator(new AccelerateDecelerateInterpolator());
            animator.setDuration(300);

            if (isHidden) {
                //Log.e(getClass().getSimpleName(), "showMenuBelowLollipop");
                attachmentLayout.setVisibility(View.VISIBLE);
                animator.start();
                isHidden = false;
            } else {
                SupportAnimator animatorReverse = animator.reverse();
                animatorReverse.start();
                animatorReverse.addListener(new SupportAnimator.AnimatorListener() {
                    @Override
                    public void onAnimationStart() {
                    }

                    @Override
                    public void onAnimationEnd() {
                        //Log.e("MainActivity", "onAnimationEnd");
                        isHidden = true;
                        attachmentLayout.setVisibility(View.INVISIBLE);
                    }

                    @Override
                    public void onAnimationCancel() {
                    }

                    @Override
                    public void onAnimationRepeat() {
                    }
                });
            }
        } catch (Exception e) {
            //Log.e(getClass().getSimpleName(), "try catch");
            isHidden = true;
            attachmentLayout.setVisibility(View.INVISIBLE);
        }
    }

    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    void showMenu() {
        int cx = (attachmentLayout.getLeft() + attachmentLayout.getRight());
        int cy = attachmentLayout.getTop();
        int radius = Math.max(attachmentLayout.getWidth(), attachmentLayout.getHeight());

        if (isHidden) {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, 0, radius);
            attachmentLayout.setVisibility(View.VISIBLE);
            anim.start();
            isHidden = false;
        } else {
            Animator anim = android.view.ViewAnimationUtils.createCircularReveal(attachmentLayout, cx, cy, radius, 0);
            anim.addListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    super.onAnimationEnd(animation);
                    attachmentLayout.setVisibility(View.INVISIBLE);
                    isHidden = true;
                }
            });
            anim.start();
        }
    }

    private void hideMenu() {
        attachmentLayout.setVisibility(View.GONE);
        isHidden = true;
    }
}
