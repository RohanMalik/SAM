package com.monkeybusiness.jaaar;

import android.app.Application;
import android.util.Log;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;

import java.util.ArrayList;

/**
 * Created by rakesh on 1/2/16.
 */
public class MasterClass extends Application {

    public static MasterClass masterClass;

    public ArrayList<StudentAttdData> studentAttdDatas = new ArrayList<>();

    public AttendanceFragment attendanceFragment;

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
}
