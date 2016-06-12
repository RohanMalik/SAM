package com.monkeybusiness.jaaar.objectClasses.lectureResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("lectures")
    @Expose
    private List<Lecture> lectures = new ArrayList<Lecture>();

    /**
     * @return The lectures
     */
    public List<Lecture> getLectures() {
        return lectures;
    }

    /**
     * @param lectures The lectures
     */
    public void setLectures(List<Lecture> lectures) {
        this.lectures = lectures;
    }

}
