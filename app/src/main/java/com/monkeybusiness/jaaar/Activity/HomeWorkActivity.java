package com.monkeybusiness.jaaar.Activity;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.AnnouncementAdapter;
import com.monkeybusiness.jaaar.Adapter.HomeWorkAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.addHomeWorkObject.AddHomeWorkObject;
import com.monkeybusiness.jaaar.objectClasses.addHomeWorkObject.Hws;
import com.monkeybusiness.jaaar.objectClasses.busListResponse.BusListResponse;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.HomeWorkResponseData;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.Hw;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.CommonDialog;
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

/**
 * Created by rakesh on 31/8/16.
 */
public class HomeWorkActivity extends AppCompatActivity implements View.OnClickListener {

    private static final String TAG = "HomeWorkActivity";
    private static final String CW = "cw";
    private static final String HW = "hw";
    EditText editTextAnnouncements;
    Spinner busListSpinner;
    Spinner spinnerHomeWork;
    //    Spinner spinnerLecture;
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

    RelativeLayout relativeLayoutNodataFound;

    TextView textViewLectureList;
    String type = "";
    List<Integer> lectureIdslist = new ArrayList<Integer>();
    LinearLayout linearLayoutCheckBoxLecture;
    List<CheckBox> checkBoxesLecture;
    AlertDialog alertDialog;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homework);

        initializtion();
        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        getClassWorkListServerCall(CW, false);

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
        spinnerHomeWork = (Spinner) findViewById(R.id.spinnerHomeWork);
//        spinnerLecture = (Spinner) findViewById(R.id.spinnerLecture);
        listViewAnnouncements = (NonScrollListView) findViewById(R.id.listViewAnnouncements);
        progressBarAnnouncements = (ProgressBar) findViewById(R.id.progressBarAnnouncements);
        textViewLectureList = (TextView) findViewById(R.id.textViewLectureList);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        imageViewToolbaar = (ImageView) findViewById(R.id.imageViewToolbaar);
        linearLayoutDynamicCheckBoxLecture = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxLecture);
        linearLayoutDynamicCheckBoxClass = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxClass);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        textViewActionTitle.setText("Class Work/Home Work");

        imageViewToolbaar.setBackgroundDrawable(getResources().getDrawable(R.drawable.cancel_event));

        imageViewToolbaar.requestLayout();
        imageViewToolbaar.getLayoutParams().height = 40;
        imageViewToolbaar.getLayoutParams().width = 40;

        linearLayoutList = (LinearLayout) findViewById(R.id.linearLayoutList);

        buttonPost.setOnClickListener(this);
        relativeLayoutMenu.setOnClickListener(this);
        textViewLectureList.setOnClickListener(this);
        progressBarAnnouncements.getIndeterminateDrawable().setColorFilter(getResources().getColor(R.color.add_button_purple), PorterDuff.Mode.MULTIPLY);

        listViewAnnouncements.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "clicked");
                Intent intent = new Intent(HomeWorkActivity.this, HomeWorkListActivity.class);
                if (spinnerHomeWork.getSelectedItemPosition() == 0) {
                    intent.putExtra("type", CW);
                } else {
                    intent.putExtra("type", HW);
                }
                startActivity(intent);
            }
        });
//        busList = new ArrayList<>();
//        busListResponse = Prefs.with(this).getObject(PrefsKeys.BUS_LIST_RESPONSE, BusListResponse.class);
//
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {

            case R.id.buttonPost:
                postHomeWorkServerCall();
                break;
            case R.id.relativeLayoutMenu:
                finish();
                break;
            case R.id.textViewLectureList:
                alertDialog.show();
                break;

        }
    }

    private void postHomeWorkServerCall() {

        String details = editTextAnnouncements.getText().toString();
        if (details.isEmpty()) {
            new CommonDialog(this).Show("Please enter some text");
            return;
        }

        int typePosition = busListSpinner.getSelectedItemPosition();

        if (typePosition == 0) {
            new CommonDialog(this).Show("Please select type of work");
            return;
        }

//        int lecturePosition = spinnerLecture.getSelectedItemPosition();
//
//        if (lecturePosition == 0)
//        {
//            new CommonDialog(this).Show("Please select lecture for work");
//            return;
//        }

        ArrayList<Integer> lectureIdList = new ArrayList<>();
        boolean isLectureSelected = false;
        for (CheckBox checkBox : checkBoxesLecture) {
            if (checkBox.isChecked()) {
                lectureIdList.add((Integer) checkBox.getTag());
                isLectureSelected = true;
            }
        }

        if (!isLectureSelected) {
            new CommonDialog(this).Show("Please select lecture for work");
            return;
        }

        if (typePosition == 1) {
            type = "cw";
        } else {
            type = "hw";
        }


//        LectureResponseData lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA,LectureResponseData.class);
//        if (lectureResponseData !=null)
//        {
////            int id = lectureIdslist.get(lecturePosition-1);
////            Log.d(TAG,"lecture_Id "+id);
////            lectureId = String.valueOf(id);
//        }
//        else {
//            return;
//        }

        String date = Utils.simpleDateFormatter(new Date());

        AddHomeWorkObject addHomeWorkObject = new AddHomeWorkObject();
        Hws hw = new Hws();
        hw.setDetails(details);
        hw.setHwDate(date);
        hw.setHwType(type);
        hw.setLectureId(lectureIdList);

        addHomeWorkObject.setHws(hw);

        String jsonData = new Gson().toJson(addHomeWorkObject);

        LoadingBox.showLoadingDialog(this, "Posting Assignment...");

        try {
            TypedInput in = new TypedByteArray("application/json", jsonData.getBytes("UTF-8"));
            String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
            String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

            RestClient.getApiService(xCookies, aCookies).apiCallPOSTCWHW(in, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    editTextAnnouncements.setText("");
                    busListSpinner.setSelection(0);
//                    spinnerLecture.setSelection(0);
                    Log.d(TAG, "Response: " + s);
                    if (LoadingBox.isDialogShowing()) {
                        LoadingBox.dismissLoadingDialog();
                    }
                    for (CheckBox checkBox : checkBoxesLecture)
                    {
                        checkBox.setChecked(false);
                    }
                    textViewLectureList.setText("SELECT LECTURE");
                    new CommonDialog(HomeWorkActivity.this).Show("Successfully Added");
                    int itemPosition = spinnerHomeWork.getSelectedItemPosition();
                    if ((itemPosition == 0 && type.equalsIgnoreCase("cw"))) {
                        getClassWorkListServerCall(CW, true);
                    } else if (itemPosition == 1 && type.equalsIgnoreCase("hw")) {
                        getClassWorkListServerCall(HW, true);
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "error: " + error.toString());
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    private void getClassWorkListServerCall(String type, boolean showLoading) {

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
                null,
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

    public void lectureServerCall() {

        LoadingBox.showLoadingDialog(this, "Loading Assignments....");
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "Response : " + new Gson().toJson(lectureResponseData));
                Prefs.with(HomeWorkActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA, lectureResponseData);

                setCheckBoxes();
                List<String> lecturelist = new ArrayList<String>();
                lecturelist.add("Select");
                if (lectureResponseData.getData().getLectures() != null) {
                    for (Lecture lecture : lectureResponseData.getData().getLectures()) {
                        lecturelist.add(lecture.getLectureName());
                        lectureIdslist.add(lecture.getId());
                    }

                    List<String> categoryList = new ArrayList<>();
                    categoryList.add("Select");
                    categoryList.add("Class Work");
                    categoryList.add("Home Work");
                    ArrayAdapter<String> adapter = new ArrayAdapter<String>(HomeWorkActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, categoryList);
                    busListSpinner.setAdapter(adapter);

                    List<String> typeList = new ArrayList<>();
                    typeList.add("Class Work");
                    typeList.add("Home Work");
                    ArrayAdapter<String> categoryAdapter = new ArrayAdapter<String>(HomeWorkActivity.this,
                            android.R.layout.simple_spinner_dropdown_item, typeList);
                    spinnerHomeWork.setAdapter(categoryAdapter);

                    spinnerHomeWork.setOnItemSelectedListener(new Spinner.OnItemSelectedListener() {
                        @Override
                        public void onItemSelected(Spinner parent, View view, int position, long id) {
                            if (position == 0) {
                                getClassWorkListServerCall(CW, true);
                            } else {
                                getClassWorkListServerCall(HW, true);
                            }
                        }
                    });

//                    ArrayAdapter<String> lectureAdapter = new ArrayAdapter<String>(HomeWorkActivity.this,
//                            android.R.layout.simple_spinner_dropdown_item, lecturelist);
//                    spinnerLecture.setAdapter(lectureAdapter);
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

    private void setCheckBoxes() {

        AlertDialog.Builder dialogBuilder = new AlertDialog.Builder(this);
        LayoutInflater inflater = this.getLayoutInflater();
        View dialogView = inflater.inflate(R.layout.lectures_list, null);
        dialogBuilder.setView(dialogView);
        dialogBuilder.setPositiveButton("Ok", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {

            }
        });

        alertDialog = dialogBuilder.create();
        alertDialog.setOnDismissListener(new DialogInterface.OnDismissListener() {

            @Override
            public void onDismiss(DialogInterface dialog) {
                Log.d("TAG", "Dismissed");
                int i = 0;
                for (CheckBox checkBox : checkBoxesLecture) {
                    if (checkBox.isChecked()) {
                        i++;
                    }
                }

                textViewLectureList.setText("" + i + " Lectures Selected");
            }
        });
//        new ASSL(this, (ViewGroup) dialogView.findViewById(R.id.root), 1134, 720,
//                false);

        linearLayoutCheckBoxLecture = (LinearLayout) dialogView.findViewById(R.id.linearLayoutCheckBoxLecture);

        if (LoadingBox.isDialogShowing()) {
            LoadingBox.dismissLoadingDialog();
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
                linearLayoutCheckBoxLecture.addView(checkBox, params);
                checkBoxesLecture.add(checkBox);

                Log.d("events", "adding : " + lecture.getId());
            }
        }
    }

}