package com.monkeybusiness.jaaar.retrofit;


import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.objectClasses.loginRequestObject.LoginRequestObject;
import com.monkeybusiness.jaaar.objectClasses.loginResponseData.LoginResponse;

import org.json.JSONObject;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.Header;
import retrofit.http.POST;
import retrofit.http.Query;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Define all server calls here
 */
public interface ApiService {

    @GET("/session")
    void apiCall(@Query("latlng") String latlng, @Query("sensor") String sensor, Callback<String> callback);

//    @FormUrlEncoded
//    @POST("/session")
//    public void apiCallLogin(@Field("session") String session,@Field("password") String password, Callback<String> callback);

    @POST("/session")
    void apiCallLogin(@Body TypedInput loginRequestObject, Callback<LoginResponse> callback);

    @GET("/check_login")
    void apiCallCheckLogin(Callback<CheckLoginResponse> callback);
}