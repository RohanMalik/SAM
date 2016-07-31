
package com.monkeybusiness.jaaar.objectClasses.addEventObject;

import android.content.Intent;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Events {

    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_description")
    @Expose
    private String eventDescription;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("event_type_type")
    @Expose
    private String eventTypeType;
    @SerializedName("event_type_id")
    @Expose
    private List<Integer> eventTypeId;

    /**
     * 
     * @return
     *     The eventName
     */
    public String getEventName() {
        return eventName;
    }

    /**
     * 
     * @param eventName
     *     The event_name
     */
    public void setEventName(String eventName) {
        this.eventName = eventName;
    }

    /**
     * 
     * @return
     *     The startTime
     */
    public String getStartTime() {
        return startTime;
    }

    /**
     * 
     * @param startTime
     *     The start_time
     */
    public void setStartTime(String startTime) {
        this.startTime = startTime;
    }

    /**
     * 
     * @return
     *     The endTime
     */
    public String getEndTime() {
        return endTime;
    }

    /**
     * 
     * @param endTime
     *     The end_time
     */
    public void setEndTime(String endTime) {
        this.endTime = endTime;
    }

    /**
     * 
     * @return
     *     The eventTypeType
     */
    public String getEventTypeType() {
        return eventTypeType;
    }

    /**
     * 
     * @param eventTypeType
     *     The event_type_type
     */
    public void setEventTypeType(String eventTypeType) {
        this.eventTypeType = eventTypeType;
    }

    public List<Integer> getEventTypeId() {
        return eventTypeId;
    }

    public void setEventTypeId(List<Integer> eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    public String getEventDescription() {
        return eventDescription;
    }

    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }
}
