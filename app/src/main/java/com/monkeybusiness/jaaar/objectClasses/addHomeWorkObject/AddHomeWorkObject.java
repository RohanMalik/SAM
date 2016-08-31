package com.monkeybusiness.jaaar.objectClasses.addHomeWorkObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddHomeWorkObject {

    @SerializedName("hws")
    @Expose
    private Hws hws;

    /**
     * @return The hws
     */
    public Hws getHws() {
        return hws;
    }

    /**
     * @param hws The hws
     */
    public void setHws(Hws hws) {
        this.hws = hws;
    }

}
