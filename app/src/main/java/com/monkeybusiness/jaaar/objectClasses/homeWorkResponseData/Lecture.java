package com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lecture {

    @SerializedName("id")
    @Expose
    private int id;
    @SerializedName("lecture_name")
    @Expose
    private String lectureName;

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
     * @return The lectureName
     */
    public String getLectureName() {
        return lectureName;
    }

    /**
     * @param lectureName The lecture_name
     */
    public void setLectureName(String lectureName) {
        this.lectureName = lectureName;
    }

}
