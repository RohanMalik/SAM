package com.monkeybusiness.jaaar.objectClasses.addHomeWorkObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Hws {

    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("hw_date")
    @Expose
    private String hwDate;
    @SerializedName("lecture_id")
    @Expose
    private List<Integer> lectureId;
    @SerializedName("hw_type")
    @Expose
    private String hwType;

    /**
     * @return The details
     */
    public String getDetails() {
        return details;
    }

    /**
     * @param details The details
     */
    public void setDetails(String details) {
        this.details = details;
    }

    /**
     * @return The hwDate
     */
    public String getHwDate() {
        return hwDate;
    }

    /**
     * @param hwDate The hw_date
     */
    public void setHwDate(String hwDate) {
        this.hwDate = hwDate;
    }

    /**
     * @return The lectureId
     */
    public List<Integer> getLectureId() {
        return lectureId;
    }

    /**
     * @param lectureId The lecture_id
     */
    public void setLectureId(List<Integer> lectureId) {
        this.lectureId = lectureId;
    }

    /**
     * @return The hwType
     */
    public String getHwType() {
        return hwType;
    }

    /**
     * @param hwType The hw_type
     */
    public void setHwType(String hwType) {
        this.hwType = hwType;
    }

}
