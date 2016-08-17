package com.monkeybusiness.jaaar.objectClasses.postStudentExamMarks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;
import com.monkeybusiness.jaaar.objectClasses.addMarksData.Student;

import java.util.ArrayList;
import java.util.List;

public class ExamMarks {

    @SerializedName("exam_marks_data")
    @Expose
    private List<Student> students = new ArrayList<Student>();
    @SerializedName("batch_id")
    @Expose
    private Integer batchId;

    /**
     * @return The students
     */
    public List<Student> getStudents() {
        return students;
    }

    /**
     * @param students The exam_marks_data
     */
    public void setStudents(List<Student> students) {
        this.students = students;
    }

    /**
     * @return The batchId
     */
    public Integer getBatchId() {
        return batchId;
    }

    /**
     * @param batchId The batch_id
     */
    public void setBatchId(Integer batchId) {
        this.batchId = batchId;
    }

}
