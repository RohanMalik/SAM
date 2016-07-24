package com.monkeybusiness.jaaar.objectClasses.pictureUploadObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class PictureUploadObject {

    @SerializedName("picture")
    @Expose
    private Picture picture;

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

}
