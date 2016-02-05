package com.monkeybusiness.jaaar.agendacalendarview;


import com.monkeybusiness.jaaar.agendacalendarview.models.CalendarEvent;
import com.monkeybusiness.jaaar.agendacalendarview.models.DayItem;

public interface CalendarPickerController {
    void onDaySelected(DayItem dayItem);

    void onEventSelected(CalendarEvent event);
}
