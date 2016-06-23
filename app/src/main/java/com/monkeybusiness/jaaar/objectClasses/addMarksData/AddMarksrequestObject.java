package com.monkeybusiness.jaaar.objectClasses.addMarksData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddMarksrequestObject {

    @SerializedName("test_marks")
    @Expose
    private TestMarks testMarks;

    /**
     * @return The testMarks
     */
    public TestMarks getTestMarks() {
        return testMarks;
    }

    /**
     * @param testMarks The test_marks
     */
    public void setTestMarks(TestMarks testMarks) {
        this.testMarks = testMarks;
    }

}
