
package com.monkeybusiness.jaaar.objectClasses.testMarksResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("rollno")
    @Expose
    private int rollno;
    @SerializedName("batch_id")
    @Expose
    private int batchId;

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
     *     The studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * 
     * @param studentName
     *     The student_name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * 
     * @return
     *     The rollno
     */
    public int getRollno() {
        return rollno;
    }

    /**
     * 
     * @param rollno
     *     The rollno
     */
    public void setRollno(int rollno) {
        this.rollno = rollno;
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

}
