
package com.monkeybusiness.jaaar.objectClasses.loginRequestObject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Session {

    @SerializedName("username")
    @Expose
    private String username;

    /**
     * 
     * @return
     *     The username
     */
    public String getUsername() {
        return username;
    }

    /**
     * 
     * @param username
     *     The username
     */
    public void setUsername(String username) {
        this.username = username;
    }

}
