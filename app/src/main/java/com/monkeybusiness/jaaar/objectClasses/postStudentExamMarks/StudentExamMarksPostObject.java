package com.monkeybusiness.jaaar.objectClasses.postStudentExamMarks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class StudentExamMarksPostObject {

    @SerializedName("exam_marks")
    @Expose
    private ExamMarks examMarks;

    /**
     * @return The examMarks
     */
    public ExamMarks getExamMarks() {
        return examMarks;
    }

    /**
     * @param examMarks The exam_marks
     */
    public void setExamMarks(ExamMarks examMarks) {
        this.examMarks = examMarks;
    }

}
