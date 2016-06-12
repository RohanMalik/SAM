
package com.monkeybusiness.jaaar.objectClasses.AddTestObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Test {

    @SerializedName("test_name")
    @Expose
    private String testName;
    @SerializedName("description")
    @Expose
    private String description;
    @SerializedName("test_date")
    @Expose
    private String testDate;
    @SerializedName("max_marks")
    @Expose
    private String maxMarks;
    @SerializedName("min_marks")
    @Expose
    private String minMarks;
    @SerializedName("duration_minutes")
    @Expose
    private String durationMinutes;

    /**
     * 
     * @return
     *     The testName
     */
    public String getTestName() {
        return testName;
    }

    /**
     * 
     * @param testName
     *     The test_name
     */
    public void setTestName(String testName) {
        this.testName = testName;
    }

    /**
     * 
     * @return
     *     The description
     */
    public String getDescription() {
        return description;
    }

    /**
     * 
     * @param description
     *     The description
     */
    public void setDescription(String description) {
        this.description = description;
    }

    /**
     * 
     * @return
     *     The testDate
     */
    public String getTestDate() {
        return testDate;
    }

    /**
     * 
     * @param testDate
     *     The test_date
     */
    public void setTestDate(String testDate) {
        this.testDate = testDate;
    }

    /**
     * 
     * @return
     *     The maxMarks
     */
    public String getMaxMarks() {
        return maxMarks;
    }

    /**
     * 
     * @param maxMarks
     *     The max_marks
     */
    public void setMaxMarks(String maxMarks) {
        this.maxMarks = maxMarks;
    }

    /**
     * 
     * @return
     *     The minMarks
     */
    public String getMinMarks() {
        return minMarks;
    }

    /**
     * 
     * @param minMarks
     *     The min_marks
     */
    public void setMinMarks(String minMarks) {
        this.minMarks = minMarks;
    }

    /**
     * 
     * @return
     *     The durationMinutes
     */
    public String getDurationMinutes() {
        return durationMinutes;
    }

    /**
     * 
     * @param durationMinutes
     *     The duration_minutes
     */
    public void setDurationMinutes(String durationMinutes) {
        this.durationMinutes = durationMinutes;
    }

}
