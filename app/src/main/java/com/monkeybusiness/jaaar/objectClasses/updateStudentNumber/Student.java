package com.monkeybusiness.jaaar.objectClasses.updateStudentNumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Student {

    @SerializedName("id")
    @Expose
    private Integer id;
    @SerializedName("parent")
    @Expose
    private Parent parent;

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

    /**
     * @return The parent
     */
    public Parent getParent() {
        return parent;
    }

    /**
     * @param parent The parent
     */
    public void setParent(Parent parent) {
        this.parent = parent;
    }

}
