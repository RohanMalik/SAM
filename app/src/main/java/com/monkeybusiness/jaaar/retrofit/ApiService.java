package com.monkeybusiness.jaaar.retrofit;


import com.monkeybusiness.jaaar.objectClasses.studentDetailsResponse.StudentsDetailsResponseData;
import com.monkeybusiness.jaaar.objectClasses.checkLoginResponse.CheckLoginResponse;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.objectClasses.loginResponseData.LoginResponse;
import com.monkeybusiness.jaaar.objectClasses.studentsResponse.StudentsListResponseData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.TestListResponse;

import retrofit.Callback;
import retrofit.http.Body;
import retrofit.http.GET;
import retrofit.http.POST;
import retrofit.http.Path;
import retrofit.http.Query;
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

    @GET("/lectures")
    void apiCallLectures(Callback<LectureResponseData> callback);

    //for getting list of students
    @GET("/students")
    void apiCallGetStudents(@Query("lecture_id") String lectureId,Callback<StudentsListResponseData> callback);

    //for getting list of students
    @GET("/students/{id}")
    void apiCallGetStudentDetails(@Path("id") String studentId, Callback<StudentsDetailsResponseData> callback);

    @GET("/tests")
    void apiCallTestList(Callback<TestListResponse> callback);

    @POST("/lectures/{lecture_id}/tests")
    void apiCallPostTest(@Path("lecture_id") String lectureId,@Body TypedInput testPostObject, Callback<String> callback);
}