
package com.monkeybusiness.jaaar.objectClasses.testMarksResponseData;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("test_marks")
    @Expose
    private List<TestMark> testMarks = new ArrayList<TestMark>();
    @SerializedName("count")
    @Expose
    private Object count;

    /**
     * 
     * @return
     *     The testMarks
     */
    public List<TestMark> getTestMarks() {
        return testMarks;
    }

    /**
     * 
     * @param testMarks
     *     The test_marks
     */
    public void setTestMarks(List<TestMark> testMarks) {
        this.testMarks = testMarks;
    }

    /**
     * 
     * @return
     *     The count
     */
    public Object getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(Object count) {
        this.count = count;
    }

}
