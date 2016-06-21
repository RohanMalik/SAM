
package com.monkeybusiness.jaaar.objectClasses.addAttdRequestObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("student_id")
    @Expose
    private int studentId;
    @SerializedName("status")
    @Expose
    private String status;

    /**
     * 
     * @return
     *     The studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * 
     * @param studentId
     *     The student_id
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
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

}
