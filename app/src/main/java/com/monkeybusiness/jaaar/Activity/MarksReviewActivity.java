package com.monkeybusiness.jaaar.Activity;

import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.ExamMarksReviewFragment;
import com.monkeybusiness.jaaar.R;

import rmn.androidscreenlibrary.ASSL;

public class MarksReviewActivity extends AppCompatActivity implements View.OnClickListener{

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;
    FrameLayout frameLayoutReviewMarks;
    ImageView imageViewToolbaar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_marks_review);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);


        initialization();
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        frameLayoutReviewMarks = (FrameLayout) findViewById(R.id.frameLayoutReviewMarks);

        imageViewToolbaar = (ImageView) findViewById(R.id.imageViewToolbaar);
        imageViewToolbaar.setVisibility(View.GONE);
        
        setupFragmentForReview();
        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("REVIEW MARKS");

    }

    private void setupFragmentForReview() {

        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        transaction.add(R.id.frameLayoutReviewMarks,ExamMarksReviewFragment.newInstance());
        transaction.commit();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId())
        {
            case R.id.relativeLayoutMenu:
                break;
        }
    }
}
