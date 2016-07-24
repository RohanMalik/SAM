package com.monkeybusiness.jaaar.objectClasses.imagePutObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ImageStudentPutObject {

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
