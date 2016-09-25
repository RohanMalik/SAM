package com.monkeybusiness.jaaar.objectClasses.lectureResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Lecture {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("lecture_name")
    @Expose
    private String lectureName;
    @SerializedName("teacher_id")
    @Expose
    private int teacherId;
    @SerializedName("subject_id")
    @Expose
    private int subjectId;
    @SerializedName("school_id")
    @Expose
    private int schoolId;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("grade_id")
    @Expose
    private int gradeId;
    @SerializedName("subject")
    @Expose
    private Subject subject;
    @SerializedName("batches")
    @Expose
    private List<Batch> batches= new ArrayList<Batch>();


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
     * @return The lectureName
     */
    public String getLectureName() {
        return lectureName;
    }

    /**
     * @param lectureName The lecture_name
     */
    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
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
     * @return The gradeId
     */
    public int getGradeId() {
        return gradeId;
    }

    /**
     * @param gradeId The grade_id
     */
    public void setGradeId(int gradeId) {
        this.gradeId = gradeId;
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

    public List<Batch> getBatches() {
        return batches;
    }

    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }
}
