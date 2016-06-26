package com.monkeybusiness.jaaar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.AddEventActivity;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.agendacalendarview.AgendaCalendarView;
import com.monkeybusiness.jaaar.agendacalendarview.CalendarPickerController;
import com.monkeybusiness.jaaar.agendacalendarview.models.BaseCalendarEvent;
import com.monkeybusiness.jaaar.agendacalendarview.models.CalendarEvent;
import com.monkeybusiness.jaaar.agendacalendarview.models.DayItem;
import com.monkeybusiness.jaaar.objectClasses.eventResponse.Event;
import com.monkeybusiness.jaaar.objectClasses.eventResponse.EventResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.FloatingActionButton;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rakesh on 13/1/16.
 */
public class MyCalendarFragment extends BaseActivity implements View.OnClickListener {

    View rootView;
    AgendaCalendarView agendaCalendarView;
    TextView textViewCurrentDate;

    FloatingActionButton fabAdd;

    List<CalendarEvent> eventList = new ArrayList<>();
    Calendar minDate;
    Calendar maxDate;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;
    private String TAG = "MyCalendarFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_calendar);

        Utils.classFlag = 5;
        toggleLayouts(linearlayoutCalender, textViewCalendar);
        initialization();

    }
//
//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
//
//        rootView = inflater.inflate(R.layout.fragment_calendar,container,false);
//
//        initialization();
//
//        return rootView;
//    }

    public void initialization() {
        agendaCalendarView = (AgendaCalendarView) findViewById(R.id.agenda_calendar_view);
        textViewCurrentDate = (TextView) findViewById(R.id.textViewCurrentDate);

        fabAdd = (FloatingActionButton) findViewById(R.id.fabAdd);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        textViewActionTitle.setText("MY CALENDAR");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        fabAdd.setOnClickListener(this);

        minDate = Calendar.getInstance();
        maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

    }

    public void setUiData() {
        mockList(eventList);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                agendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), new CalendarPickerController() {
                    @Override
                    public void onDaySelected(DayItem dayItem) {

                        Log.d("MyCalendar", "day " + dayItem.getDate() + " value " + dayItem.getValue() + " " + dayItem.getMonth());
//                        Intent dayViewIntent = new Intent(getApplicationContext(), DayViewActivity.class);
//                        startActivity(dayViewIntent);

// /                ((LandingPageActivity) getActivity()).getSupportActionBar().setTitle("My Attendance");
//                FragmentTransaction fragmentTransaction = ((LandingPageActivity) getActivity()).getSupportFragmentManager().beginTransaction();
//                fragmentTransaction.add(R.id.fragmentContainer, new DayViewCalendarFragment());
//                fragmentTransaction.commit();
                    }

                    @Override
                    public void onEventSelected(CalendarEvent event) {

                    }
                });


                Calendar c = Calendar.getInstance();
                Log.d("MyCalendar", "Current time => " + c.getTime());

                SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
                String formattedDate = dateFormat.format(c.getTime());


                textViewCurrentDate.setText(formattedDate);
                agendaCalendarView.addEventRenderer(new DrawableEventRenderer());
            }
        }, 50);

    }

    private void mockList(List<CalendarEvent> eventList) {

        EventResponseData eventResponseData = Prefs.with(this).getObject(PrefsKeys.EVENT_RESPONSE_DATA, EventResponseData.class);

        if (eventResponseData != null) {
            for (Event event : eventResponseData.getData().getEvents()) {
                try {
                    Calendar startTime = ISO8601.toCalendar(event.getStartTime());
                    Calendar endTime = ISO8601.toCalendar(event.getEndTime());

                    int eventColor;

                    if (event.getEventTypeType().equalsIgnoreCase(Constants.PERSONAL)) {
                        eventColor = ContextCompat.getColor(this, R.color.personal_event_color);
                    } else if (event.getEventTypeType().equalsIgnoreCase(Constants.BATCH)) {
                        eventColor = ContextCompat.getColor(this, R.color.batch_event_color);
                    } else {
                        eventColor = ContextCompat.getColor(this, R.color.lecture_event_color);
                    }

                    String eventDescription = "";
                    if (event.getEventDescription()!=null)
                    {
                        eventDescription = event.getEventDescription();
                    }

                    if (event.getEventName()!=null)
                    {
                        BaseCalendarEvent event1 = new BaseCalendarEvent(event.getEventName(), "",eventDescription,
                                eventColor, startTime, endTime, true);

                        eventList.add(event1);
                    }

                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }

        }
//        Calendar startTime1 = Calendar.getInstance();
//        Calendar endTime1 = Calendar.getInstance();
//        endTime1.add(Calendar.MONTH, 1);
//        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
//                ContextCompat.getColor(this, R.color.personal_event_color), startTime1, endTime1, true);
//        eventList.add(event1);

//        Calendar startTime2 = Calendar.getInstance();
//        startTime2.add(Calendar.DAY_OF_YEAR, 1);
//        Calendar endTime2 = Calendar.getInstance();
//        endTime2.add(Calendar.DAY_OF_YEAR, 3);
//        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
//                ContextCompat.getColor(this, R.color.lecture_event_color), startTime2, endTime2, true);
//        eventList.add(event2);
//
////        // Example on how to provide your own layout
//        Calendar startTime3 = Calendar.getInstance();
//        Calendar endTime3 = Calendar.getInstance();
////        startTime3.set(Calendar.HOUR_OF_DAY, 14);
//        startTime3.set(Calendar.MINUTE, 0);
////        endTime3.set(Calendar.HOUR_OF_DAY, 15);
//        endTime3.set(Calendar.MINUTE, 1);
//        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
//                ContextCompat.getColor(this, R.color.blue_dark), startTime3, endTime3, false, R.drawable.ic_tmp_usr_placeholder);
//        eventList.add(event3);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.fabAdd:
                Intent intent = new Intent(this, AddEventActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }

    public void getEventsServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");


        String fromTime = ISO8601.fromCalendar(minDate);
        String toTime = ISO8601.fromCalendar(maxDate);

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetEvents(fromTime, toTime, new Callback<EventResponseData>() {
            @Override
            public void success(EventResponseData eventResponseData, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(eventResponseData));

                Prefs.with(MyCalendarFragment.this).save(PrefsKeys.EVENT_RESPONSE_DATA, eventResponseData);
                setUiData();
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();

        getEventsServerCall();
    }
}
