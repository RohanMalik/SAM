package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.BatchesAdapter;
import com.monkeybusiness.jaaar.Adapter.LecturesAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.batchesData.BatchesResponseData;
import com.monkeybusiness.jaaar.retrofit.CommonApiCalls;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class MyBatchesActivity extends BaseActivity {

    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;
    BatchesAdapter batchesAdapter;
    private final String TAG = "MyBatches";

    ProgressBar progressBarLectures;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_lectures);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Utils.classFlag = 1;

        toggleLayouts(linearlayoutAttendance, textViewAttendance);


        initialization();

        new CommonApiCalls(this).checkLoginServerCall();

        getBatchesServerCall();

    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        progressBarLectures = (ProgressBar) findViewById(R.id.progressBarLectures);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("My Batches");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);
        listViewLectures.setVisibility(View.GONE);

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

    private void setUIData(BatchesResponseData batchesResponseData) {

        if (!batchesResponseData.getData().getBatches().isEmpty())
        {
            progressBarLectures.setVisibility(View.GONE);
            listViewLectures.setVisibility(View.VISIBLE);
            batchesAdapter = new BatchesAdapter(this,batchesResponseData);
            listViewLectures.setAdapter(batchesAdapter);
        }
    }


    public void getBatchesServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetBatches(new Callback<BatchesResponseData>() {
            @Override
            public void success(BatchesResponseData batchesResponseData, Response response) {
                android.util.Log.d(TAG, "Response : " + new Gson().toJson(batchesResponseData));

                if (batchesResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Prefs.with(MyBatchesActivity.this).save(PrefsKeys.BATCHES_RESPONSE_DATA, batchesResponseData);
                    setUIData(batchesResponseData);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d(TAG, "error : " + error.toString());
            }
        });
    }



}
