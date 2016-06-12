
package com.monkeybusiness.jaaar.objectClasses.AddTestObject;


import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddTestObject {


    @SerializedName("test")
    @Expose
    private Test test;

    /**
     * @return The test
     */
    public Test getTest() {
        return test;
    }

    /**
     * @param test The test
     */
    public void setTest(Test test) {
        this.test = test;
    }


}
