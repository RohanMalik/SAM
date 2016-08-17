package com.monkeybusiness.jaaar.objectClasses.examStudentMarks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("student_name")
    @Expose
    private String studentName;
    @SerializedName("rollno")
    @Expose
    private Integer rollno;
    @SerializedName("picture")
    @Expose
    private Picture picture;

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
     * @return The studentName
     */
    public String getStudentName() {
        return studentName;
    }

    /**
     * @param studentName The student_name
     */
    public void setStudentName(String studentName) {
        this.studentName = studentName;
    }

    /**
     * @return The rollno
     */
    public Integer getRollno() {
        return rollno;
    }

    /**
     * @param rollno The rollno
     */
    public void setRollno(Integer rollno) {
        this.rollno = rollno;
    }

    /**
     * @return The picture
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

}
