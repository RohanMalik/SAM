
package com.monkeybusiness.jaaar.objectClasses.attendanceResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Datum {

    @SerializedName("date")
    @Expose
    private String date;
    @SerializedName("present")
    @Expose
    private int present;
    @SerializedName("absent")
    @Expose
    private int absent;
    @SerializedName("attendance_status")
    @Expose
    private Boolean attendanceStatus;

    /**
     * 
     * @return
     *     The date
     */
    public String getDate() {
        return date;
    }

    /**
     * 
     * @param date
     *     The date
     */
    public void setDate(String date) {
        this.date = date;
    }

    /**
     * 
     * @return
     *     The present
     */
    public int getPresent() {
        return present;
    }

    /**
     * 
     * @param present
     *     The present
     */
    public void setPresent(int present) {
        this.present = present;
    }

    /**
     * 
     * @return
     *     The absent
     */
    public int getAbsent() {
        return absent;
    }

    /**
     * 
     * @param absent
     *     The absent
     */
    public void setAbsent(int absent) {
        this.absent = absent;
    }

    /**
     * 
     * @return
     *     The attendanceStatus
     */
    public Boolean getAttendanceStatus() {
        return attendanceStatus;
    }

    /**
     * 
     * @param attendanceStatus
     *     The attendance_status
     */
    public void setAttendanceStatus(Boolean attendanceStatus) {
        this.attendanceStatus = attendanceStatus;
    }

}
