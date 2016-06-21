
package com.monkeybusiness.jaaar.objectClasses.addAttdRequestObject;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AttdPostObjectData {

    @SerializedName("student_attendance")
    @Expose
    private StudentAttendance studentAttendance;

    /**
     * 
     * @return
     *     The studentAttendance
     */
    public StudentAttendance getStudentAttendance() {
        return studentAttendance;
    }

    /**
     * 
     * @param studentAttendance
     *     The student_attendance
     */
    public void setStudentAttendance(StudentAttendance studentAttendance) {
        this.studentAttendance = studentAttendance;
    }



}
