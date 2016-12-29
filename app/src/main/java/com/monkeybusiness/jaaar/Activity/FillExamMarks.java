package com.monkeybusiness.jaaar.Activity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPagerConfigurator;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.ExamMarksAdapter;
import com.monkeybusiness.jaaar.Fragment.ExamMarksReviewFragment;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.examData.Exam;
import com.monkeybusiness.jaaar.objectClasses.examStudentMarks.ExamMark;
import com.monkeybusiness.jaaar.objectClasses.examStudentMarks.ExamStudentMarks;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.Student;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.StudentDetailsForMarksResponse;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import rmn.androidscreenlibrary.ASSL;

public class FillExamMarks extends BaseActivity {

    private String TAG = "FillExamMarks";

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;
    TextView textViewActionSave;
    ExamMarksAdapter examMarksAdapter;
    ListView listViewExamMarks;
    Exam exam;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_fill_exam_marks);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        exam = Prefs.with(this).getObject(PrefsKeys.EXAM_DATA, Exam.class);

        intialization();
        getTestMarksServerCall();
    }

    private void intialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewActionSave = (TextView) findViewById(R.id.textViewActionSave);
        listViewExamMarks = (ListView) findViewById(R.id.listViewExamMarks);

        textViewActionSave.setVisibility(View.VISIBLE);

        textViewActionSave.setOnClickListener(this);
        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("" + exam.getExamName());

//        textViewActionTitle.setText("" + exam.getExamName());
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.textViewActionSave:
                MasterClass.getInstance().setStudentsForMarksExams(examMarksAdapter.studentForMarkses);
                Intent intent = new Intent(this,MarksReviewActivity.class);
                startActivityForResult(intent,REQUEST_CODE);
                break;
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        Log.d(TAG,"onActivityResult"+requestCode+" : "+resultCode);
        switch (requestCode)
        {
            case REQUEST_CODE:
                if (resultCode == ExamMarksReviewFragment.RESULT_OK)
                {
                    finish();
                }
                break;
        }
    }

    public static final int REQUEST_CODE = 999;

    private void getTestMarksServerCall() {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        String batchId = String.valueOf(Prefs.with(this).getString(Constants.BATCH_ID,""));

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetExamMarks(String.valueOf(exam.getId()), batchId, new Callback<ExamStudentMarks>() {
            @Override
            public void success(ExamStudentMarks examStudentMarks, Response response) {
                Log.d(TAG, "ExamsMarksResponse : " + new Gson().toJson(examStudentMarks));

                Prefs.with(FillExamMarks.this).save(PrefsKeys.EXAM_MARKS_DATA, examStudentMarks);

                Log.d(TAG,"size : "+examStudentMarks.getData().getExamMarks().size());
                List<Student> students = new ArrayList<Student>();
                for (ExamMark examMark : examStudentMarks.getData().getExamMarks()) {
                    Student student = new Student();
                    student.setId(examMark.getStudent().getId());
                    Log.d(TAG,"id : "+examMark.getStudent().getId());
//                    student.setBatchId(examMark.getStudent().getBatchId());
                    student.setRollno(examMark.getStudent().getRollno());
                    student.setStudentName(examMark.getStudent().getStudentName());

                    students.add(student);
                }
                getTestStudentsServerCall(students);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    private void getTestStudentsServerCall(List<Student> students) {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallGetExamStudents(String.valueOf(exam.getId()),String.valueOf(Prefs.with(this).getString(Constants.BATCH_ID,"")), new Callback<StudentDetailsForMarksResponse>() {
            @Override
            public void success(StudentDetailsForMarksResponse studentDetailsForMarksResponse, Response response) {
                Log.d(TAG, "Response : " + new Gson().toJson(studentDetailsForMarksResponse));
                Log.d(TAG, "size api : " + studentDetailsForMarksResponse.getData().getStudents().size());

                setUIData(studentDetailsForMarksResponse, students);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }

    Activity context;
    List<Student> students;
    public void setUIData(StudentDetailsForMarksResponse studentDetailsForMarksResponse, List<Student> studentsList) {

        context = this;
//        progressBarAttd.setVisibility(View.GONE);

        if (studentsList.isEmpty()) {
            students = studentDetailsForMarksResponse.getData().getStudents();
        } else {
            students = studentsList;
        }

        int pageCount = students.size();
        Log.d(TAG, "pageCount : " + pageCount);

        Log.d("sort", "before studentInfo : " + new Gson().toJson(students));

        Collections.sort(students, new Comparator<Student>() {
            @Override
            public int compare(Student self, Student other) {
                return String.valueOf(self.getStudentName()).compareTo(String.valueOf(other.getStudentName()));
            }
        });

        Prefs.with(FillExamMarks.this).save(PrefsKeys.STUDENT_EXAM_TEST, studentDetailsForMarksResponse);

        Log.d("sort", "after students : " + new Gson().toJson(students));

        MasterClass.getInstance().setClassAlias(Prefs.with(this).getString(Constants.CLASS_ALIAS,""));
//        MasterClass.getInstance().setStudents(students);

        examMarksAdapter = new ExamMarksAdapter(this,students,Prefs.with(this).getString(Constants.CLASS_ALIAS,""));
        listViewExamMarks.setAdapter(examMarksAdapter);

//        hollyViewPager.getViewPager().setPageMargin(getResources().getDimensionPixelOffset(R.dimen.viewpager_margin));
//        hollyViewPager.setConfigurator(new HollyViewPagerConfigurator() {
//            @Override
//            public float getHeightPercentForPage(int page) {
//
////                Log.d("attendancePager","Height : "+((page+4)%10)/10f);
////                return ((page+4)%10)/10f;
//                return 0.5f;
//            }
//
//        });
//
//        FillMarksForExamsCategoryActivity.AttendancePagerAdapterHolly adapterHolly = new FillMarksForExamsCategoryActivity.AttendancePagerAdapterHolly(getSupportFragmentManager());
//
//        hollyViewPager.setAdapter(adapterHolly);
//
//        hollyViewPager.getViewPager().addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
//            @Override
//            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
//
//            }
//
//            @Override
//            public void onPageSelected(int position) {
//                Log.d(TAG, "pageSelected : " + position);
//
//                //Close keyBoard in transition
////                InputMethodManager inputManager = (InputMethodManager) context.getSystemService(
////                        Context.INPUT_METHOD_SERVICE);
////                inputManager.hideSoftInputFromWindow(context.getCurrentFocus().getWindowToken(),
////                        InputMethodManager.HIDE_NOT_ALWAYS);
//
//                try {
//                    if (getCurrentFocus() != null) {
//                        InputMethodManager inputMethodManager = (InputMethodManager) getSystemService(INPUT_METHOD_SERVICE);
//                        inputMethodManager.hideSoftInputFromWindow(getCurrentFocus().getWindowToken(), 0);
//                    }
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
//
//                if (position == pageCount) {
//                    ReviewAttdInterface reviewAttdInterface = (ReviewAttdInterface) adapterHolly.getRegisteredFragment(position);
//                    reviewAttdInterface.onResumeFragment();
//                } else {
//
//                }
//            }
//
//            @Override
//            public void onPageScrollStateChanged(int state) {
//
//            }
//
//
//    });
    }

    static void destroyAfterCallBack(){

    }
}
