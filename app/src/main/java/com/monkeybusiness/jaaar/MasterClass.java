package com.monkeybusiness.jaaar;

import android.app.Application;
import android.util.Log;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 1/2/16.
 */
public class MasterClass extends Application {

    public static MasterClass masterClass;

    public ArrayList<StudentAttdData> studentAttdDatas = new ArrayList<>();

    public AttendanceFragment attendanceFragment;

    List<SingleIdDetail> singleIdDetails;
    List<StudentsInfo> studentsInfos;
    String classAlias;

    public static MasterClass getInstance()
    {
        if (masterClass==null)
        {
            Log.d("MasterClass","creating Instance");
            masterClass =  new MasterClass();
        }
        Log.d("MasterClass","already created Instance");
        return masterClass;
    }

    public ArrayList<StudentAttdData> getStudentAttdDatas() {
        return studentAttdDatas;
    }

    public void setStudentAttdDatas(ArrayList<StudentAttdData> studentAttdDatas) {
        this.studentAttdDatas = studentAttdDatas;
    }

    public AttendanceFragment getAttendanceFragment() {
        return attendanceFragment;
    }

    public void setAttendanceFragment(AttendanceFragment attendanceFragment) {
        this.attendanceFragment = attendanceFragment;
    }

    public List<SingleIdDetail> getSingleIdDetails() {
        return singleIdDetails;
    }

    public void setSingleIdDetails(List<SingleIdDetail> singleIdDetails) {
        this.singleIdDetails = singleIdDetails;
    }

    public List<StudentsInfo> getStudentsInfos() {
        return studentsInfos;
    }

    public void setStudentsInfos(List<StudentsInfo> studentsInfos) {
        this.studentsInfos = studentsInfos;
    }

    public String getClassAlias() {
        return classAlias;
    }

    public void setClassAlias(String classAlias) {
        this.classAlias = classAlias;
    }
}
