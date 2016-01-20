package com.monkeybusiness.jaaar.objectClasses;

/**
 * Created by rakesh on 20/1/16.
 */
public class TestData
{
    String date;
    String time;
    String subjectName;

    public TestData(String date,String time,String subjectName)
    {
        this.date = date;
        this.time = time;
        this.subjectName = subjectName;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getSubjectName() {
        return subjectName;
    }

    public void setSubjectName(String subjectName) {
        this.subjectName = subjectName;
    }
}
