package com.monkeybusiness.jaaar.objectClasses.examStudentMarks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("exam_marks")
    @Expose
    private List<ExamMark> examMarks = new ArrayList<ExamMark>();

    /**
     * @return The examMarks
     */
    public List<ExamMark> getExamMarks() {
        return examMarks;
    }

    /**
     * @param examMarks The exam_marks
     */
    public void setExamMarks(List<ExamMark> examMarks) {
        this.examMarks = examMarks;
    }

}
