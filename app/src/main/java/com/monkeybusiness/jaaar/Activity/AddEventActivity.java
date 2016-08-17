package com.monkeybusiness.jaaar.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.addEventObject.AddEventObject;
import com.monkeybusiness.jaaar.objectClasses.addEventObject.Events;
import com.monkeybusiness.jaaar.objectClasses.addEventResponse.AddEventResponseData;
import com.monkeybusiness.jaaar.objectClasses.batchesData.Batch;
import com.monkeybusiness.jaaar.objectClasses.batchesData.BatchesResponseData;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.CheckBox;
import com.rey.material.widget.RadioButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

//import android.widget.Spinner;

public class AddEventActivity extends BaseActivity implements View.OnClickListener, TimePickerDialog.OnTimeSetListener, DatePickerDialog.OnDateSetListener {

    ImageView imageViewCross;

    EditText input_event_name;
    EditText input_event_desc;
    TextView textViewFrom;
    TextView textViewTo;
    TextView textViewTitleFrom;
    TextView textViewTitleTo;
    RadioButton radioButtonPersonal;
    RadioButton radioButtonClass;
    RadioButton radioButtonLecture;

//    Button buttonAddEvent;

    com.rey.material.widget.RelativeLayout relativeLayoutAdd;

    RelativeLayout relativeLayoutMenu;

    LinearLayout linearLayoutDynamicCheckBoxClass;
    LinearLayout linearLayoutDynamicCheckBoxLecture;

    TextView textViewActionTitle;

    ProgressBar progressBarAddEvent;

    LinearLayout linearLayoutMainAddEvent;

    boolean fromTo;
    Date startDate;
    Date endDate;
    int year;
    int monthOfYear;
    int dayOfMonth;
    List<CheckBox> checkBoxesBatch;
    List<CheckBox> checkBoxesLecture;
    private String TAG = "AddEventActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);
        initialization();

        getBatchesServerCall();

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                lectureServerCall();
            }
        }, 200);

        setFont();
    }

    private void setFont() {
        input_event_name.setTypeface(FontClass.proximaBold(this));
        input_event_desc.setTypeface(FontClass.proximaRegular(this));
        textViewFrom.setTypeface(FontClass.proximaRegular(this));
        textViewTo.setTypeface(FontClass.proximaRegular(this));
        textViewTitleFrom.setTypeface(FontClass.proximaRegular(this));
        textViewTitleTo.setTypeface(FontClass.proximaRegular(this));
        radioButtonPersonal.setTypeface(FontClass.proximaRegular(this));
        radioButtonClass.setTypeface(FontClass.proximaRegular(this));
        radioButtonLecture.setTypeface(FontClass.proximaRegular(this));
    }

    public void initialization() {

        linearLayoutMainAddEvent = (LinearLayout) findViewById(R.id.linearLayoutMainAddEvent);

        input_event_name = (EditText) findViewById(R.id.input_event_name);
        input_event_desc = (EditText) findViewById(R.id.input_event_desc);
        textViewTitleFrom = (TextView) findViewById(R.id.textViewTitleFrom);
        textViewTitleTo = (TextView) findViewById(R.id.textViewTitleTo);

        imageViewCross = (ImageView) findViewById(R.id.imageViewCross);

//        buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);

        relativeLayoutAdd = (com.rey.material.widget.RelativeLayout) findViewById(R.id.relativeLayoutAdd);

        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewTo = (TextView) findViewById(R.id.textViewTo);

        linearLayoutDynamicCheckBoxClass = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxClass);
        linearLayoutDynamicCheckBoxLecture = (LinearLayout) findViewById(R.id.linearLayoutDynamicCheckBoxLecture);

        linearLayoutMainAddEvent.setVisibility(View.GONE);

        progressBarAddEvent = (ProgressBar) findViewById(R.id.progressBarAddEvent);

        radioButtonPersonal = (RadioButton) findViewById(R.id.radioButtonPersonal);
        radioButtonClass = (RadioButton) findViewById(R.id.radioButtonClass);
        radioButtonLecture = (RadioButton) findViewById(R.id.radioButtonLecture);

        radioButtonClass.setOnClickListener(this);
        radioButtonLecture.setOnClickListener(this);
        radioButtonPersonal.setOnClickListener(this);
        imageViewCross.setOnClickListener(this);

        startDate = new Date();

        SimpleDateFormat format = new SimpleDateFormat("MMMM  dd, yyyy    HH:mm");

        textViewFrom.setText(format.format(startDate));

        endDate = new Date();
        endDate.setDate(endDate.getDate()+1);
        textViewTo.setText(format.format(endDate));


//        spinner.setOnItemClickListener(new Spinner.OnItemClickListener() {
//            @Override
//            public boolean onItemClick(Spinner parent, View view, int position, long id) {
//
//                Log.d("abc", "Position : " + position);
//                if (position == 1) {
//                    linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
//                    linearLayoutDynamicCheckBoxClass.setVisibility(View.VISIBLE);
//                } else if (position == 2) {
//                    linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
//                    linearLayoutDynamicCheckBoxLecture.setVisibility(View.VISIBLE);
//                } else {
//                    linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
//                    linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
//                }
//                return true;
//            }
//        });


        relativeLayoutAdd.setOnClickListener(this);
        textViewFrom.setOnClickListener(this);
        textViewTo.setOnClickListener(this);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.textViewFrom:
                showDateDialog();
                fromTo = true;
                break;
            case R.id.textViewTo:
                showDateDialog();
                fromTo = false;
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.relativeLayoutAdd:
                postEventServerCall();
                break;
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.radioButtonClass:
                toggleRadioButton(radioButtonClass);
                linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
                linearLayoutDynamicCheckBoxClass.setVisibility(View.VISIBLE);
                break;
            case R.id.radioButtonPersonal:
                linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
                linearLayoutDynamicCheckBoxLecture.setVisibility(View.GONE);
                toggleRadioButton(radioButtonPersonal);
                break;
            case R.id.radioButtonLecture:
                linearLayoutDynamicCheckBoxClass.setVisibility(View.GONE);
                linearLayoutDynamicCheckBoxLecture.setVisibility(View.VISIBLE);
                toggleRadioButton(radioButtonLecture);
                break;
            case R.id.imageViewCross:
                finish();
                break;
        }
    }

    public void toggleRadioButton(RadioButton radioButton) {

        radioButtonClass.setChecked(false);
        radioButtonPersonal.setChecked(false);
        radioButtonLecture.setChecked(false);

        radioButton.setChecked(true);
    }

    public void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddEventActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        Log.d("AddEvnt", "h: " + hourOfDay + " m: " + minute + "h1: " + hourOfDay + " m1: " + second);

        if (fromTo) {

            startDate = new Date();
            startDate.setYear(year - 1900);
            startDate.setMonth(monthOfYear);
            startDate.setDate(dayOfMonth);

            startDate.setHours(hourOfDay);
            startDate.setMinutes(minute);

            SimpleDateFormat format = new SimpleDateFormat("MMMM  dd, yyyy    HH:mm");

            textViewFrom.setText(format.format(startDate));
        } else {
            endDate = new Date();
            endDate.setYear(year - 1900);
            endDate.setMonth(monthOfYear);
            endDate.setDate(dayOfMonth);

            endDate.setHours(hourOfDay);
            endDate.setMinutes(minute);
            SimpleDateFormat format = new SimpleDateFormat("MMMM  dd, yyyy    HH:mm");

            textViewTo.setText(format.format(endDate));

        }
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

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG, " " + year + " : " + monthOfYear + " : " + dayOfMonth);
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;

        showTimeDialog();
    }

    public void postEventServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String eventName = input_event_name.getText().toString();
        String eventDesc = input_event_desc.getText().toString();
        String type = "";

        List<Integer> eventId = new ArrayList<>();

        if (radioButtonPersonal.isChecked()) {
            type = "UserLogin";
        } else if (radioButtonClass.isChecked()) {
            type = "Batch";
        } else {
            type = "Lecture";
        }

        if (type.equalsIgnoreCase("Batch")) {
            for (CheckBox checkBox : checkBoxesBatch) {
                if (checkBox.isChecked()) {
                    eventId.add((Integer) checkBox.getTag());
                }
            }
        } else {
            for (CheckBox checkBox : checkBoxesLecture) {
                if (checkBox.isChecked()) {
                    eventId.add((Integer) checkBox.getTag());
                }
            }
        }

        Log.d("events", "ids : " + new Gson().toJson(eventId));

        if (textViewFrom.getText().toString().equalsIgnoreCase("select date") || textViewTo.getText().toString().equalsIgnoreCase("select date")) {
            Utils.failureDialog(this, "Warning", "Please select date.");
        } else if (!type.equalsIgnoreCase("userLogin") && eventId.isEmpty()) {
            Utils.failureDialog(this, "Warning", "Please select event.");
        } else if (eventName.equalsIgnoreCase("")) {
            Utils.failureDialog(this, "Warning", "Please enter event name.");
        } else {
            AddEventObject object = new AddEventObject();

            Events events = new Events();
            events.setEventName(eventName);
            events.setEventDescription(eventDesc);
            events.setEventTypeType(type);
            events.setEventTypeId(eventId);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(startDate.getYear() + 1900, startDate.getMonth(), startDate.getDate(), startDate.getHours(), startDate.getMinutes());

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endDate.getYear() + 1900, endDate.getMonth(), endDate.getDate(), endDate.getHours(), endDate.getMinutes());

            events.setStartTime(ISO8601.fromCalendar(startCalendar));
            events.setEndTime(ISO8601.fromCalendar(endCalendar));

            object.setEvents(events);

            Log.d(TAG, "object : " + new Gson().toJson(object));

            String jsonObject = new Gson().toJson(object);

            LoadingBox.showLoadingDialog(this,"Adding Event...");
            try {
                TypedInput typedInput = new TypedByteArray("application/json", jsonObject.getBytes("UTF-8"));
                RestClient.getApiServicePojo(xCookies, aCookies).apiCallPostEvent(typedInput, new Callback<AddEventResponseData>() {
                    @Override
                    public void success(AddEventResponseData addEventResponseData, Response response) {
                        Log.d(TAG, "Response : " + new Gson().toJson(addEventResponseData));

                        if (LoadingBox.isDialogShowing()){
                            LoadingBox.dismissLoadingDialog();
                        }
                        if (addEventResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AddEventActivity.this);
                            alert.setTitle("Event Added");
                            alert.setMessage("You Have successfully created event.");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
//                                    Intent intent = new Intent(AddEventActivity.this
//                                            , MyCalendarFragment.class);
//                                    startActivity(intent);
                                }
                            });
                            alert.show();
                        } else {
                            Utils.failureDialog(AddEventActivity.this, "Warning...", "Something went wrong, please try again.");
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                        if (LoadingBox.isDialogShowing()){
                            LoadingBox.dismissLoadingDialog();
                        }
                        Utils.failureDialog(AddEventActivity.this, "Something went wrong", "Plaese try again.");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
//        Intent intent = new Intent(AddEventActivity.this
//                , MyCalendarFragment.class);
//        startActivity(intent);
    }

    private void setCheckBoxes() {

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


    public void getBatchesServerCall() {

        LoadingBox.showLoadingDialog(this,"Loading...");

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetBatches(new Callback<BatchesResponseData>() {
            @Override
            public void success(BatchesResponseData batchesResponseData, Response response) {
                android.util.Log.d(TAG, "Response : " + new Gson().toJson(batchesResponseData));

                if (batchesResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Prefs.with(AddEventActivity.this).save(PrefsKeys.BATCHES_RESPONSE_DATA, batchesResponseData);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                android.util.Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    public void lectureServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "Response : " + new Gson().toJson(lectureResponseData));
                Prefs.with(AddEventActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA, lectureResponseData);
                setCheckBoxes();
                if (LoadingBox.isDialogShowing())
                {
                    LoadingBox.dismissLoadingDialog();
                }
                progressBarAddEvent.setVisibility(View.GONE);
                linearLayoutMainAddEvent.setVisibility(View.VISIBLE);
            }

            @Override
            public void failure(RetrofitError error) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG, "error : ");
            }
        });
    }
}
