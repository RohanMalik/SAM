package com.monkeybusiness.jaaar.Fragment;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.util.SparseArray;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ProgressBar;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPager;
import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.BatchInfo;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

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

/**
 * Created by rohanmalik on 29/12/15.
 */
public class AttendanceFragment extends BaseActivity implements DatePickerDialog.OnDateSetListener {

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    HollyViewPager hollyViewPager;
    int pageCount;
//    ViewPager viewPagerAttd;
    ProgressBar progressBarAttd;
    int batchId;
    List<StudentsInfo> studentsInfos;
    //    AttendanceSlidePagerAdapter adapter;
    private String TAG = "AttendanceFragment";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_attendance);


        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        MasterClass.getInstance().setAttendanceFragment(this);

        Utils.classFlag = 1;

        toggleLayouts(linearlayoutAttendance, textViewAttendance);

//        viewPagerAttd = (ViewPager) findViewById(R.id.viewPagerAttd);

        hollyViewPager = (HollyViewPager) findViewById(R.id.hollyViewPager);

        progressBarAttd = (ProgressBar) findViewById(R.id.progressBarAttd);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("Attendance");

        Intent intent = getIntent();

        if (intent != null) {
            batchId = intent.getIntExtra(Constants.BATCH_ID, 0);
            String date = intent.getStringExtra(Constants.DATE);

            Prefs.with(this).save(PrefsKeys.ATTD_DATE, date);

            textViewActionTitle.setText("Attendance : " + date);
            getSingleDayAttendanceServerCall(date, batchId);
            Prefs.with(this).save(Constants.BATCH_ID, batchId);
        }

//        adapter = new AttendanceSlidePagerAdapter(getSupportFragmentManager(),6);


//        viewPagerAttd.setPageTransformer(true, new DepthPageTransformer());
//        viewPagerAttd.setAdapter(adapter);
//
//        viewPagerAttd.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                if (position == 5) {
//                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapter.getItem(position);
//                    reviewAttdInterface.onResumeFragment();
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//        });

    }

    public void setUIData(List<SingleIdDetail> singleIdDetails, List<StudentsInfo> studentsInfos, String classAlias) {

        progressBarAttd.setVisibility(View.GONE);
        hollyViewPager.setVisibility(View.VISIBLE);

        for (StudentsInfo studentsInfo : studentsInfos) {

            for (SingleIdDetail singleIdDetail : singleIdDetails) {

                if (singleIdDetail.getStudentId() == studentsInfo.getId()) {

                    singleIdDetail.setStudentName(studentsInfo.getStudentName());
                }
            }
        }

        pageCount = singleIdDetails.size();
        Log.d(TAG, "pageCount : " + pageCount);
        ArrayList<StudentAttdData> studentAttdDatas = new ArrayList<>();

        Log.d("sort", "before abc : " + new Gson().toJson(singleIdDetails));
        Log.d("sort", "before studentInfo : " + new Gson().toJson(studentsInfos));

        Collections.sort(singleIdDetails, new Comparator<SingleIdDetail>() {
            @Override
            public int compare(SingleIdDetail self, SingleIdDetail other) {
                return String.valueOf(self.getStudentName()).compareTo(String.valueOf(other.getStudentName()));
            }
        });

        Collections.sort(studentsInfos, new Comparator<StudentsInfo>() {
            @Override
            public int compare(StudentsInfo self, StudentsInfo other) {
                return String.valueOf(self.getStudentName()).compareTo(String.valueOf(other.getStudentName()));
            }
        });


        this.studentsInfos = studentsInfos;

        Log.d("sort", "after singleId : " + new Gson().toJson(singleIdDetails));
        Log.d("sort", "after studentInfo : " + new Gson().toJson(studentsInfos));

        MasterClass.getInstance().setStudentAttdDatas(studentAttdDatas);

        MasterClass.getInstance().setSingleIdDetails(singleIdDetails);
        MasterClass.getInstance().setStudentsInfos(studentsInfos);
        MasterClass.getInstance().setClassAlias(classAlias);

        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
            @Override
            public float getHeightPercentForPage(int page) {

//                Log.d("attendancePager","Height : "+((page+4)%10)/10f);
//                return ((page+4)%10)/10f;
                return 0.5f;
            }

        });

        AttendancePagerAdapterHolly adapterHolly = new AttendancePagerAdapterHolly(getSupportFragmentManager());

        hollyViewPager.setAdapter(adapterHolly);

        hollyViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

            }

            @Override
            public void onPageSelected(int position) {
                Log.d(TAG, "pageSelected : " + position);
                if (position == pageCount) {
                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapterHolly.getRegisteredFragment(position);
                    reviewAttdInterface.onResumeFragment();
                }
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.textViewActionTitle:
                showDateDialog();
                break;
        }
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(AttendanceFragment.this, now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH));

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    private void getSingleDayAttendanceServerCall(String date, int id) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String currentDate = date + "T00:00:00.000Z";

        hollyViewPager.setVisibility(View.GONE);
        progressBarAttd.setVisibility(View.VISIBLE);

        RestClient.getApiService(xCookies, aCookies).apiCallGetSingleDayAttendanceDetail(String.valueOf(id), currentDate, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, "Response : " + s);
                Prefs.with(AttendanceFragment.this).save(PrefsKeys.ATTD_STATUS, "");

                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(s);

                    JSONObject dataObject = responseObject.getJSONObject("data");

                    JSONObject batchInfoObject = dataObject.getJSONObject("batch_info");

                    Log.d(TAG, "abc " + batchInfoObject.toString());

                    BatchInfo batchInfo = new Gson().fromJson(batchInfoObject.toString(), BatchInfo.class);

                    Log.d(TAG, "batchInfo : " + new Gson().toJson(batchInfo));

                    JSONArray studentInfoArray = dataObject.getJSONArray("students_info");

                    List<StudentsInfo> studentsInfos = new ArrayList<StudentsInfo>();

                    for (int i = 0; i < studentInfoArray.length(); i++) {
                        StudentsInfo studentsInfo = new Gson().fromJson(studentInfoArray.get(i).toString(), StudentsInfo.class);
                        studentsInfos.add(studentsInfo);
                    }

                    Log.d(TAG, "StudentsInfo : " + new Gson().toJson(studentsInfos));


                    JSONObject studentAttd = dataObject.getJSONObject("student_attendance");


                    if (studentAttd.length() != 0) {
                        Iterator<String> keys = studentAttd.keys();

                        List<SingleIdDetail> singleIdDetails = new ArrayList<SingleIdDetail>();

                        while (keys.hasNext()) {
                            String key = keys.next();

                            Log.d(TAG, "Key : " + key);

                            singleIdDetails.add(getSingleIdObject(studentAttd, key));
                        }

                        Log.d(TAG, "Json : " + new Gson().toJson(singleIdDetails));

                        JSONObject studentAttdGrpObj = dataObject.getJSONObject("student_attendance_groups");

                        String status = studentAttdGrpObj.getString("status");
                        Prefs.with(AttendanceFragment.this).save(PrefsKeys.ATTD_STATUS, status);

                        Log.d(TAG, "Attd_status : " + status);

                        setUIData(singleIdDetails, studentsInfos, batchInfo.getClassAlias());
                    } else {
                        List<SingleIdDetail> singleIdDetails = new ArrayList<SingleIdDetail>();

                        for (StudentsInfo studentsInfo : studentsInfos) {

                            singleIdDetails.add(getDefaultSingleIdObject(studentAttd, studentsInfo));
                        }
                        setUIData(singleIdDetails, studentsInfos, batchInfo.getClassAlias());
                    }

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

    private SingleIdDetail getSingleIdObject(JSONObject studentAttd, String key) {

        try {
            JSONObject singleIdobject = studentAttd.getJSONObject(key);

            SingleIdDetail singleIdDetail = new SingleIdDetail();

            singleIdDetail.setId(singleIdobject.getInt("id"));
            singleIdDetail.setStudentId(singleIdobject.getInt("student_id"));
            singleIdDetail.setStatus(singleIdobject.getString("status"));
            singleIdDetail.setAttendanceDate(singleIdobject.getString("attendance_date"));
            singleIdDetail.setIsSave(singleIdobject.getBoolean("is_save"));
            singleIdDetail.setStudentAttendanceGroupId(singleIdobject.getInt("student_attendance_group_id"));

            Log.d(TAG, "SingleId : " + new Gson().toJson(singleIdDetail));

            return singleIdDetail;
        } catch (JSONException e) {
            e.printStackTrace();
        }
        return null;
    }

    private SingleIdDetail getDefaultSingleIdObject(JSONObject studentAttd, StudentsInfo studentsInfo) {

        try {
            SingleIdDetail singleIdDetail = new SingleIdDetail();

            singleIdDetail.setId(0);
            singleIdDetail.setStudentId(studentsInfo.getId());
            singleIdDetail.setStatus("P");
            singleIdDetail.setAttendanceDate(Utils.getOnlyDateNotTime(new Date()));
            singleIdDetail.setIsSave(false);
            singleIdDetail.setStudentAttendanceGroupId(0);

            Log.d(TAG, "SingleId : " + new Gson().toJson(singleIdDetail));

            return singleIdDetail;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        monthOfYear++;
        textViewActionTitle.setText("ATTENDANCE : " + year + "-" + monthOfYear + "-" + dayOfMonth);

        checkSingleDayAttendanceServerCall("" + year + "-" + monthOfYear + "-" + dayOfMonth, batchId);
    }

    private void checkSingleDayAttendanceServerCall(String date, int id) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        Log.d(TAG, "abcdate : " + date);

        String currentDate = date + "T00:00:00.000Z";

        ProgressDialog dialog = ProgressDialog.show(this, "Please wait...", "Loading...", false);

        RestClient.getApiService(xCookies, aCookies).apiCallGetSingleDayAttendanceDetail(String.valueOf(id), currentDate, new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG, "Response : " + s);

                dialog.dismiss();

                JSONObject responseObject = null;
                try {
                    responseObject = new JSONObject(s);
                    JSONObject responseMetaData = responseObject.getJSONObject("response_metadata");

                    String success = responseMetaData.getString("success");

                    JSONObject dataObject = responseObject.getJSONObject("data");

                    JSONObject studentAttd = dataObject.getJSONObject("student_attendance");

                    Log.d(TAG, "Attd : " + studentAttd.length());

                    if (success.equalsIgnoreCase("yes") && studentAttd.length() != 0) {
                        Intent intent = new Intent(AttendanceFragment.this, AttendanceFragment.class);
                        intent.putExtra(Constants.BATCH_ID, id);
                        intent.putExtra(Constants.DATE, date);
                        startActivity(intent);
                        finish();
                    } else {
                        String msg = responseMetaData.getString("message");
                        AlertDialog.Builder alert = Utils.failureDialogCanOverride(AttendanceFragment.this, "Error", "Attendance Not found");

                        alert.setPositiveButton("Fill Attendance", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                Intent intent = new Intent(AttendanceFragment.this, AttendanceFragment.class);
                                intent.putExtra(Constants.BATCH_ID, id);
                                intent.putExtra(Constants.DATE, date);
                                startActivity(intent);
                            }
                        });

                        alert.show();
                    }

                } catch (JSONException e) {
                    e.printStackTrace();
                }

            }

            @Override
            public void failure(RetrofitError error) {
                dialog.dismiss();
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    public class AttendancePagerAdapterHolly extends FragmentPagerAdapter {

        SparseArray<Fragment> registeredFragments = new SparseArray<Fragment>();

        public AttendancePagerAdapterHolly(FragmentManager fm) {
            super(fm);
        }

        @Override
        public Fragment getItem(int position) {
            if (position != pageCount) {
                return StudentAttendanceCardFargment.newInstance(position, (String) getPageTitle(position));
            } else {
                return AttendanceReviewFragment.newInstance(position, (String) getPageTitle(position));
            }
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            Fragment fragment = (Fragment) super.instantiateItem(container, position);
            registeredFragments.put(position, fragment);
            return fragment;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            registeredFragments.remove(position);
            super.destroyItem(container, position, object);
        }


        public Fragment getRegisteredFragment(int position) {
            return registeredFragments.get(position);
        }

        @Override
        public int getCount() {
            return pageCount + 1;
        }

        @Override
        public CharSequence getPageTitle(int position) {
            if (position != pageCount) {
//                if ((studentsInfos.get(position).getRollno()+"").length()<=2)
//                {
                return "" + (position + 1);
//                }
//                else {
//                    return "Roll No. " + (position+1);
//                }
//                return studentsInfos.get(position).getRollno()+"";
            } else {
                return "Submit";
            }
        }

    }

}
