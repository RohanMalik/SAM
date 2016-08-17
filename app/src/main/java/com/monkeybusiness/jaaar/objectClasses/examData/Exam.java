package com.monkeybusiness.jaaar.objectClasses.examData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Exam {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("exam_name")
    @Expose
    private String examName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("exam_date")
    @Expose
    private String examDate;
    @SerializedName("subject_id")
    @Expose
    private int subjectId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("exam_group_id")
    @Expose
    private int examGroupId;
    @SerializedName("max_marks")
    @Expose
    private int maxMarks;
    @SerializedName("min_marks")
    @Expose
    private int minMarks;
    @SerializedName("duration_minutes")
    @Expose
    private Object durationMinutes;
    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("subject")
    @Expose
    private Subject subject;
    @SerializedName("exam_batch_map")
    @Expose
    private List<Object> examBatchMap = new ArrayList<Object>();

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
     * @return The examName
     */
    public String getExamName() {
        return examName;
    }

    /**
     * @param examName The exam_name
     */
    public void setExamName(String examName) {
        this.examName = examName;
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
     * @return The examDate
     */
    public String getExamDate() {
        return examDate;
    }

    /**
     * @param examDate The exam_date
     */
    public void setExamDate(String examDate) {
        this.examDate = examDate;
    }

    /**
     * @return The subjectId
     */
    public int getSubjectId() {
        return subjectId;
    }

    /**
     * @param subjectId The subject_id
     */
    public void setSubjectId(int subjectId) {
        this.subjectId = subjectId;
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
     * @return The examGroupId
     */
    public int getExamGroupId() {
        return examGroupId;
    }

    /**
     * @param examGroupId The exam_group_id
     */
    public void setExamGroupId(int examGroupId) {
        this.examGroupId = examGroupId;
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
     * @return The durationMinutes
     */
    public Object getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * @param durationMinutes The duration_minutes
     */
    public void setDurationMinutes(Object durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

    /**
     * @return The status
     */
    public String getStatus() {
        return status;
    }

    /**
     * @param status The status
     */
    public void setStatus(String status) {
        this.status = status;
    }

    /**
     * @return The subject
     */
    public Subject getSubject() {
        return subject;
    }

    /**
     * @param subject The subject
     */
    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    /**
     * @return The examBatchMap
     */
    public List<Object> getExamBatchMap() {
        return examBatchMap;
    }

    /**
     * @param examBatchMap The exam_batch_map
     */
    public void setExamBatchMap(List<Object> examBatchMap) {
        this.examBatchMap = examBatchMap;
    }

}
