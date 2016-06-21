package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.monkeybusiness.jaaar.Adapter.ReviewListAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.utils.NonScrollListView;
import com.rey.material.widget.Button;

import java.util.ArrayList;
import java.util.List;

import butterknife.ButterKnife;

/**
 * Created by rakesh on 2/2/16.
 */
public class AttendanceReviewFragment extends Fragment implements ReviewAttdInterface,View.OnClickListener{


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

    public void submitAttd()
    {
        Toast.makeText(getActivity(), "Attendance Submitted", Toast.LENGTH_SHORT).show();
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
                submitAttd();
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
