package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.graphics.PorterDuff;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.HomeWorkAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.HomeWorkResponseData;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.Hw;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
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
    ListView listViewAnnouncements;
    LinearLayout linearLayoutList;
    ProgressBar progressBarAnnouncements;

    RelativeLayout relativeLayoutNodataFound;
    String type;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_work_list);

        initializtion();
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Intent intent = getIntent();
        type = intent.getStringExtra("type");

        if (type.equalsIgnoreCase(CW))
        {
            getClassWorkListServerCall(CW,true);
        }else {
            getClassWorkListServerCall(HW,true);
        }
    }

    private void initializtion() {

        spinnerHomeWork = (Spinner) findViewById(R.id.spinnerHomeWork);
        listViewAnnouncements = (ListView) findViewById(R.id.listViewAnnouncements);
        progressBarAnnouncements = (ProgressBar) findViewById(R.id.progressBarAnnouncements);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        linearLayoutList = (LinearLayout) findViewById(R.id.linearLayoutList);

        progressBarAnnouncements.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);
    }

    private void setUiData(List<Hw> hws) {
        if (!hws.isEmpty()) {
            progressBarAnnouncements.setVisibility(View.GONE);
            linearLayoutList.setVisibility(View.VISIBLE);
            relativeLayoutNodataFound.setVisibility(View.GONE);
            HomeWorkAdapter homeWorkAdapter = new HomeWorkAdapter(this,hws);
            listViewAnnouncements.setAdapter(homeWorkAdapter);
        }else {
            relativeLayoutNodataFound.setVisibility(View.VISIBLE);
            progressBarAnnouncements.setVisibility(View.GONE);
            linearLayoutList.setVisibility(View.GONE);
        }
    }

    private void getClassWorkListServerCall(String type, boolean showLoading) {

        if (showLoading)
        {
            LoadingBox.showLoadingDialog(this,"Loading Assignments....");
        }

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        Date fromDate = new Date();
        fromDate.setDate(fromDate.getDate() - 7);

        Date toDate = new Date();
        toDate.setDate(toDate.getDate() + 7);

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallGetCWHW(
                Utils.simpleDateFormatter(fromDate),
                Utils.simpleDateFormatter(toDate),
                type, new Callback<HomeWorkResponseData>() {
                    @Override
                    public void success(HomeWorkResponseData homeWorkResponseData, Response response) {
                        Log.d(TAG,"Response : "+new Gson().toJson(homeWorkResponseData));

                        setUiData(homeWorkResponseData.getData().getHws());
                        if (showLoading)
                        {
                            LoadingBox.dismissLoadingDialog();
                        }

                        List<String> typeList = new ArrayList<>();
                        typeList.add("Class Work");
                        typeList.add("Home Work");
                        ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(HomeWorkListActivity.this,
                                android.R.layout.simple_spinner_dropdown_item, typeList);
                        spinnerHomeWork.setAdapter(categoryAdapter);

                        if (type.equalsIgnoreCase(CW))
                        {
                            spinnerHomeWork.setSelection(0);
                        }else {
                            spinnerHomeWork.setSelection(1);
                        }

                        spinnerHomeWork.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                            @Override
                            public void onItemSelected(Spinner parent, View view, int position, long id) {
                                if (position==0)
                                {
                                    getClassWorkListServerCall(CW,true);
                                }else {
                                    getClassWorkListServerCall(HW,true);
                                }
                            }
                        });

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG,"error : "+error.toString());
                    }
                });
    }
}
