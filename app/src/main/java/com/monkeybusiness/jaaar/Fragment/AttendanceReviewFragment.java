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
import com.google.android.gms.internal.el;
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
import com.monkeybusiness.jaaar.utils.FontClass;
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
    Button buttonSave;

    TextView textViewAbsentStudents;
    TextView textViewPresentStudents;
    TextView textViewTitleAbsent;
    TextView textViewTitlePresent;
    TextView textViewListTitle;
    TextView textViewTitleRoll;
    TextView textViewTitleName;

    ObservableScrollView scrollView;


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
        setFont();
        return rootView;
    }

    private void setFont() {
        buttonSubmit.setTypeface(FontClass.proximaBold(context));
        buttonSave.setTypeface(FontClass.proximaBold(context));
        textViewAbsentStudents.setTypeface(FontClass.proximaRegular(context));
        textViewPresentStudents.setTypeface(FontClass.proximaRegular(context));
        textViewTitleAbsent.setTypeface(FontClass.proximaBold(context));
        textViewTitlePresent.setTypeface(FontClass.proximaBold(context));
        textViewListTitle.setTypeface(FontClass.proximaBold(context));
        textViewTitleRoll.setTypeface(FontClass.proximaBold(context));
        textViewTitleName.setTypeface(FontClass.proximaBold(context));
    }

    public void initialization()
    {
        buttonSubmit = (Button) rootView.findViewById(R.id.buttonSubmit);

        buttonSave = (Button) rootView.findViewById(R.id.buttonSave);

        listViewReviewAttd = (NonScrollListView) rootView.findViewById(R.id.listViewReviewAttd);

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.scrollView);

        textViewAbsentStudents = (TextView) rootView.findViewById(R.id.textViewAbsentStudents);

        textViewPresentStudents = (TextView) rootView.findViewById(R.id.textViewPresentStudents);
        textViewTitleAbsent = (TextView) rootView.findViewById(R.id.textViewTitleAbsent);
        textViewTitlePresent = (TextView) rootView.findViewById(R.id.textViewTitlePresent);
        textViewListTitle = (TextView) rootView.findViewById(R.id.textViewListTitle);
        textViewTitleRoll = (TextView) rootView.findViewById(R.id.textViewTitleRoll);
        textViewTitleName = (TextView) rootView.findViewById(R.id.textViewTitleName);

        String status = Prefs.with(this.getActivity()).getString(PrefsKeys.ATTD_STATUS,"");

        if (status.equalsIgnoreCase("save"))
        {
            buttonSave.setVisibility(View.GONE);
        }
        else if (status.equalsIgnoreCase("submit")){
            buttonSave.setVisibility(View.GONE);
            buttonSubmit.setVisibility(View.GONE);
        }

        buttonSubmit.setOnClickListener(this);
        buttonSave.setOnClickListener(this);
    }

    public void submitAttd(String status)
    {
        AttdPostObjectData objectData = new AttdPostObjectData();

        StudentAttendance studentAttendance = new StudentAttendance();
        studentAttendance.setStatus(status);
        String date = Prefs.with(this.getActivity()).getString(PrefsKeys.ATTD_DATE,"");
        date = date+"T00:00:00.000Z";
        studentAttendance.setAttendanceDate(date);

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

                    String status = "";

                    if (objectData.getStudentAttendance().getStatus().equalsIgnoreCase("save"))
                    {
                        status = "saved";
                    }
                    else {
                        status = "submitted";
                    }

                    dialog.dismiss();
                    if (attdSavedResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes"))
                    {
                        AlertDialog.Builder alert = new AlertDialog.Builder(context);
                        alert.setTitle("Success");
                        alert.setMessage("You Have successfully "+status+" attendance");
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
                submitAttd("submit");
                break;
            case R.id.buttonSave:
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
