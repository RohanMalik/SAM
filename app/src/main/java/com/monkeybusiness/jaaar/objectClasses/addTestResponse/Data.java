
package com.monkeybusiness.jaaar.objectClasses.addTestResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("test")
    @Expose
    private Test test;

    /**
     * 
     * @return
     *     The test
     */
    public Test getTest() {
        return test;
    }

    /**
     * 
     * @param test
     *     The test
     */
    public void setTest(Test test) {
        this.test = test;
    }

}
