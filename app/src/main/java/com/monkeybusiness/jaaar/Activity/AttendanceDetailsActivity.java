package com.monkeybusiness.jaaar.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.primitives.Booleans;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.monkeybusiness.jaaar.Adapter.AttendanceDetailsAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.attendanceResponse.Datum;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.FloatingActionButton;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AttendanceDetailsActivity extends BaseActivity implements View.OnClickListener {

    private static final String TAG = "AttendanceDetails";
    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;

    FloatingActionButton fabAddAttd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        initialization();

        getAttendanceDetailsServerCall(getIntent().getIntExtra(Constants.BATCH_ID, 0));
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        fabAddAttd = (FloatingActionButton) findViewById(R.id.fabAddAttd);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        fabAddAttd.setOnClickListener(this);

        textViewActionTitle.setText("My Batches");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.fabAddAttd:
                break;
        }
    }

    public void getAttendanceDetailsServerCall(int id) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiService(xCookies, aCookies).apiCallGetAttendanceDetail(String.valueOf(id), "day", "1", "10", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, "Response : " + s);

                try {
                    JSONObject responseObject = new JSONObject(s);

                    JSONObject dataObject = responseObject.getJSONObject("data");

                    JSONObject studentAttendance = dataObject.getJSONObject("student_attendance");

                    JSONObject presentObject = studentAttendance.getJSONObject("present");

                    JSONObject absentObject = studentAttendance.getJSONObject("absent");

                    JSONObject attendanceStatus = studentAttendance.getJSONObject("attendance_status");

                    List<String> presentDates = new ArrayList<String>();
                    List<Integer> presentStudents = new ArrayList<Integer>();
                    List<Integer> absentStudents = new ArrayList<Integer>();
                    List<Boolean> attdStatus = new ArrayList<Boolean>();

                    List<Datum> attendanceList = new ArrayList<Datum>();

                    Iterator<String> presentKeys = presentObject.keys();

                    while (presentKeys.hasNext())
                    {
                        String key = presentKeys.next();
//                        presentDates.add(key);
//                        presentStudents.add(presentObject.getInt(key));
//                        absentStudents.add(absentObject.getInt(key));
//                        attdStatus.add(attendanceStatus.getBoolean(key));

                        Datum datum = new Datum();
                        datum.setDate(key);
                        datum.setAbsent(absentObject.getInt(key));
                        datum.setAttendanceStatus(attendanceStatus.getBoolean(key));
                        datum.setPresent(presentObject.getInt(key));

                        attendanceList.add(datum);
                    }

                    Log.d(TAG,"Datum : "+new Gson().toJson(attendanceList));
                    setUiData(attendanceList);
//                    Log.d(TAG,"presentStudent : "+new Gson().toJson(presentStudents));
//                    Log.d(TAG,"absentStudent : "+new Gson().toJson(absentStudents));
//                    Log.d(TAG,"attdStudent : "+new Gson().toJson(attdStatus));

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    private void setUiData(List<Datum> attendanceList) {

        AttendanceDetailsAdapter attendanceDetailsAdapter = new AttendanceDetailsAdapter(this,attendanceList);
        listViewLectures.setAdapter(attendanceDetailsAdapter);
    }
}
