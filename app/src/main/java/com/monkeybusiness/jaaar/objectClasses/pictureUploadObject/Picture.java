package com.monkeybusiness.jaaar.objectClasses.pictureUploadObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Picture {

    @SerializedName("extention")
    @Expose
    private String extention;
    @SerializedName("contentType")
    @Expose
    private String contentType;
    @SerializedName("file")
    @Expose
    private String file;

    /**
     * @return The extention
     */
    public String getExtention() {
        return extention;
    }

    /**
     * @param extention The extention
     */
    public void setExtention(String extention) {
        this.extention = extention;
    }

    /**
     * @return The contentType
     */
    public String getContentType() {
        return contentType;
    }

    /**
     * @param contentType The contentType
     */
    public void setContentType(String contentType) {
        this.contentType = contentType;
    }

    /**
     * @return The file
     */
    public String getFile() {
        return file;
    }

    /**
     * @param file The file
     */
    public void setFile(String file) {
        this.file = file;
    }

}
