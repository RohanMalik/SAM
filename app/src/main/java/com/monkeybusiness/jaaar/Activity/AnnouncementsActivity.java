package com.monkeybusiness.jaaar.Activity;

import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.Gravity;
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
import com.monkeybusiness.jaaar.Adapter.AnnouncementAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.AddAnnouncementsData.AddAnnouncementObject;
import com.monkeybusiness.jaaar.objectClasses.AddAnnouncementsData.Announcements;
import com.monkeybusiness.jaaar.objectClasses.announcementResponse.AnnouncementsResponseData;
import com.monkeybusiness.jaaar.objectClasses.batchesData.Batch;
import com.monkeybusiness.jaaar.objectClasses.batchesData.BatchesResponseData;
import com.monkeybusiness.jaaar.objectClasses.busListResponse.BusListResponse;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.Spinner;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

public class AnnouncementsActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "Announcements";
    EditText editTextAnnouncements;
    Spinner busListSpinner;
    Button buttonPost;
    NonScrollListView listViewAnnouncements;
    LinearLayout linearLayoutList;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;
    ImageView imageViewToolbaar;

    ProgressBar progressBarAnnouncements;

    AnnouncementAdapter announcementAdapter;

    ArrayList<String> busList;
    BusListResponse busListResponse;

    LinearLayout linearLayoutDynamicCheckBoxLecture;
    LinearLayout linearLayoutDynamicCheckBoxClass;
    List<CheckBox> checkBoxesBatch;
    List<CheckBox> checkBoxesLecture;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_announcements);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        initializtion();
        getAnnouncementServerCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lectureServerCall();
            }
        }, 200);
    }

    private void initializtion() {

        editTextAnnouncements = (EditText) findViewById(R.id.editTextAnnouncements);
        busListSpinner = (Spinner) findViewById(R.id.busListSpinner);
        buttonPost = (Button) findViewById(R.id.buttonPost);
        listViewAnnouncements = (NonScrollListView) findViewById(R.id.listViewAnnouncements);
        progressBarAnnouncements = (ProgressBar) findViewById(R.id.progressBarAnnouncements);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        imageViewToolbaar = (ImageView) findViewById(R.id.imageViewToolbaar);
        linearLayoutDynamicCheckBoxLecture = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxLecture);
        linearLayoutDynamicCheckBoxClass = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxClass);

        textViewActionTitle.setText("Announcements");

        imageViewToolbaar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cancel_event));

        linearLayoutList = (LinearLayout) findViewById(R.id.linearLayoutList);

        buttonPost.setOnClickListener(this);
        relativeLayoutMenu.setOnClickListener(this);
        progressBarAnnouncements.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);

//        busList = new ArrayList<>();
//        busListResponse = Prefs.with(this).getObject(PrefsKeys.BUS_LIST_RESPONSE, BusListResponse.class);
//
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.buttonPost:
                sendPostAnnouncementServerCall();
                break;
            case R.id.relativeLayoutMenu:
                finish();
                break;
        }
    }

    private void sendPostAnnouncementServerCall() {

        String announcement = editTextAnnouncements.getText().toString();
        if (announcement.isEmpty()) {
            Utils.failureDialog(this, "Something went wrong", "Please enter some text.");
            return;
        }

        if (busListSpinner.getSelectedItemPosition() == 0)
        {
            Utils.failureDialog(this, "Something went wrong", "Please select lecture or batch");
            return;
        }

        List<Integer> annIds = new ArrayList<>();
        String type = "";

        if (busListSpinner.getSelectedItemPosition() == 1) {
            type = "lecture";
            for (CheckBox checkBox : checkBoxesLecture) {
                if (checkBox.isChecked()) {
                    annIds.add((Integer) checkBox.getTag());
                }
            }
        } else {
            type = "batch";
            for (CheckBox checkBox : checkBoxesBatch) {
                if (checkBox.isChecked()) {
                    annIds.add((Integer) checkBox.getTag());
                }
            }
        }

        if (annIds.isEmpty())
        {
            Utils.failureDialog(this, "Something went wrong", "Please select atleast one checkbox");
            return;
        }

        LoadingBox.showLoadingDialog(this, "Posting Announcements...");

        AddAnnouncementObject addAnnouncementObject = new AddAnnouncementObject();

        Announcements announcements = new Announcements();
        announcements.setAnnouncementTypeType(type);
        announcements.setAnnouncementDetails(announcement);
        announcements.setAnnouncementTypeId(annIds);

        addAnnouncementObject.setAnnouncements(announcements);

        String jsonObject = new Gson().toJson(addAnnouncementObject);

        try {
            TypedInput in = new TypedByteArray("application/json", jsonObject.getBytes("UTF-8"));
            String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
            String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");
            RestClient.getApiService(xCookies, aCookies).apiCallPostAnnouncment(in, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d("Announcement", "Response : " + s);
                    if (LoadingBox.isDialogShowing()) {
                        LoadingBox.dismissLoadingDialog();
                    }
                    progressBarAnnouncements.setVisibility(View.VISIBLE);
                    linearLayoutList.setVisibility(View.INVISIBLE);
                    getAnnouncementServerCall();
                    editTextAnnouncements.setText("");
                    busListSpinner.setSelection(0);
                    linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
                    linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d("Announcement", "error : " + error.toString());
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    public void getAnnouncementServerCall() {
        Date fromDate = new Date();
        fromDate.setDate(fromDate.getDate() - 7);

        Date toDate = new Date();
        toDate.setDate(toDate.getDate() + 7);

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");
        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetAnnouncements(
                Utils.simpleDateFormatter(fromDate),
                Utils.simpleDateFormatter(toDate),
                new Callback<AnnouncementsResponseData>() {
                    @Override
                    public void success(AnnouncementsResponseData announcementsResponseData, Response response) {
                        Log.d("Annuncements", "Response: " + new Gson().toJson(announcementsResponseData));
                        setUiData(announcementsResponseData);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d("Annuncements", "error: " + error.toString());
                    }
                });
    }

    private void setUiData(AnnouncementsResponseData announcementsResponseData) {
        if (!announcementsResponseData.getData().getAnnouncements().isEmpty()) {
            progressBarAnnouncements.setVisibility(View.GONE);
            linearLayoutList.setVisibility(View.VISIBLE);
            announcementAdapter = new AnnouncementAdapter(this, announcementsResponseData.getData().getAnnouncements());
            listViewAnnouncements.setAdapter(announcementAdapter);
        }
    }

    public void getBusListServerCallDay() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetBusList(new Callback<BusListResponse>() {
            @Override
            public void success(BusListResponse busListResponse, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(busListResponse));

                Prefs.with(AnnouncementsActivity.this).save(PrefsKeys.BUS_LIST_RESPONSE, busListResponse);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    public void lectureServerCall() {

        LoadingBox.showLoadingDialog(this,"Loading Announcements...");
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "Response : " + new Gson().toJson(lectureResponseData));
                Prefs.with(AnnouncementsActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA, lectureResponseData);
                getBatchesServerCall();
            }

            @Override
            public void failure(RetrofitError error) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "error : ");
            }
        });
    }

    public void getBatchesServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetBatches(new Callback<BatchesResponseData>() {
            @Override
            public void success(BatchesResponseData batchesResponseData, Response response) {
                android.util.Log.d(TAG, "Response : " + new Gson().toJson(batchesResponseData));

                if (batchesResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Prefs.with(AnnouncementsActivity.this).save(PrefsKeys.BATCHES_RESPONSE_DATA, batchesResponseData);
                    setCheckBoxes();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    private void setCheckBoxes() {

        if (LoadingBox.isDialogShowing()){
            LoadingBox.dismissLoadingDialog();
        }

        List<String> categoryList = new ArrayList<>();
        categoryList.add("Select");
        categoryList.add("Lecture");
        categoryList.add("Batch");

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_dropdown_item, categoryList);
        busListSpinner.setAdapter(adapter);

        busListSpinner.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
            @Override
            public void onItemSelected(Spinner parent, View view, int position, long id) {
                switch (position)
                {
                    case 1:
                        linearLayoutDynamicCheckBoxLecture.setVisibility(View.VISIBLE);
                        linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
                        break;
                    case 2:
                        linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
                        linearLayoutDynamicCheckBoxClass.setVisibility(View.VISIBLE);
                        break;
                }
            }
        });

        BatchesResponseData batchesResponseData = Prefs.with(this).getObject(PrefsKeys.BATCHES_RESPONSE_DATA, BatchesResponseData.class);

        checkBoxesBatch = new ArrayList<>();

        if (!batchesResponseData.getData().getBatches().isEmpty()) {
            for (Batch batch : batchesResponseData.getData().getBatches()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(batch.getClassAlias());
                checkBox.setTag(batch.getId());
                checkBox.applyStyle(R.style.checkBoxStyle);
                linearLayoutDynamicCheckBoxClass.addView(checkBox, params);
                checkBoxesBatch.add(checkBox);

                Log.d("events", "Batch adding : " + batch.getId());
            }

        }


        checkBoxesLecture = new ArrayList<>();

        LectureResponseData lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA, LectureResponseData.class);

        if (!lectureResponseData.getData().getLectures().isEmpty()) {
            for (Lecture lecture : lectureResponseData.getData().getLectures()) {
                LinearLayout.LayoutParams params = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                params.gravity = Gravity.CENTER_VERTICAL;
                CheckBox checkBox = new CheckBox(this);
                checkBox.setText(lecture.getLectureName());
                checkBox.setTag(lecture.getId());
                checkBox.applyStyle(R.style.checkBoxStyle);
                linearLayoutDynamicCheckBoxLecture.addView(checkBox, params);
                checkBoxesLecture.add(checkBox);

                Log.d("events", "adding : " + lecture.getId());
            }

        }
    }
}
