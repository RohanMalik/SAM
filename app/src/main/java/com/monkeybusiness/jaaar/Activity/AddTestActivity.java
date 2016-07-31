package com.monkeybusiness.jaaar.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.AddTestObject.AddTestObject;
import com.monkeybusiness.jaaar.objectClasses.AddTestObject.Test;
import com.monkeybusiness.jaaar.objectClasses.addTestResponse.AddTestResponse;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

//import android.widget.Spinner;

public class AddTestActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddTestActivity";
    Spinner input_event_name;
    EditText input_event_desc;
    EditText input_event_full_name;

    TextView textViewDate;

    ImageView imageViewCross;
    ImageView imageViewSave;

//    Button buttonAddEvent;

//    RelativeLayout relativeLayoutMenu;
//    TextView textViewActionTitle;

    EditText editTextMax;
    EditText editTextMin;
    EditText editTextDuration;

    RelativeLayout relativeLayoutDate;

    boolean fromTo;

    LectureResponseData lectureResponseData;

    Intent intent;
    Calendar calendar;
    Date date;
    int month;
    int year;
    int day;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        initialization();

        lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA, LectureResponseData.class);

        intent = getIntent();
        if (lectureResponseData != null) {
            setUiData();
            lectureServerCall();
        } else {
            lectureServerCall();
        }

    }

    public void initialization() {

        input_event_name = (Spinner) findViewById(R.id.input_event_name);
        input_event_desc = (EditText) findViewById(R.id.input_event_desc);
        input_event_full_name = (EditText) findViewById(R.id.input_event_full_name);
        relativeLayoutDate = (RelativeLayout) findViewById(R.id.relativeLayoutDate);

        imageViewCross = (ImageView) findViewById(R.id.imageViewCross);
        imageViewSave = (ImageView) findViewById(R.id.imageViewSave);

//        buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);

        textViewDate = (TextView) findViewById(R.id.textViewDate);


//        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
//        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        editTextMax = (EditText) findViewById(R.id.editTextMax);
        editTextMin = (EditText) findViewById(R.id.editTextMin);

        editTextDuration = (EditText) findViewById(R.id.editTextDuration);


//        textViewActionTitle.setText("ADD TEST");
//
//        relativeLayoutMenu.setOnClickListener(this);
//        textViewActionTitle.setOnClickListener(this);

//        buttonAddEvent.setOnClickListener(this);
        textViewDate.setOnClickListener(this);
        relativeLayoutDate.setOnClickListener(this);
        imageViewCross.setOnClickListener(this);
        imageViewSave.setOnClickListener(this);
    }

    public void setUiData() {
        lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA, LectureResponseData.class);

        ArrayList<String> lectureList = new ArrayList<>();
        for (Lecture lecture : lectureResponseData.getData().getLectures()) {
            lectureList.add(lecture.getLectureName());
        }

        if (!lectureList.isEmpty()) {
            ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                    android.R.layout.simple_spinner_item, lectureList);

//            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
//                    R.array.example_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.expandable_list_content);
// Apply the adapter to the spinner
            input_event_name.setAdapter(adapter);
        }

        if (intent != null) {
            String lectureName = intent.getStringExtra(Constants.LECTURE_ID);

            if (lectureName != null) {
                int pos = lectureList.indexOf(lectureName);
                input_event_name.setSelection(pos);

                input_event_full_name.setText(intent.getStringExtra(Constants.TEST_NAME));

                textViewDate.setText(Utils.formatDateAndTime(intent.getStringExtra(Constants.DATE)));
                editTextMin.setText(intent.getIntExtra(Constants.MIN_MARKS, 0) + "");
                editTextMax.setText(intent.getIntExtra(Constants.MAX_MARKS, 0) + "");
                editTextDuration.setText(intent.getIntExtra(Constants.DURATION, 0) + "");
            }
        }
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutDate:
                showDateDialog();
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.buttonAddEvent:

                break;
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.imageViewSave:
                Log.d(TAG, "List Selection : " + input_event_name.getSelectedItem());

                String lectureName = intent.getStringExtra(Constants.LECTURE_ID);
                if (lectureName != null) {
                    updateTestServerCall();
                } else {
                    addTestServerCall();
                }
                break;
            case R.id.imageViewCross:
                finish();
                break;
        }
    }

    private void updateTestServerCall() {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        if (!textViewDate.getText().toString().equalsIgnoreCase("Select Date")) {

            String testDate;
            if (date!=null)
            {
                Calendar calendar = Calendar.getInstance();
                calendar.set(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());
                testDate = ISO8601.fromCalendar(calendar);
            }
            else
            {
                testDate = intent.getStringExtra(Constants.DATE);
            }

            String lectureId = lectureResponseData.getData().getLectures().get(input_event_name.getSelectedItemPosition()).getId() + "";
            String testName = input_event_name.getSelectedItem().toString();
            String desc = input_event_desc.getText().toString();
            String maxMarks = editTextMax.getText().toString();
            String minMarks = editTextMin.getText().toString();
            String duration = editTextDuration.getText().toString();

            Log.d(TAG, "Actual : " + new Gson().toJson(date) + "   testDate : " + testDate);

            AddTestObject addTestObject = new AddTestObject();


            Test test = new Test();

            test.setDescription(desc);
            test.setDurationMinutes(duration);
            test.setMaxMarks(maxMarks);
            test.setMinMarks(minMarks);
            test.setTestDate(testDate);
            test.setTestName(testName);

            addTestObject.setTest(test);

            String addTestJson = new Gson().toJson(addTestObject);

            Log.d(TAG, "AddTest : " + addTestJson);

            int testId = intent.getIntExtra(Constants.TEST_ID, 0);

            ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait", "Adding Test...", false);

            try {
                TypedInput typedInput = new TypedByteArray("application/json", addTestJson.getBytes("UTF-8"));

                RestClient.getApiServicePojo(xCookies, aCookies).apiCallPutTest(lectureId, String.valueOf(testId), typedInput, new Callback<AddTestResponse>() {
                    @Override
                    public void success(AddTestResponse addTestResponse, Response response) {
                        Log.d(TAG, "Response : " + new Gson().toJson(addTestResponse));

                        progressDialog.dismiss();
                        if (addTestResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AddTestActivity.this);
                            alert.setTitle("Test Added");
                            alert.setMessage("You Have successfully created test.");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        } else {
                            Utils.failureDialog(AddTestActivity.this, "Something went wrong", "Something went wrong please try again.");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                        progressDialog.dismiss();
                        Utils.failureDialog(AddTestActivity.this, "Something went wrong", "Something went wrong please try again.");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            Utils.failureDialog(this, "Warning", "Please select date");
        }

    }

    public void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddTestActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(AddTestActivity.this, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        Log.d("AddEvnt", "h: " + hourOfDay + " m: " + minute + "h1: " + hourOfDay);

        date.setHours(hourOfDay);
        date.setMinutes(minute);

        setDateToTextView();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lectureServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "Response : " + new Gson().toJson(lectureResponseData));
                Prefs.with(AddTestActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA, lectureResponseData);
                setUiData();
            }

            @Override
            public void failure(RetrofitError error) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "error : ");
            }
        });
    }

    public void addTestServerCall() {


        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String testDate;
        if (!textViewDate.getHint().toString().equalsIgnoreCase("Select Date") || !input_event_full_name.getText().toString().equalsIgnoreCase("")) {

            Calendar calendar = Calendar.getInstance();
            calendar.set(date.getYear(), date.getMonth(), date.getDate(), date.getHours(), date.getMinutes());
            testDate = ISO8601.fromCalendar(calendar);

            String lectureId = lectureResponseData.getData().getLectures().get(input_event_name.getSelectedItemPosition()).getId() + "";

            String testName = input_event_full_name.getText().toString();
            String desc = input_event_desc.getText().toString();
            String maxMarks = editTextMax.getText().toString();
            String minMarks = editTextMin.getText().toString();
            String duration = editTextDuration.getText().toString();

            Log.d(TAG, "Actual : " + new Gson().toJson(date) + "   testDate : " + testDate);

            AddTestObject addTestObject = new AddTestObject();


            Test test = new Test();

            test.setDescription(desc);
            test.setDurationMinutes(duration);
            test.setMaxMarks(maxMarks);
            test.setMinMarks(minMarks);
            test.setTestDate(testDate);
            test.setTestName(testName);

            addTestObject.setTest(test);

            String addTestJson = new Gson().toJson(addTestObject);

            Log.d(TAG, "AddTest : " + addTestJson);
//
//
//  ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait", "Adding Test...", false);

            LoadingBox.showLoadingDialog(this,"Saving Event...");


            try {
                TypedInput typedInput = new TypedByteArray("application/json", addTestJson.getBytes("UTF-8"));

                RestClient.getApiServicePojo(xCookies, aCookies).apiCallPostTest(lectureId, typedInput, new Callback<AddTestResponse>() {
                    @Override
                    public void success(AddTestResponse addTestResponse, Response response) {
                        Log.d(TAG, "Response : " + new Gson().toJson(addTestResponse));

                        if (LoadingBox.isDialogShowing()){
                            LoadingBox.dismissLoadingDialog();
                        }
                        if (addTestResponse.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AddTestActivity.this);
                            alert.setTitle("Test Added");
                            alert.setMessage("You Have successfully created test.");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                }
                            });
                            alert.show();
                        } else {
                            Utils.failureDialog(AddTestActivity.this, "Something went wrong", "Something went wrong please try again.");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                        if (LoadingBox.isDialogShowing()){
                            LoadingBox.dismissLoadingDialog();
                        }
                        Utils.failureDialog(AddTestActivity.this, "Something went wrong", "Something went wrong please try again.");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        } else {
            Utils.failureDialog(this, "Warning", "Please select date");
        }
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Log.d(TAG, "Date : " + year + ": " + monthOfYear + ":" + dayOfMonth);
        month = monthOfYear;
        this.year = year;
        day = dayOfMonth;

        date = new Date();
        date.setYear(year);
        date.setDate(dayOfMonth);
        date.setMonth(monthOfYear);

        showTimeDialog();
    }

    public void setDateToTextView() {
        Log.d(TAG, "Date : " + date.toString());

        Date formatDate = new Date();
        formatDate.setDate(date.getDate());
        formatDate.setMonth(date.getMonth());
        formatDate.setYear(date.getYear() - 1900);

        formatDate.setHours(date.getHours());
        formatDate.setMinutes(date.getMinutes());

        SimpleDateFormat format = new SimpleDateFormat("MMMM  dd, yyyy   HH:mm");

        textViewDate.setText(format.format(formatDate));
    }
}
