package com.monkeybusiness.jaaar.objectClasses.addMarksData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("student_id")
    @Expose
    private Integer studentId;
    @SerializedName("marks")
    @Expose
    private Integer marks;
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
    public Integer getMarks() {
        return marks;
    }

    /**
     * @param marks The marks
     */
    public void setMarks(Integer marks) {
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
