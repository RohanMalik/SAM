package com.monkeybusiness.jaaar.objectClasses.addMarksData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class TestMarks {

    @SerializedName("status")
    @Expose
    private String status;
    @SerializedName("students")
    @Expose
    private List<Student> students = new ArrayList<Student>();

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
     * @return The students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students The students
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

}
