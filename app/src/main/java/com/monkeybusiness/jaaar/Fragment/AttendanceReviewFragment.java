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

import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.monkeybusiness.jaaar.Adapter.ReviewListAdapter;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.ReviewAttdInterface;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.utils.NonScrollListView;

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

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);
    }
}
