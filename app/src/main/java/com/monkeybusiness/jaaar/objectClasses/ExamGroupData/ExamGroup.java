package com.monkeybusiness.jaaar.objectClasses.ExamGroupData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamGroup {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("exam_group_name")
    @Expose
    private String examGroupName;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("school_id")
    @Expose
    private Integer schoolId;

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return The examGroupName
     */
    public String getExamGroupName() {
        return examGroupName;
    }

    /**
     * @param examGroupName The exam_group_name
     */
    public void setExamGroupName(String examGroupName) {
        this.examGroupName = examGroupName;
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
     * @return The schoolId
     */
    public Integer getSchoolId() {
        return schoolId;
    }

    /**
     * @param schoolId The school_id
     */
    public void setSchoolId(Integer schoolId) {
        this.schoolId = schoolId;
    }

}
