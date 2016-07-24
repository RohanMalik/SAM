package com.monkeybusiness.jaaar.objectClasses.imagePutObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("picture")
    @Expose
    private Picture picture;
    @SerializedName("id")
    @Expose
    private Integer id;

    /**
     * @return The picture
     */
    public Picture getPicture() {
        return picture;
    }

    /**
     * @param picture The picture
     */
    public void setPicture(Picture picture) {
        this.picture = picture;
    }

    /**
     * @return The id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id The id
     */
    public void setId(Integer id) {
        this.id = id;
    }

}
