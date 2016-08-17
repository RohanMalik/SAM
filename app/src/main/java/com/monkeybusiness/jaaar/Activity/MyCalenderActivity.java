package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.EventsListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.eventResponse.EventResponseData;
import com.monkeybusiness.jaaar.retrofit.CommonApiCalls;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.dialogBox.LoadingBox;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class MyCalenderActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = MyCalenderActivity.class.getName();
    public final String DAY = "day";
    public final String WEEK = "week";
    public final String MONTH = "month";

    TextView textViewDay;
    TextView textViewWeek;
    TextView textViewMonth;
    TextView textViewCurrentDateCal;
    TextView textViewDayCal;
    TextView textViewActionTitle;

    FloatingActionButton fabAddEvent;
    ListView listViewEvents;
    RelativeLayout relativeLayoutMenu;
    EventsListAdapter eventsListAdapter;

    RelativeLayout relativeLayoutNodataFound;
    Date pickedDate = new Date();

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_calender);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Utils.classFlag = 6;
        toggleLayouts(linearlayoutCalender, textViewCalendar);

        initialization();

        new CommonApiCalls(this).checkLoginServerCall();

        setFont();
    }

    private void setFont() {
        textViewDay.setTypeface(FontClass.proximaRegular(this));
        textViewWeek.setTypeface(FontClass.proximaRegular(this));
        textViewMonth.setTypeface(FontClass.proximaRegular(this));
        textViewCurrentDateCal.setTypeface(FontClass.proximaBold(this));
        textViewDayCal.setTypeface(FontClass.proximaRegular(this));
        textViewActionTitle.setTypeface(FontClass.proximaRegular(this));
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutNodataFound = (RelativeLayout) findViewById(R.id.relativeLayoutNodataFound);

        textViewActionTitle.setText("MY CALENDAR");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewDay = (TextView) findViewById(R.id.textViewDay);
        textViewWeek = (TextView) findViewById(R.id.textViewWeek);
        textViewMonth = (TextView) findViewById(R.id.textViewMonth);
        textViewCurrentDateCal = (TextView) findViewById(R.id.textViewCurrentDateCal);
        textViewDayCal = (TextView) findViewById(R.id.textViewDayCal);

        fabAddEvent = (FloatingActionButton) findViewById(R.id.fabAddEvent);

        listViewEvents = (ListView) findViewById(R.id.listViewEvents);

        eventsListAdapter = new EventsListAdapter(this);
        listViewEvents.setAdapter(eventsListAdapter);

        Date date = new Date();
        textViewCurrentDateCal.setText(String.valueOf(date.getDate()));
        textViewCurrentDateCal.setOnClickListener(this);

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        textViewDayCal.setText(dateFormat.format(date));

        textViewDay.setOnClickListener(this);
        textViewMonth.setOnClickListener(this);
        textViewWeek.setOnClickListener(this);
        fabAddEvent.setOnClickListener(this);
    }

    public void setUiData(EventResponseData eventResponseData) {

        if (eventResponseData.getData().getEvents() != null) {
            if (!eventResponseData.getData().getEvents().isEmpty()) {
                listViewEvents.setVisibility(View.VISIBLE);
                relativeLayoutNodataFound.setVisibility(View.GONE);
                eventsListAdapter.setData(eventResponseData.getData().getEvents());
            } else {
                listViewEvents.setVisibility(View.GONE);
                relativeLayoutNodataFound.setVisibility(View.VISIBLE);
            }
        } else {
            listViewEvents.setVisibility(View.GONE);
            relativeLayoutNodataFound.setVisibility(View.VISIBLE);
        }
    }

    public void getEventsServerCallDay(String type) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String fromTime;
        String toTime;

        if (type.equalsIgnoreCase(DAY)) {

            Calendar start = Calendar.getInstance();
            start.set(pickedDate.getYear()+1900, pickedDate.getMonth(), pickedDate.getDate(), 0, 0);

            Calendar end = Calendar.getInstance();
            end.set(pickedDate.getYear()+1900, pickedDate.getMonth(), pickedDate.getDate(), 23, 59);

            fromTime = ISO8601.fromCalendar(start);
            toTime = ISO8601.fromCalendar(end);

        } else if (type.equalsIgnoreCase(WEEK)) {


            Calendar start = Calendar.getInstance();

            Date startDate = new Date();
            startDate.setHours(0);
            startDate.setMinutes(0);
            start.setTime(startDate);

            Calendar end = Calendar.getInstance();
            Date endDate = new Date();
            endDate.setDate(startDate.getDate() + 7);
            end.setTime(endDate);

            fromTime = ISO8601.fromCalendar(start);
            toTime = ISO8601.fromCalendar(end);
        } else {
            Calendar start = Calendar.getInstance();
            Date startDate = new Date();
            startDate.setHours(0);
            startDate.setMinutes(0);
            startDate.setDate(1);
            start.setTime(startDate);

            Calendar end = Calendar.getInstance();

            Date date = new Date();
            date.setDate(30);

            end.setTime(date);

            fromTime = ISO8601.fromCalendar(start);
            toTime = ISO8601.fromCalendar(end);
        }

        LoadingBox.showLoadingDialog(this,"Loading Data...");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetEvents(fromTime, toTime, new Callback<EventResponseData>() {
            @Override
            public void success(EventResponseData eventResponseData, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(eventResponseData));

                if (LoadingBox.isDialogShowing()){
                    LoadingBox.dismissLoadingDialog();
                }
                Prefs.with(MyCalenderActivity.this).save(PrefsKeys.EVENT_RESPONSE_DATA, eventResponseData);
                setUiData(eventResponseData);
            }

            @Override
            public void failure(RetrofitError error) {
                if (LoadingBox.isDialogShowing()){
                    LoadingBox.dismissLoadingDialog();
                }
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.textViewCurrentDateCal:
                showDatePicker();
                break;
            case R.id.textViewDay:
                pickedDate = new Date();
                toggleDays(textViewDay);
                getEventsServerCallDay(DAY);
                break;
            case R.id.textViewWeek:
                toggleDays(textViewWeek);
                getEventsServerCallDay(WEEK);
                break;
            case R.id.textViewMonth:
                toggleDays(textViewMonth);
                getEventsServerCallDay(MONTH);
                break;
            case R.id.fabAddEvent:
                Intent intent = new Intent(this, AddEventActivity.class);
                startActivity(intent);
                break;
        }
    }

    private void toggleDays(TextView textView) {

        textViewDay.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_button_selector));
        textViewWeek.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_button_selector));
        textViewMonth.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_button_selector));
        textViewDay.setTextColor(getResources().getColor(R.color.white));
        textViewWeek.setTextColor(getResources().getColor(R.color.white));
        textViewMonth.setTextColor(getResources().getColor(R.color.white));

        textView.setBackgroundDrawable(getResources().getDrawable(R.drawable.day_button_white));
        textView.setTextColor(getResources().getColor(R.color.calender_button_transarent));
    }

    private void showDatePicker() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(MyCalenderActivity.this, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        pickedDate.setDate(dayOfMonth);
        pickedDate.setYear(year-1900);
        pickedDate.setMonth(monthOfYear);
        getEventsServerCallDay(DAY);

        textViewCurrentDateCal.setText(String.valueOf(dayOfMonth));

        SimpleDateFormat dateFormat = new SimpleDateFormat("EEEE");
        textViewDayCal.setText(dateFormat.format(pickedDate));
    }

    @Override
    protected void onResume() {
        super.onResume();

        toggleDays(textViewDay);
        getEventsServerCallDay(DAY);
    }
}
