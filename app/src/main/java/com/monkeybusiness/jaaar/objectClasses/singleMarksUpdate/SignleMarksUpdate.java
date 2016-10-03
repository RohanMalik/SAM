package com.monkeybusiness.jaaar.objectClasses.singleMarksUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class SignleMarksUpdate {

    @SerializedName("exam_mark")
    @Expose
    private ExamMark examMark;

    /**
     * @return The examMark
     */
    public ExamMark getExamMark() {
        return examMark;
    }

    /**
     * @param examMark The exam_mark
     */
    public void setExamMark(ExamMark examMark) {
        this.examMark = examMark;
    }

}
