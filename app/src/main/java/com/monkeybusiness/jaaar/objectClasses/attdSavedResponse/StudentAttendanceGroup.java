
package com.monkeybusiness.jaaar.objectClasses.attdSavedResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentAttendanceGroup {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("batch_id")
    @Expose
    private int batchId;
    @SerializedName("attendance_date")
    @Expose
    private String attendanceDate;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;

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
     *     The batchId
     */
    public int getBatchId() {
        return batchId;
    }

    /**
     * 
     * @param batchId
     *     The batch_id
     */
    public void setBatchId(int batchId) {
        this.batchId = batchId;
    }

    /**
     * 
     * @return
     *     The attendanceDate
     */
    public String getAttendanceDate() {
        return attendanceDate;
    }

    /**
     * 
     * @param attendanceDate
     *     The attendance_date
     */
    public void setAttendanceDate(String attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    /**
     * 
     * @return
     *     The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * 
     * @param status
     *     The status
     */
    public void setStatus(String status) {
        this.status = status;
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

}
