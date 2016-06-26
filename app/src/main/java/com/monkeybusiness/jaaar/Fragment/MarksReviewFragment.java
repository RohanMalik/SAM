package com.monkeybusiness.jaaar.Fragment;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.MarksListAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.addMarksData.AddMarksrequestObject;
import com.monkeybusiness.jaaar.objectClasses.addMarksData.TestMarks;
import com.monkeybusiness.jaaar.objectClasses.simpleResponseDaa.SimpleResponseData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;

import java.io.UnsupportedEncodingException;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 2/2/16.
 */
public class MarksReviewFragment extends Fragment implements ReviewAttdInterface, View.OnClickListener {


    private static final String TAG = "AttdReviewFrag";
    View rootView;
    NonScrollListView listViewReviewAttd;
    MarksListAdapter reviewListAdapter;

    Button buttonSubmit;
    Button buttonSave;


    ObservableScrollView scrollView;

    TextView textViewAbsentStudents;
    TextView textViewPresentStudents;
    Context context;

    public static MarksReviewFragment newInstance(int page, String title) {

        MarksReviewFragment attendanceReviewFragment = new MarksReviewFragment();
        return attendanceReviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_marks_review, container, false);

        new ASSL(getActivity(), (ViewGroup) rootView.findViewById(R.id.scrollView), 1134, 720,
                false);

        initialization();
        return rootView;
    }

//    public void submitAttd(String status)
//    {
//        AttdPostObjectData objectData = new AttdPostObjectData();
//
//        StudentAttendance studentAttendance = new StudentAttendance();
//        studentAttendance.setStatus(status);
//        studentAttendance.setAttendanceDate(Utils.simpleDateFormatter(new Date()));
//
//        List<Student> students = new ArrayList<>();
//
//        for (SingleIdDetail singleIdDetail : singleIdDetails)
//        {
//            Student student = new Student();
//            student.setStatus(singleIdDetail.getStatus());
//            student.setStudentId(singleIdDetail.getStudentId());
//
//            students.add(student);
//        }
//
//        studentAttendance.setStudents(students);
//
//        objectData.setStudentAttendance(studentAttendance);
//
////        sendStudentsMarksServerCall(objectData);
//    }

    public void initialization() {
        buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        listViewReviewAttd = (NonScrollListView) rootView.findViewById(R.id.listViewReviewAttd);

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.scrollView);

        textViewAbsentStudents = (TextView) rootView.findViewById(R.id.textViewAbsentStudents);

        textViewPresentStudents = (TextView) rootView.findViewById(R.id.textViewPresentStudents);

        buttonSubmit.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    private void sendStudentsMarksServerCall(int id,String status) {

        context = getContext();
        String xCookies = Prefs.with(this.getContext()).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this.getContext()).getString(PrefsKeys.A_COOKIES, "");

        AddMarksrequestObject addMarksrequestObject = new AddMarksrequestObject();

        TestMarks testMarks = new TestMarks();

        testMarks.setStudents(MasterClass.getInstance().getStudentsForMarks());
        testMarks.setStatus(status);

        addMarksrequestObject.setTestMarks(testMarks);

        String jsonStr = new Gson().toJson(addMarksrequestObject);
        Log.d(TAG, "json : " + jsonStr);

        try {
            TypedInput typedInput = new TypedByteArray("application/json", jsonStr.getBytes("UTF-8"));

            ProgressDialog dialog = ProgressDialog.show(context, "Please wait.", "Loading...", false);

            RestClient.getApiServicePojo(xCookies, aCookies).apiCallSendStudentMarks(String.valueOf(id), typedInput, new Callback<SimpleResponseData>() {
                @Override
                public void success(SimpleResponseData simpleResponseData, Response response) {
                    Log.d(TAG, "Response : " + new Gson().toJson(simpleResponseData));

                    dialog.dismiss();
                    if (simpleResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes"))
                    {
                        String msg = "";

                        if (status.equalsIgnoreCase("save"))
                        {
                            msg = "You Have successfully saved attendance";
                        }
                        else
                        {
                            msg = "You Have successfully Submitted attendance";
                        }
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Success");
                        alert.setMessage(msg);
                        alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                            @Override
                            public void onClick(DialogInterface dialog, int which) {
                                getActivity().finish();
                            }
                        });
                        alert.show();
                    }
                    else
                    {
                        if (simpleResponseData.getResponseMetadata().getDisplay().equalsIgnoreCase("yes"))
                        {
                            Utils.failureDialog(context,"Error",simpleResponseData.getResponseMetadata().getMessage());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    Utils.failureDialog(context, "Error", "Something went wrong.");
                    Log.d(TAG, "error : " + error.toString());
                }
            });
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void onResumeFragment() {
        Log.d("abc", "onResumeFrag");
        setUiData();

        Log.d("MarksReview", "Marks : " + new Gson().toJson(MasterClass.getInstance().getStudentsForMarks()));

    }

    private void setUiData() {
//        absentStudents = 0;
//        studentIds = getAbsentStudents();
        reviewListAdapter = new MarksListAdapter(getActivity(), MasterClass.getInstance().getStudentsForMarks());
        listViewReviewAttd.setAdapter(reviewListAdapter);
    }

//    int absentStudents = 0;
//
//    public List<Integer> getAbsentStudents()
//    {
//        singleIdDetails = MasterClass.getInstance().getSingleIdDetails();
//        List<Integer> studentIds = new ArrayList<>();
//
//        for (SingleIdDetail singleIdDetail : singleIdDetails)
//        {
//            if (singleIdDetail.getStatus().equalsIgnoreCase("A"))
//            {
//                absentStudents++;
//                studentIds.add(singleIdDetail.getStudentId());
//            }
//        }
//
//        textViewAbsentStudents.setText(String.valueOf(absentStudents));
//        textViewPresentStudents.setText(String.valueOf(singleIdDetails.size()-absentStudents));
//
//        return studentIds;
//    }

    @Override
    public void onClick(View v) {

        Test test = Prefs.with(this.getContext()).getObject(PrefsKeys.TEST_DATA, Test.class);
        switch (v.getId()) {
            case R.id.buttonSubmit:
//                submitAttd("save");
                sendStudentsMarksServerCall(test.getId(),"submit");
                break;
            case R.id.buttonSave:
                sendStudentsMarksServerCall(test.getId(),"save");
                break;
        }
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);

        Log.d("abc", "viewCreated");
    }
}
