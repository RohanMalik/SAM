package com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("hws")
    @Expose
    private List<Hw> hws = new ArrayList<Hw>();

    /**
     * @return The hws
     */
    public List<Hw> getHws() {
        return hws;
    }

    /**
     * @param hws The hws
     */
    public void setHws(List<Hw> hws) {
        this.hws = hws;
    }

}
