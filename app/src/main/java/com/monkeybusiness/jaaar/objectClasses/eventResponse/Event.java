
package com.monkeybusiness.jaaar.objectClasses.eventResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Event {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("event_name")
    @Expose
    private String eventName;
    @SerializedName("event_description")
    @Expose
    private String eventDescription;
    @SerializedName("event_type_id")
    @Expose
    private int eventTypeId;
    @SerializedName("user_login_id")
    @Expose
    private int userLoginId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("event_type_type")
    @Expose
    private String eventTypeType;
    @SerializedName("start_time")
    @Expose
    private String startTime;
    @SerializedName("end_time")
    @Expose
    private String endTime;
    @SerializedName("school_id")
    @Expose
    private int schoolId;
    @SerializedName("event_group_id")
    @Expose
    private int eventGroupId;
    @SerializedName("event_type")
    @Expose
    private EventType eventType;

    /**
     * 
     * @return
     *     The id
     */
    public int getId() {
        return id;
    }

    /**
     * 
     * @param id
     *     The id
     */
    public void setId(int id) {
        this.id = id;
    }

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
     *     The eventDescription
     */
    public String getEventDescription() {
        return eventDescription;
    }

    /**
     * 
     * @param eventDescription
     *     The event_description
     */
    public void setEventDescription(String eventDescription) {
        this.eventDescription = eventDescription;
    }

    /**
     * 
     * @return
     *     The eventTypeId
     */
    public int getEventTypeId() {
        return eventTypeId;
    }

    /**
     * 
     * @param eventTypeId
     *     The event_type_id
     */
    public void setEventTypeId(int eventTypeId) {
        this.eventTypeId = eventTypeId;
    }

    /**
     * 
     * @return
     *     The userLoginId
     */
    public int getUserLoginId() {
        return userLoginId;
    }

    /**
     * 
     * @param userLoginId
     *     The user_login_id
     */
    public void setUserLoginId(int userLoginId) {
        this.userLoginId = userLoginId;
    }

    /**
     * 
     * @return
     *     The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * 
     * @param createdAt
     *     The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * 
     * @return
     *     The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * 
     * @param updatedAt
     *     The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
     *     The schoolId
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     * 
     * @param schoolId
     *     The school_id
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
    }

    /**
     * 
     * @return
     *     The eventGroupId
     */
    public int getEventGroupId() {
        return eventGroupId;
    }

    /**
     * 
     * @param eventGroupId
     *     The event_group_id
     */
    public void setEventGroupId(int eventGroupId) {
        this.eventGroupId = eventGroupId;
    }

    /**
     * 
     * @return
     *     The eventType
     */
    public EventType getEventType() {
        return eventType;
    }

    /**
     * 
     * @param eventType
     *     The event_type
     */
    public void setEventType(EventType eventType) {
        this.eventType = eventType;
    }

}
