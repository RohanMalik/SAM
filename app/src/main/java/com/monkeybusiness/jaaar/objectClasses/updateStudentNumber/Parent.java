package com.monkeybusiness.jaaar.objectClasses.updateStudentNumber;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Parent {

    @SerializedName("contact_phone")
    @Expose
    private String contactPhone;

    /**
     * @return The contactPhone
     */
    public String getContactPhone() {
        return contactPhone;
    }

    /**
     * @param contactPhone The contact_phone
     */
    public void setContactPhone(String contactPhone) {
        this.contactPhone = contactPhone;
    }

}
