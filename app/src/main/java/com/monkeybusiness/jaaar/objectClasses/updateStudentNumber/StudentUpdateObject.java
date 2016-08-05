package com.monkeybusiness.jaaar.objectClasses.updateStudentNumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentUpdateObject {

    @SerializedName("student")
    @Expose
    private Student student;

    /**
     * @return The student
     */
    public Student getStudent() {
        return student;
    }

    /**
     * @param student The student
     */
    public void setStudent(Student student) {
        this.student = student;
    }

}
