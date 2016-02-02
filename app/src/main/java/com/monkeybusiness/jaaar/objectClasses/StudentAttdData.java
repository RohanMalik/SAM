package com.monkeybusiness.jaaar.objectClasses;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 1/2/16.
 */
public class StudentAttdData {

    String sName;
    String sClass;
    String sRollNo;
    String sCurrentAttd;
    List<String> recentRecord = new ArrayList<>();

    public StudentAttdData(String sName, String sClass, String sRollNo, String sCurrentAttd, List<String> recentRecord)
    {
        this.sName = sName;
        this.sClass = sClass;
        this.sRollNo = sRollNo;
        this.sCurrentAttd = sCurrentAttd;
        this.recentRecord = recentRecord;
    }

    public String getsClass() {
        return sClass;
    }

    public void setsClass(String sClass) {
        this.sClass = sClass;
    }

    public String getsName() {
        return sName;
    }

    public void setsName(String sName) {
        this.sName = sName;
    }

    public String getsRollNo() {
        return sRollNo;
    }

    public void setsRollNo(String sRollNo) {
        this.sRollNo = sRollNo;
    }

    public String getsCurrentAttd() {
        return sCurrentAttd;
    }

    public void setsCurrentAttd(String sCurrentAttd) {
        this.sCurrentAttd = sCurrentAttd;
    }

    public List<String> getRecentRecord() {
        return recentRecord;
    }

    public void setRecentRecord(List<String> recentRecord) {
        this.recentRecord = recentRecord;
    }
}
