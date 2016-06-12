package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.AddTestObject.AddTestObject;
import com.monkeybusiness.jaaar.objectClasses.AddTestObject.Test;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.Lecture;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

//import android.widget.Spinner;

public class AddTestActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener, com.wdullaer.materialdatetimepicker.date.DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AddTestActivity";
    Spinner input_event_name;
    EditText input_event_desc;

    TextView textViewDate;

    Button buttonAddEvent;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    boolean fromTo;

    LectureResponseData lectureResponseData;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initialization();

        lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA,LectureResponseData.class);

        if (lectureResponseData != null)
        {
            setUiData();
        }
        else
        {
            lectureServerCall();
        }
    }

    public void initialization()
    {

        input_event_name = (Spinner) findViewById(R.id.input_event_name);
        input_event_desc = (EditText) findViewById(R.id.input_event_desc);

        buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);

        textViewDate = (TextView) findViewById(R.id.textViewDate);


        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        textViewActionTitle.setText("ADD EVENT");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        buttonAddEvent.setOnClickListener(this);
        textViewDate.setOnClickListener(this);

    }

    public void setUiData()
    {
        lectureResponseData = Prefs.with(this).getObject(PrefsKeys.LECTURE_RESPONSE_DATA,LectureResponseData.class);

        ArrayList<String> lectureList = new ArrayList<>();
        for (Lecture lecture : lectureResponseData.getData().getLectures())
        {
            lectureList.add(lecture.getLectureName());
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,
                android.R.layout.simple_spinner_item, lectureList);
// Specify the layout to use when the list of choices appears
//        adapter.setDropDownViewResource(android.R.layout.expandable_list_content);
// Apply the adapter to the spinner
        input_event_name.setAdapter(adapter);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.textViewDate:
                showDateDialog();
                 break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.buttonAddEvent:
                Log.d(TAG,"List Selection : "+input_event_name.getSelectedItem());
                addTestServerCall();
                break;
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }

    public void showTimeDialog()
    {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddTestActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    public void showDateDialog()
    {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(AddTestActivity.this,now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    Calendar calendar;
    Date date;

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        Log.d("AddEvnt","h: "+hourOfDay+" m: "+minute+"h1: "+hourOfDay);

        date.setHours(hourOfDay);
        date.setMinutes(minute);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public void lectureServerCall()
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        RestClient.getApiServicePojo(xCookies,aCookies).apiCallLectures(new Callback<LectureResponseData>() {
            @Override
            public void success(LectureResponseData lectureResponseData, Response response) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG,"Response : "+new Gson().toJson(lectureResponseData));
                Prefs.with(AddTestActivity.this).save(PrefsKeys.LECTURE_RESPONSE_DATA,lectureResponseData);
                setUiData();
            }

            @Override
            public void failure(RetrofitError error) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG,"error : ");
            }
        });
    }

    public void addTestServerCall()
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        Calendar calendar = Calendar.getInstance();
        calendar.set(date.getYear(),date.getMonth(),date.getDate(),date.getHours(),date.getMinutes());

        String lectureId = lectureResponseData.getData().getLectures().get(input_event_name.getSelectedItemPosition()).getId()+"";
        String testDate = ISO8601.fromCalendar(calendar);
        String testName = input_event_name.getSelectedItem().toString();
        String desc = input_event_desc.getText().toString();
        String maxMarks = "100";
        String minMarks = "35";
        String duration = "60";

        Log.d(TAG,"Actual : "+new Gson().toJson(date)+"   testDate : "+ testDate);

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

        Log.d(TAG,"AddTest : "+addTestJson);

        try {
            TypedInput typedInput = new TypedByteArray("application/json",addTestJson.getBytes("UTF-8"));

            RestClient.getApiService(xCookies,aCookies).apiCallPostTest(lectureId, typedInput, new Callback<String>() {
                @Override
                public void success(String s, Response response) {
                    Log.d(TAG,"Response : "+s);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG,"error : "+error.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

    }

    int month;
    int year;
    int day;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        Log.d(TAG,"Date : "+year+": "+monthOfYear+":"+dayOfMonth);
        month = monthOfYear;
        this.year = year;
        day = dayOfMonth;

        date = new Date();
        date.setYear(year);
        date.setDate(dayOfMonth);
        date.setMonth(monthOfYear);

        showTimeDialog();
    }
}
