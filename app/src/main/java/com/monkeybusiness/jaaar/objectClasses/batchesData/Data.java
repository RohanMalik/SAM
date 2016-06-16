
package com.monkeybusiness.jaaar.objectClasses.batchesData;

import java.util.ArrayList;
import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Data {

    @SerializedName("batches")
    @Expose
    private List<Batch> batches = new ArrayList<Batch>();

    /**
     * 
     * @return
     *     The batches
     */
    public List<Batch> getBatches() {
        return batches;
    }

    /**
     * 
     * @param batches
     *     The batches
     */
    public void setBatches(List<Batch> batches) {
        this.batches = batches;
    }

}
