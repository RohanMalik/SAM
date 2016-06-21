
package com.monkeybusiness.jaaar.objectClasses.attdSavedResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("student_attendance_group")
    @Expose
    private StudentAttendanceGroup studentAttendanceGroup;

    /**
     * 
     * @return
     *     The studentAttendanceGroup
     */
    public StudentAttendanceGroup getStudentAttendanceGroup() {
        return studentAttendanceGroup;
    }

    /**
     * 
     * @param studentAttendanceGroup
     *     The student_attendance_group
     */
    public void setStudentAttendanceGroup(StudentAttendanceGroup studentAttendanceGroup) {
        this.studentAttendanceGroup = studentAttendanceGroup;
    }

}
