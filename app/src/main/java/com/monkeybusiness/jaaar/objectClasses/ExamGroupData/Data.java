package com.monkeybusiness.jaaar.objectClasses.ExamGroupData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("exam_groups")
    @Expose
    private List<ExamGroup> examGroups = new ArrayList<ExamGroup>();

    /**
     * @return The examGroups
     */
    public List<ExamGroup> getExamGroups() {
        return examGroups;
    }

    /**
     * @param examGroups The exam_groups
     */
    public void setExamGroups(List<ExamGroup> examGroups) {
        this.examGroups = examGroups;
    }

}
