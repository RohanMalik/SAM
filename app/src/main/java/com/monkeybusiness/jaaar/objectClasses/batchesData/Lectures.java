
package com.monkeybusiness.jaaar.objectClasses.batchesData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Lectures {

    @SerializedName("count")
    @Expose
    private int count;

    /**
     * 
     * @return
     *     The count
     */
    public int getCount() {
        return count;
    }

    /**
     * 
     * @param count
     *     The count
     */
    public void setCount(int count) {
        this.count = count;
    }

}
