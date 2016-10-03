package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.ExamsDetailsAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.examData.ExamData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class AllExamsActivity extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener {

    private static final String TAG = "AllExamsActivity";
    ListView listViewLectures;
    int examGrpId;
    String examGrpName;

    //    FloatingActionButton fabAddAttd;
    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exams_details);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        initialization();
        setFont();

        examGrpId = getIntent().getIntExtra(Constants.EXAM_GROUP_ID, 0);
        examGrpName = getIntent().getStringExtra(Constants.EXAM_GROUP_NAME);

        getExamsServerCall(examGrpId);

//        getMonthAttdServerCall(examGrpId);
    }

    private void setFont() {
        textViewActionTitle.setTypeface(FontClass.proximaRegular(this));
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

//        fabAddAttd = (FloatingActionButton) findViewById(R.id.fabAddAttd);


        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
//        fabAddAttd.setOnClickListener(this);

        SimpleDateFormat simpleDateFormat = new SimpleDateFormat("dd-MM-yyyy");
        String date = simpleDateFormat.format(new Date());


        textViewActionTitle.setText("My Exams");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
//            case R.id.fabAddAttd:
//                Date date = new Date();
//
//                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                String dateStr = simpleDateFormat.format(date);
//
//                Intent intent = new Intent(AllExamsActivity.this, AttendanceFragment.class);
//                intent.putExtra(Constants.BATCH_ID,examGrpId);
//                intent.putExtra(Constants.DATE,dateStr);
//                startActivity(intent);
//                break;
//            case R.id.textViewDate:
//                showDatePickrDialog();
//                break;
        }
    }

    private void showDatePickrDialog() {

        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(AllExamsActivity.this, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }

//    public void getAttendanceDetailsServerCall(int id) {
//
//        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
//        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");
//
//        RestClient.getApiService(xCookies, aCookies).apiCallGetAttendanceDetail(String.valueOf(id), "day", "1", "10", new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                Log.d(TAG, "Response : " + s);
//
//                try {
//                    JSONObject responseObject = new JSONObject(s);
//
//                    JSONObject dataObject = responseObject.getJSONObject("data");
//
//                    JSONObject studentAttendance = dataObject.getJSONObject("student_attendance");
//
//                    JSONObject presentObject = studentAttendance.getJSONObject("present");
//
//                    JSONObject absentObject = studentAttendance.getJSONObject("absent");
//
//                    JSONObject attendanceStatus = studentAttendance.getJSONObject("attendance_status");
//
//                    List<Datum> attendanceList = new ArrayList<Datum>();
//
//                    Iterator<String> attdKeys = attendanceStatus.keys();
//
//                    while (attdKeys.hasNext())
//                    {
//                        String key = attdKeys.next();
////                        presentDates.add(key);
////                        presentStudents.add(presentObject.getInt(key));
////                        absentStudents.add(absentObject.getInt(key));
////                        attdStatus.add(attendanceStatus.getBoolean(key));
//
//                        Datum datum = new Datum();
//                        datum.setDate(key);
//                        int absentValue = 0;
//
//                        try
//                        {
//                            absentValue = absentObject.getInt(key);
//                        }
//                        catch (JSONException e){
//
//                        }
//
//                        int presentValue = 0;
//                        try
//                        {
//                            presentValue = presentObject.getInt(key);
//                        }
//                        catch (JSONException e){
//
//                        }
//
//                        datum.setAbsent(absentValue);
//                        datum.setAttendanceStatus(attendanceStatus.getString(key));
//                        datum.setPresent(presentValue);
//
//                        attendanceList.add(datum);
//                    }
//
//                    Log.d(TAG,"Datum : "+new Gson().toJson(attendanceList));
//                    setUiData(attendanceList,id);
////                    Log.d(TAG,"presentStudent : "+new Gson().toJson(presentStudents));
////                    Log.d(TAG,"absentStudent : "+new Gson().toJson(absentStudents));
////                    Log.d(TAG,"attdStudent : "+new Gson().toJson(attdStatus));
//
//                } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                Log.d(TAG, "error : " + error.toString());
//            }
//        });
//    }

    private void setUiData(ExamData examData) {

        Log.d("list", "before : " + new Gson().toJson(examData));

//        AttendanceDetailsAdapter attendanceDetailsAdapter = new AttendanceDetailsAdapter(this,attendanceListReverse,id);
//        listViewLectures.setAdapter(attendanceDetailsAdapter);
        ExamsDetailsAdapter examsDetailsAdapter = new ExamsDetailsAdapter(this, examData, examGrpName);
        listViewLectures.setAdapter(examsDetailsAdapter);
    }

    public void getMonthAttdServerCall(int id) {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String fromDate = "2016-05-01T00:00:00.000Z";
        String toDate = "2016-06-01T00:00:00.000Z";

        RestClient.getApiService(xCookies, aCookies).apiCallMonthAttendanceDetail(String.valueOf(id),
                fromDate,
                toDate, new Callback<String>() {
                    @Override
                    public void success(String s, Response response) {
                        Log.d(TAG, "Response : " + s);
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                    }
                });
    }


    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG, "Date : " + year + " " + monthOfYear + " " + dayOfMonth);

        monthOfYear++;
//        getSingleDayAttendanceServerCall(""+year+"-"+monthOfYear+"-"+dayOfMonth,examGrpId);
    }

//    private void getSingleDayAttendanceServerCall(String date,int id) {
//
//        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
//        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");
//
//        Log.d(TAG,"abcdate : "+date);
//
//        String currentDate  = date+"T00:00:00.000Z";
//
//        ProgressDialog dialog = ProgressDialog.show(this,"Please wait...","Loading...",false);
//
//        RestClient.getApiService(xCookies,aCookies).apiCallGetSingleDayAttendanceDetail(String.valueOf(id), currentDate, new Callback<String>() {
//            @Override
//            public void success(String s, Response response) {
//                Log.d(TAG,"Response : "+s);
//
//                dialog.dismiss();
//
//                JSONObject responseObject = null;
//                try {
//                    responseObject = new JSONObject(s);
//                    JSONObject responseMetaData = responseObject.getJSONObject("response_metadata");
//
//                    String success = responseMetaData.getString("success");
//
//                    JSONObject dataObject = responseObject.getJSONObject("data");
//
//                    JSONObject studentAttd = dataObject.getJSONObject("student_attendance");
//
//                    Log.d(TAG,"Attd : "+studentAttd.length());
//
//                    if (success.equalsIgnoreCase("yes") && studentAttd.length()!=0)
//                    {
//                        Intent intent = new Intent(AllExamsActivity.this, AttendanceFragment.class);
//                        intent.putExtra(Constants.BATCH_ID,id);
//                        intent.putExtra(Constants.DATE,date);
//                        startActivity(intent);
//                    }
//                    else
//                    {
//                        String msg = responseMetaData.getString("message");
//                        AlertDialog.Builder alert = Utils.failureDialogCanOverride(AllExamsActivity.this,"Error","Attendance Not found");
//
//                        alert.setPositiveButton("Fill Attendance", new DialogInterface.OnClickListener() {
//                            @Override
//                            public void onClick(DialogInterface dialog, int which) {
//                                Intent intent = new Intent(AllExamsActivity.this, AttendanceFragment.class);
//                                intent.putExtra(Constants.BATCH_ID,id);
//                                intent.putExtra(Constants.DATE,date);
//                                startActivity(intent);
//                            }
//                        });
//
//                        alert.show();
//
//                    }
//
//           } catch (JSONException e) {
//                    e.printStackTrace();
//                }
//
//            }
//
//            @Override
//            public void failure(RetrofitError error) {
//                dialog.dismiss();
//                Log.d(TAG,"error : "+error.toString());
//            }
//        });
//    }

    @Override
    protected void onResume() {
        super.onResume();

        MasterClass.getInstance().getStudentsForMarksExams().clear();
    }

    private void getExamsServerCall(int examGrpId) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        boolean fromLecture = Prefs.with(this).getBoolean(Constants.FROM_LECTURES,false);

        if (fromLecture)
        {
            String lectureId = Prefs.with(this).getString(Constants.LECTURE_ID_EXAMS,"");
            RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetExamsByLecture(String.valueOf(examGrpId), String.valueOf(lectureId), new Callback<ExamData>() {
                @Override
                public void success(ExamData examData, Response response) {
                    Log.d(TAG, "Response : " + new Gson().toJson(examData));
                    setUiData(examData);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "Response : " + error.toString());
                }
            });
        }else {
            String gradeId = Prefs.with(this).getString(Constants.GRADE_ID, "");
            RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetExamsByBatch(String.valueOf(examGrpId), String.valueOf(gradeId), new Callback<ExamData>() {
                @Override
                public void success(ExamData examData, Response response) {
                    Log.d(TAG, "Response : " + new Gson().toJson(examData));
                    setUiData(examData);
                }

                @Override
                public void failure(RetrofitError error) {
                    Log.d(TAG, "Response : " + error.toString());
                }
            });
        }
    }
}
