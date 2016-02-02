package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ListView;
import android.widget.Toast;

import com.monkeybusiness.jaaar.Adapter.ReviewListAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;

import java.util.List;

/**
 * Created by rakesh on 2/2/16.
 */
public class AttendanceReviewFragment extends Fragment implements ReviewAttdInterface,View.OnClickListener{


    View rootView;
    ListView listViewReviewAttd;
    ReviewListAdapter reviewListAdapter;

    Button buttonSubmit;

    public static AttendanceReviewFragment newInstance(int page) {

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

        listViewReviewAttd = (ListView) rootView.findViewById(R.id.listViewReviewAttd);
        reviewListAdapter = new ReviewListAdapter(getActivity());
        listViewReviewAttd.setAdapter(reviewListAdapter);

        buttonSubmit.setOnClickListener(this);
    }

    public void submitAttd()
    {
        Toast.makeText(getActivity(), "Attendance Submitted", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onResumeFragment() {
        Log.d("attdReview", "onResumeFrag");
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
}
