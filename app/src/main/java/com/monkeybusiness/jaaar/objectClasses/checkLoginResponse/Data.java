package com.monkeybusiness.jaaar.objectClasses.checkLoginResponse;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

public class Data {

    @SerializedName("user_info")
    @Expose
    private UserInfo userInfo;
    @SerializedName("user_type_details")
    @Expose
    private UserTypeDetails userTypeDetails;
    @SerializedName("user_roles")
    @Expose
    private List<UserRole> userRoles = new ArrayList<UserRole>();

    /**
     * @return The userInfo
     */
    public UserInfo getUserInfo() {
        return userInfo;
    }

    /**
     * @param userInfo The user_info
     */
    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    /**
     * @return The userTypeDetails
     */
    public UserTypeDetails getUserTypeDetails() {
        return userTypeDetails;
    }

    /**
     * @param userTypeDetails The user_type_details
     */
    public void setUserTypeDetails(UserTypeDetails userTypeDetails) {
        this.userTypeDetails = userTypeDetails;
    }

    /**
     * @return The userRoles
     */
    public List<UserRole> getUserRoles() {
        return userRoles;
    }

    /**
     * @param userRoles The user_roles
     */
    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
