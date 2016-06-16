package com.monkeybusiness.jaaar.objectClasses.testListResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("test_date")
    @Expose
    private String testDate;
    @SerializedName("lecture_id")
    @Expose
    private int lectureId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("max_marks")
    @Expose
    private int maxMarks;
    @SerializedName("min_marks")
    @Expose
    private int minMarks;
    @SerializedName("test_status")
    @Expose
    private String testStatus;
    @SerializedName("duration_minutes")
    @Expose
    private int durationMinutes;
    @SerializedName("school_id")
    @Expose
    private int schoolId;
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
     * @return The testName
     */
    public String getTestName() {
        return testName;
    }

    /**
     * @param testName The test_name
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * @return The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * @param description The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * @return The testDate
     */
    public String getTestDate() {
        return testDate;
    }

    /**
     * @param testDate The test_date
     */
    public void setTestDate(String testDate) {
        this.testDate = testDate;
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
     * @return The maxMarks
     */
    public int getMaxMarks() {
        return maxMarks;
    }

    /**
     * @param maxMarks The max_marks
     */
    public void setMaxMarks(int maxMarks) {
        this.maxMarks = maxMarks;
    }

    /**
     * @return The minMarks
     */
    public int getMinMarks() {
        return minMarks;
    }

    /**
     * @param minMarks The min_marks
     */
    public void setMinMarks(int minMarks) {
        this.minMarks = minMarks;
    }

    /**
     * @return The testStatus
     */
    public String getTestStatus() {
        return testStatus;
    }

    /**
     * @param testStatus The test_status
     */
    public void setTestStatus(String testStatus) {
        this.testStatus = testStatus;
    }

    /**
     * @return The durationMinutes
     */
    public int getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * @param durationMinutes The duration_minutes
     */
    public void setDurationMinutes(int durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * @return The schoolId
     */
    public int getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId The school_id
     */
    public void setSchoolId(int schoolId) {
        this.schoolId = schoolId;
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
