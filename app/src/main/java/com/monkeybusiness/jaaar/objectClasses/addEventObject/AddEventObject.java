
package com.monkeybusiness.jaaar.objectClasses.addEventObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class AddEventObject {

    @SerializedName("events")
    @Expose
    private Events events;

    /**
     * 
     * @return
     *     The events
     */
    public Events getEvents() {
        return events;
    }

    /**
     * 
     * @param events
     *     The events
     */
    public void setEvents(Events events) {
        this.events = events;
    }

}
