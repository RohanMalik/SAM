package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.ExamGroupAdapter;
import com.monkeybusiness.jaaar.Adapter.ExamsAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.ExamGroupData.ExamGroupData;
import com.monkeybusiness.jaaar.retrofit.CommonApiCalls;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class ExamGroupActivity extends BaseActivity {

    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;
    ExamGroupAdapter examGroupAdapter;
    private final String TAG = "MyClassExam";

    ProgressBar progressBarLectures;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lectures);


        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Utils.classFlag = 5;

        toggleLayouts(linearlayoutMyclassExams, textViewMyclassExams);


        initialization();

        new CommonApiCalls(this).checkLoginServerCall();

        getExamGroupServerCall();
        textViewActionTitle.setTypeface(FontClass.proximaRegular(this));

    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("Exams Group");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);

        progressBarLectures = (ProgressBar) findViewById(R.id.progressBarLectures);

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

    private void setUIData(ExamGroupData examGroupData) {

        if (!examGroupData.getData().getExamGroups().isEmpty())
        {
            progressBarLectures.setVisibility(View.GONE);
            listViewLectures.setVisibility(View.VISIBLE);
            examGroupAdapter = new ExamGroupAdapter(this,examGroupData);
            listViewLectures.setAdapter(examGroupAdapter);
        }
    }


    public void getExamGroupServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetExamGroupByBatch(new Callback<ExamGroupData>() {
            @Override
            public void success(ExamGroupData examGroupData, Response response) {
                android.util.Log.d(TAG, "Response : " + new Gson().toJson(examGroupData));

                if (examGroupData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Prefs.with(ExamGroupActivity.this).save(PrefsKeys.EXAM_GROUP_RESPONSE_DATA, examGroupData);
                    setUIData(examGroupData);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d(TAG, "error : " + error.toString());
            }
        });
    }



}
