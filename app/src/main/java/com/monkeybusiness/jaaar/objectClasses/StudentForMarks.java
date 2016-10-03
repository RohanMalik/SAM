package com.monkeybusiness.jaaar.objectClasses;

/**
 * Created by rakesh on 3/10/16.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentForMarks {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("marks")
    @Expose
    private String marks;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    /**
     * @return The studentId
     */
    public Integer getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(Integer studentId) {
        this.studentId = studentId;
    }

    /**
     * @return The marks
     */
    public String getMarks() {
        return marks;
    }

    /**
     * @param marks The marks
     */
    public void setMarks(String marks) {
        this.marks = marks;
    }

    /**
     * @return The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
