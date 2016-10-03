package com.monkeybusiness.jaaar.objectClasses.singleMarksUpdate;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamMark {

    @SerializedName("marks")
    @Expose
    private int marks;
    @SerializedName("remarks")
    @Expose
    private String remarks;

    /**
     * @return The marks
     */
    public int getMarks() {
        return marks;
    }

    /**
     * @param marks The marks
     */
    public void setMarks(int marks) {
        this.marks = marks;
    }

    /**
     * @return The remarks
     */
    public String getRemarks() {
        return remarks;
    }

    /**
     * @param remarks The remarks
     */
    public void setRemarks(String remarks) {
        this.remarks = remarks;
    }

}
