package com.monkeybusiness.jaaar.objectClasses.examStudentMarks;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class ExamMark {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("exam_id")
    @Expose
    private int examId;
    @SerializedName("student_id")
    @Expose
    private int studentId;
    @SerializedName("marks")
    @Expose
    private int marks;
    @SerializedName("created_at")
    @Expose
    private String createdAt;
    @SerializedName("updated_at")
    @Expose
    private String updatedAt;
    @SerializedName("remarks")
    @Expose
    private String remarks;
    @SerializedName("student")
    @Expose
    private Student student;

    /**
     * @return The id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return The examId
     */
    public int getExamId() {
        return examId;
    }

    /**
     * @param examId The exam_id
     */
    public void setExamId(int examId) {
        this.examId = examId;
    }

    /**
     * @return The studentId
     */
    public int getStudentId() {
        return studentId;
    }

    /**
     * @param studentId The student_id
     */
    public void setStudentId(int studentId) {
        this.studentId = studentId;
    }

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
     * @return The createdAt
     */
    public String getCreatedAt() {
        return createdAt;
    }

    /**
     * @param createdAt The created_at
     */
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    /**
     * @return The updatedAt
     */
    public String getUpdatedAt() {
        return updatedAt;
    }

    /**
     * @param updatedAt The updated_at
     */
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
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
