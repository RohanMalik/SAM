package com.monkeybusiness.jaaar.Activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Utils;

import rmn.androidscreenlibrary.ASSL;

public class ExamTypeActivity extends BaseActivity {

    LinearLayout linearlayoutClassExm;
    LinearLayout linearlayoutLectureExm;

    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_exam_type);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

        Utils.classFlag = 5;

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);
        textViewActionTitle.setText("EXAM TYPE");

        toggleLayouts(linearlayoutMyclassExams, textViewMyclassExams);


        linearlayoutClassExm = (LinearLayout) findViewById(R.id.linearlayoutClassExm);
        linearlayoutLectureExm = (LinearLayout) findViewById(R.id.linearlayoutLectureExm);

        relativeLayoutMenu.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                toggle();
            }
        });

        linearlayoutClassExm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamTypeActivity.this,MyClassExamActivity.class);
                startActivity(intent);
            }
        });

        linearlayoutLectureExm.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ExamTypeActivity.this,MyClassExamsActivity.class);
                startActivity(intent);
            }
        });
    }
}
