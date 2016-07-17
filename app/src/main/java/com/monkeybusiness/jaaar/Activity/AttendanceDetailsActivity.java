package com.monkeybusiness.jaaar.Activity;

import android.app.ProgressDialog;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.common.primitives.Booleans;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.monkeybusiness.jaaar.Adapter.AttendanceDetailsAdapter;
import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.attendanceResponse.Datum;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.BatchInfo;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.Iterator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class AttendanceDetailsActivity extends BaseActivity implements View.OnClickListener,DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AttendanceDetails";
    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;

    FloatingActionButton fabAddAttd;

    TextView textViewDate;

    int batchId;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        initialization();

        batchId = getIntent().getIntExtra(Constants.BATCH_ID, 0);


//        getMonthAttdServerCall(batchId);
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        fabAddAttd = (FloatingActionButton) findViewById(R.id.fabAddAttd);

        textViewDate = (TextView) findViewById(R.id.textViewDate);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        fabAddAttd.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());

        textViewDate.setText("Date : "+date);

        textViewActionTitle.setText("My ATTENDANCES");

        textViewDate.setOnClickListener(this);

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
                Date date = new Date();

                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                String dateStr = simpleDateFormat.format(date);

                Intent intent = new Intent(AttendanceDetailsActivity.this, AttendanceFragment.class);
                intent.putExtra(Constants.BATCH_ID,batchId);
                intent.putExtra(Constants.DATE,dateStr);
                startActivity(intent);
                break;
            case R.id.textViewDate:
                showDatePickrDialog();
                break;
        }
    }

    private void showDatePickrDialog() {

        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(AttendanceDetailsActivity.this, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
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
                        datum.setAttendanceStatus(attendanceStatus.getString(key));
                        datum.setPresent(presentObject.getInt(key));

                        attendanceList.add(datum);
                    }

                    Log.d(TAG,"Datum : "+new Gson().toJson(attendanceList));
                    setUiData(attendanceList,id);
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

    private void setUiData(List<Datum> attendanceList, int id) {

        Log.d("list","before : "+new Gson().toJson(attendanceList));

        Collections.sort(attendanceList, new Comparator<Datum>() {
            @Override
            public int compare(Datum self, Datum other) {
                return String.valueOf(self.getDate()).compareTo(String.valueOf(other.getDate()));
            }
        });

        List<Datum> attendanceListReverse = new ArrayList<>();

        for (int i = attendanceList.size()-1 ; i >=0 ; i--)
        {
            attendanceListReverse.add(attendanceList.get(i));
        }
        AttendanceDetailsAdapter attendanceDetailsAdapter = new AttendanceDetailsAdapter(this,attendanceListReverse,id);
        listViewLectures.setAdapter(attendanceDetailsAdapter);
    }

    public void getMonthAttdServerCall(int id)
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String fromDate = "2016-05-01T00:00:00.000Z";
        String toDate = "2016-06-01T00:00:00.000Z";

        RestClient.getApiService(xCookies,aCookies).apiCallMonthAttendanceDetail(String.valueOf(id),
                fromDate,
                toDate, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d(TAG,"Response : "+s);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG,"error : "+error.toString());
                    }
                });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG,"Date : "+year+" "+monthOfYear+" "+dayOfMonth);

        monthOfYear++;
        getSingleDayAttendanceServerCall(""+year+"-"+monthOfYear+"-"+dayOfMonth,batchId);
    }

    private void getSingleDayAttendanceServerCall(String date,int id) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        Log.d(TAG,"abcdate : "+date);

        String currentDate  = date+"T00:00:00.000Z";

        ProgressDialog dialog = ProgressDialog.show(this,"Please wait...","Loading...",false);

        RestClient.getApiService(xCookies,aCookies).apiCallGetSingleDayAttendanceDetail(String.valueOf(id), currentDate, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG,"Response : "+s);

                dialog.dismiss();

                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(s);
                    JSONObject responseMetaData = responseObject.getJSONObject("response_metadata");

                    String success = responseMetaData.getString("success");

                    JSONObject dataObject = responseObject.getJSONObject("data");

                    JSONObject studentAttd = dataObject.getJSONObject("student_attendance");

                    Log.d(TAG,"Attd : "+studentAttd.length());

                    if (success.equalsIgnoreCase("yes") && studentAttd.length()!=0)
                    {
                        Intent intent = new Intent(AttendanceDetailsActivity.this, AttendanceFragment.class);
                        intent.putExtra(Constants.BATCH_ID,id);
                        intent.putExtra(Constants.DATE,date);
                        startActivity(intent);
                    }
                    else
                    {
                        String msg = responseMetaData.getString("message");
                        Utils.failureDialog(AttendanceDetailsActivity.this,"Error","Attendance Not found");
                    }

           } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Log.d(TAG,"error : "+error.toString());
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        getAttendanceDetailsServerCall(batchId);
    }
}
