package com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Hw {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("details")
    @Expose
    private String details;
    @SerializedName("hw_type")
    @Expose
    private String hwType;
    @SerializedName("lecture_id")
    @Expose
    private int lectureId;
    @SerializedName("teacher_id")
    @Expose
    private int teacherId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("hw_date")
    @Expose
    private String hwDate;
    @SerializedName("teacher")
    @Expose
    private Teacher teacher;
    @SerializedName("lecture")
    @Expose
    private Lecture lecture;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

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

    /**
     * @return The lectureId
     */
    public int getLectureId() {
        return lectureId;
    }

    /**
     * @param lectureId The lecture_id
     */
    public void setLectureId(int lectureId) {
        this.lectureId = lectureId;
    }

    /**
     * @return The teacherId
     */
    public int getTeacherId() {
        return teacherId;
    }

    /**
     * @param teacherId The teacher_id
     */
    public void setTeacherId(int teacherId) {
        this.teacherId = teacherId;
    }

    /**
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
     * @return The teacher
     */
    public Teacher getTeacher() {
        return teacher;
    }

    /**
     * @param teacher The teacher
     */
    public void setTeacher(Teacher teacher) {
        this.teacher = teacher;
    }

    /**
     * @return The lecture
     */
    public Lecture getLecture() {
        return lecture;
    }

    /**
     * @param lecture The lecture
     */
    public void setLecture(Lecture lecture) {
        this.lecture = lecture;
    }

}
