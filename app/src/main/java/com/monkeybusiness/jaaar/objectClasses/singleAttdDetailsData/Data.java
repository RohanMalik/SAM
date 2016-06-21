
package com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("batch_info")
    @Expose
    private BatchInfo batchInfo;
    @SerializedName("students_info")
    @Expose
    private List<StudentsInfo> studentsInfo = new ArrayList<StudentsInfo>();
    @SerializedName("student_attendance_group")
    @Expose
    private Object studentAttendanceGroup;
    @SerializedName("count")
    @Expose
    private Object count;

    /**
     * 
     * @return
     *     The batchInfo
     */
    public BatchInfo getBatchInfo() {
        return batchInfo;
    }

    /**
     * 
     * @param batchInfo
     *     The batch_info
     */
    public void setBatchInfo(BatchInfo batchInfo) {
        this.batchInfo = batchInfo;
    }

    /**
     * 
     * @return
     *     The studentsInfo
     */
    public List<StudentsInfo> getStudentsInfo() {
        return studentsInfo;
    }

    /**
     * 
     * @param studentsInfo
     *     The students_info
     */
    public void setStudentsInfo(List<StudentsInfo> studentsInfo) {
        this.studentsInfo = studentsInfo;
    }

    /**
     * 
     * @return
     *     The studentAttendanceGroup
     */
    public Object getStudentAttendanceGroup() {
        return studentAttendanceGroup;
    }

    /**
     * 
     * @param studentAttendanceGroup
     *     The student_attendance_group
     */
    public void setStudentAttendanceGroup(Object studentAttendanceGroup) {
        this.studentAttendanceGroup = studentAttendanceGroup;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Object getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Object count) {
        this.count = count;
    }

}
