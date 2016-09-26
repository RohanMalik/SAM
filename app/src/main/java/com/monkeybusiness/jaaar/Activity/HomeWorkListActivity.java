package com.monkeybusiness.jaaar.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.HomeWorkAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.HomeWorkResponseData;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.Hw;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class HomeWorkListActivity extends AppCompatActivity {

    private static final String TAG = "HomeWorkList";
    private static final String CW = "cw";
    private static final String HW = "hw";

    Spinner spinnerHomeWork;
    Spinner spinnerLectures;
    ListView listViewAnnouncements;
    LinearLayout linearLayoutList;
    ProgressBar progressBarAnnouncements;

    RelativeLayout relativeLayoutNodataFound;
    String type;
    List<Integer> lectureIdslist = new ArrayList<Integer>();


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_list);

        initializtion();
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (type.equalsIgnoreCase(CW)) {
            getClassWorkListServerCall(CW, null, false);
        } else {
            getClassWorkListServerCall(HW, null, false);
        }

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lectureServerCall();
            }
        }, 200);
    }

    private void initializtion() {

        spinnerHomeWork = (Spinner) findViewById(R.id.spinnerHomeWork);
        listViewAnnouncements = (ListView) findViewById(R.id.listViewAnnouncements);
        progressBarAnnouncements = (ProgressBar) findViewById(R.id.progressBarAnnouncements);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        linearLayoutList = (LinearLayout) findViewById(R.id.linearLayoutList);

        spinnerLectures = (Spinner) findViewById(R.id.spinnerLectures);

        progressBarAnnouncements.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);
    }

    private void setUiData(List<Hw> hws) {
        if (!hws.isEmpty()) {
            progressBarAnnouncements.setVisibility(View.GONE);
            linearLayoutList.setVisibility(View.VISIBLE);
            relativeLayoutNodataFound.setVisibility(View.GONE);
            HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(this, hws);
            listViewAnnouncements.setAdapter(homeWorkAdapter);
        } else {
            relativeLayoutNodataFound.setVisibility(View.VISIBLE);
            progressBarAnnouncements.setVisibility(View.GONE);
            linearLayoutList.setVisibility(View.GONE);
        }
    }

    private void getClassWorkListServerCall(String type, String lectureId, boolean showLoading) {

        if (showLoading) {
            LoadingBox.showLoadingDialog(this, "Loading Assignments....");
        }

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        Date fromDate = new Date();
        fromDate.setDate(fromDate.getDate() - 7);

        Date toDate = new Date();
        toDate.setDate(toDate.getDate() + 7);

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetCWHW(
                Utils.simpleDateFormatter(fromDate),
                Utils.simpleDateFormatter(toDate),
                type,
                lectureId,
                new Callback<HomeWorkResponseData>() {
                    @Override
                    public void success(HomeWorkResponseData homeWorkResponseData, Response response) {
                        Log.d(TAG, "Response : " + new Gson().toJson(homeWorkResponseData));

                        setUiData(homeWorkResponseData.getData().getHws());
                        if (showLoading) {
                            LoadingBox.dismissLoadingDialog();
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                    }
                });
    }

    public void getListHWS(String type) {
        if (spinnerLectures.getSelectedItemPosition() == 0) {
            getClassWorkListServerCall(type, null, true);
        } else {
            getClassWorkListServerCall(type, String.valueOf(lectureIdslist.get(spinnerLectures.getSelectedItemPosition() - 1)), true);
        }
    }

    public void lectureServerCall() {

        LoadingBox.showLoadingDialog(this, "Loading Assignments....");
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "Response : " + new Gson().toJson(lectureResponseData));
                Prefs.with(HomeWorkListActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA, lectureResponseData);

                List<String> lecturelist = new ArrayList<String>();
                lecturelist.add("All Lectures");
                if (lectureResponseData.getData().getLectures() != null) {
                    for (Lecture lecture : lectureResponseData.getData().getLectures()) {
                        lecturelist.add(lecture.getLectureName());
                        lectureIdslist.add(lecture.getId());
                    }

                    Log.d(TAG, "LectureIds : " + new Gson().toJson(lecturelist));

                    ArrayAdapter<String> lectureAdapter = new ArrayAdapter<String>(HomeWorkListActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, lecturelist);
                    spinnerLectures.setAdapter(lectureAdapter);

                    spinnerLectures.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(Spinner parent, View view, int position, long id) {

                            String type;
                            if (spinnerHomeWork.getSelectedItemPosition() == 0) {
                                type = CW;
                            } else {
                                type = HW;
                            }

                            String lectureId;
                            if (spinnerLectures.getSelectedItemPosition() == 0) {
                                lectureId = null;
                            } else {
                                lectureId = String.valueOf(lectureIdslist.get(spinnerLectures.getSelectedItemPosition() - 1));
                            }
                            getClassWorkListServerCall(type, lectureId, true);
                        }
                    });

                    List<String> typeList = new ArrayList<>();
                    typeList.add("Class Work");
                    typeList.add("Home Work");
                    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(HomeWorkListActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, typeList);
                    spinnerHomeWork.setAdapter(categoryAdapter);

                    if (type.equalsIgnoreCase(CW)) {
                        spinnerHomeWork.setSelection(0);
                    } else {
                        spinnerHomeWork.setSelection(1);
                    }

                    spinnerHomeWork.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(Spinner parent, View view, int position, long id) {
//                                String lectureId;

//                                    lectureId = String.valueOf(lectureIdslist.get(spinnerLectures.getSelectedItemPosition()-1));

                            if (position == 0) {
                                getListHWS(CW);
                            } else {
                                getListHWS(HW);
                            }
                        }
                    });
                }

                if (LoadingBox.isDialogShowing()) {
                    LoadingBox.dismissLoadingDialog();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "error : ");
            }
        });
    }

}
