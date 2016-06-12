package com.monkeybusiness.jaaar.objectClasses.testListResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("tests")
    @Expose
    private List<Test> tests = new ArrayList<Test>();
    @SerializedName("count")
    @Expose
    private Object count;

    /**
     * @return The tests
     */
    public List<Test> getTests() {
        return tests;
    }

    /**
     * @param tests The tests
     */
    public void setTests(List<Test> tests) {
        this.tests = tests;
    }

    /**
     * @return The count
     */
    public Object getCount() {
        return count;
    }

    /**
     * @param count The count
     */
    public void setCount(Object count) {
        this.count = count;
    }

}
