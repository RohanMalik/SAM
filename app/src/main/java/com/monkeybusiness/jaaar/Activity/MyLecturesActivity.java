package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.LecturesAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MyLecturesActivity extends BaseActivity {

    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;
    LecturesAdapter lecturesAdapter;
    private final String TAG = "MyLecture";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lectures);

        toggleLayouts(linearlayoutMyclass, textViewMyclass);
        Utils.classFlag = 2;
        
        initialization();
        lectureServerCall();
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("My Lectures");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);

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

    private void setUIData(LectureResponseData lectureResponseData) {

        if (!lectureResponseData.getData().getLectures().isEmpty())
        {
            lecturesAdapter = new LecturesAdapter(this,lectureResponseData);
            listViewLectures.setAdapter(lecturesAdapter);
        }
    }

    public void lectureServerCall()
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                Log.d(TAG,"Response : "+new Gson().toJson(lectureResponseData));
                Prefs.with(MyLecturesActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA,lectureResponseData);
                setUIData(lectureResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : ");
            }
        });
    }

}
