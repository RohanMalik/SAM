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
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Adapter.ReviewListAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.addAttdRequestObject.AttdPostObjectData;
import com.monkeybusiness.jaaar.objectClasses.addAttdRequestObject.Student;
import com.monkeybusiness.jaaar.objectClasses.addAttdRequestObject.StudentAttendance;
import com.monkeybusiness.jaaar.objectClasses.attdSavedResponse.AttdSavedResponseData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import butterknife.ButterKnife;
import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;
import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 2/2/16.
 */
public class AttendanceReviewFragment extends Fragment implements ReviewAttdInterface,View.OnClickListener{


    private static final String TAG = "AttdReviewFrag";
    View rootView;
    NonScrollListView listViewReviewAttd;
    ReviewListAdapter reviewListAdapter;

     Button buttonSubmit;

    ObservableScrollView scrollView;

    TextView textViewAbsentStudents;
    TextView textViewPresentStudents;

    public static AttendanceReviewFragment newInstance(int page,String title) {

        AttendanceReviewFragment attendanceReviewFragment = new AttendanceReviewFragment();
        return attendanceReviewFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_attd_review,container,false);

        new ASSL(getActivity(), (ViewGroup) rootView.findViewById(R.id.scrollView), 1134, 720,
                false);
        initialization();
        return rootView;
    }

    public void initialization()
    {
        buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);

        listViewReviewAttd = (NonScrollListView) rootView.findViewById(R.id.listViewReviewAttd);

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.scrollView);

        textViewAbsentStudents = (TextView) rootView.findViewById(R.id.textViewAbsentStudents);

        textViewPresentStudents = (TextView) rootView.findViewById(R.id.textViewPresentStudents);

        buttonSubmit.setOnClickListener(this);
    }

    public void submitAttd(String status)
    {
        AttdPostObjectData objectData = new AttdPostObjectData();

        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setStatus(status);
        studentAttendance.setAttendanceDate(Utils.simpleDateFormatter(new Date()));

        List<Student> students = new ArrayList<>();

        for (SingleIdDetail singleIdDetail : singleIdDetails)
        {
            Student student = new Student();
            student.setStatus(singleIdDetail.getStatus());
            student.setStudentId(singleIdDetail.getStudentId());

            students.add(student);
        }

        studentAttendance.setStudents(students);

        objectData.setStudentAttendance(studentAttendance);

        sendStudentsAttdServerCall(objectData);
    }

    Context context;

    private void sendStudentsAttdServerCall(AttdPostObjectData objectData) {

        context = getContext();
        String xCookies = Prefs.with(this.getContext()).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this.getContext()).getString(PrefsKeys.A_COOKIES, "");

        String jsonStr = new Gson().toJson(objectData);
        Log.d(TAG,"json : "+jsonStr);

        int batchId = Prefs.with(this.getContext()).getInt(Constants.BATCH_ID,0);

        try {
            TypedInput typedInput = new TypedByteArray("application/json",jsonStr.getBytes("UTF-8"));

            ProgressDialog dialog = ProgressDialog.show(context,"Please wait.","Loading...",false);

            RestClient.getApiServicePojo(xCookies,aCookies).apiCallPostAttendances(String.valueOf(batchId), typedInput, new Callback<AttdSavedResponseData>() {
                @Override
                public void success(AttdSavedResponseData attdSavedResponseData, Response response) {
                    Log.d(TAG,"Response : "+new Gson().toJson(attdSavedResponseData));

                    dialog.dismiss();
                    if (attdSavedResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes"))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Success");
                        alert.setMessage("You Have successfully saved attendance");
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
                        if (attdSavedResponseData.getResponseMetadata().getDisplay().equalsIgnoreCase("yes"))
                        {
                            Utils.failureDialog(context,"Error",attdSavedResponseData.getResponseMetadata().getMessage());
                        }
                    }
                }

                @Override
                public void failure(RetrofitError error) {
                    dialog.dismiss();
                    Utils.failureDialog(context,"Error","Something went wrong.");
                    Log.d(TAG,"error : "+error.toString());
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
    }

    List<SingleIdDetail> singleIdDetails;
    List<Integer> studentIds;
//    String classAlias;

    private void setUiData() {
        absentStudents = 0;
        studentIds = getAbsentStudents();
        reviewListAdapter = new ReviewListAdapter(getActivity(),studentIds);
        listViewReviewAttd.setAdapter(reviewListAdapter);
    }

    int absentStudents = 0;

    public List<Integer> getAbsentStudents()
    {
        singleIdDetails = MasterClass.getInstance().getSingleIdDetails();
        List<Integer> studentIds = new ArrayList<>();

        for (SingleIdDetail singleIdDetail : singleIdDetails)
        {
            if (singleIdDetail.getStatus().equalsIgnoreCase("A"))
            {
                absentStudents++;
                studentIds.add(singleIdDetail.getStudentId());
            }
        }

        textViewAbsentStudents.setText(String.valueOf(absentStudents));
        textViewPresentStudents.setText(String.valueOf(singleIdDetails.size()-absentStudents));

        return studentIds;
    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.buttonSubmit:
                submitAttd("save");
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
