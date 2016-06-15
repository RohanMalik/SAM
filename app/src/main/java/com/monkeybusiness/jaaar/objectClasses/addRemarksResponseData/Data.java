
package com.monkeybusiness.jaaar.objectClasses.addRemarksResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("errors")
    @Expose
    private Object errors;

    /**
     * 
     * @return
     *     The errors
     */
    public Object getErrors() {
        return errors;
    }

    /**
     * 
     * @param errors
     *     The errors
     */
    public void setErrors(Object errors) {
        this.errors = errors;
    }

}
