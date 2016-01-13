package com.monkeybusiness.jaaar.Fragment;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.content.ContextCompat;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.tibolte.agendacalendarview.AgendaCalendarView;
import com.github.tibolte.agendacalendarview.CalendarPickerController;
import com.github.tibolte.agendacalendarview.models.BaseCalendarEvent;
import com.github.tibolte.agendacalendarview.models.CalendarEvent;
import com.github.tibolte.agendacalendarview.models.DayItem;
import com.monkeybusiness.jaaar.Activity.DayViewActivity;
import com.monkeybusiness.jaaar.Activity.LandingPageActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.DrawableCalendarEvent;
import com.monkeybusiness.jaaar.utils.DrawableEventRenderer;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

/**
 * Created by rakesh on 13/1/16.
 */
public class MyCalendarFragment extends Fragment{

    View rootView;
    AgendaCalendarView agendaCalendarView;
    TextView textViewCurrentDate;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_calendar,container,false);

        initialization();
        return rootView;
    }

    public void initialization()
    {
        agendaCalendarView = (AgendaCalendarView) rootView.findViewById(R.id.agenda_calendar_view);
        textViewCurrentDate = (TextView) rootView.findViewById(R.id.textViewCurrentDate);

        Calendar minDate = Calendar.getInstance();
        Calendar maxDate = Calendar.getInstance();

        minDate.add(Calendar.MONTH, -2);
        minDate.set(Calendar.DAY_OF_MONTH, 1);
        maxDate.add(Calendar.YEAR, 1);

        List<CalendarEvent> eventList = new ArrayList<>();
        mockList(eventList);

        agendaCalendarView.init(eventList, minDate, maxDate, Locale.getDefault(), new CalendarPickerController() {
            @Override
            public void onDaySelected(DayItem dayItem) {

                Log.d("MyCalendar", "day " + dayItem.getDate() + " value " + dayItem.getValue() + " " + dayItem.getMonth());

                Intent dayViewIntent = new Intent(getActivity(), DayViewActivity.class);
                startActivity(dayViewIntent);
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
        Log.d("MyCalendar","Current time => " + c.getTime());

        SimpleDateFormat dateFormat = new SimpleDateFormat("dd MMM yyyy");
        String formattedDate = dateFormat.format(c.getTime());


        textViewCurrentDate.setText(formattedDate);
        agendaCalendarView.addEventRenderer(new DrawableEventRenderer());
    }

    private void mockList(List<CalendarEvent> eventList) {
        Calendar startTime1 = Calendar.getInstance();
        Calendar endTime1 = Calendar.getInstance();
//        endTime1.add(Calendar.MONTH, 1);
        BaseCalendarEvent event1 = new BaseCalendarEvent("Thibault travels in Iceland", "A wonderful journey!", "Iceland",
                ContextCompat.getColor(getActivity(), R.color.orange_dark), startTime1, endTime1, true);
        eventList.add(event1);

        Calendar startTime2 = Calendar.getInstance();
        startTime2.add(Calendar.DAY_OF_YEAR, 1);
        Calendar endTime2 = Calendar.getInstance();
        endTime2.add(Calendar.DAY_OF_YEAR, 3);
        BaseCalendarEvent event2 = new BaseCalendarEvent("Visit to Dalvík", "A beautiful small town", "Dalvík",
                ContextCompat.getColor(getActivity(), R.color.yellow), startTime2, endTime2, true);
        eventList.add(event2);

        // Example on how to provide your own layout
        Calendar startTime3 = Calendar.getInstance();
        Calendar endTime3 = Calendar.getInstance();
//        startTime3.set(Calendar.HOUR_OF_DAY, 14);
        startTime3.set(Calendar.MINUTE, 0);
//        endTime3.set(Calendar.HOUR_OF_DAY, 15);
        endTime3.set(Calendar.MINUTE, 1);
        DrawableCalendarEvent event3 = new DrawableCalendarEvent("Visit of Harpa", "", "Dalvík",
                ContextCompat.getColor(getActivity(), R.color.blue_dark), startTime3, endTime3, false, R.drawable.common_ic_googleplayservices);
        eventList.add(event3);
    }
}
