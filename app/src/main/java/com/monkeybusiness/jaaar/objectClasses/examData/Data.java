package com.monkeybusiness.jaaar.objectClasses.examData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("exams")
    @Expose
    private List<Exam> exams = new ArrayList<Exam>();

    /**
     * @return The exams
     */
    public List<Exam> getExams() {
        return exams;
    }

    /**
     * @param exams The exams
     */
    public void setExams(List<Exam> exams) {
        this.exams = exams;
    }

}
