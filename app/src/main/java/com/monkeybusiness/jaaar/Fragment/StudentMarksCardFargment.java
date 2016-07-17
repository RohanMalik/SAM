package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.Student;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.objectClasses.testMarksResponseData.TestMark;
import com.monkeybusiness.jaaar.objectClasses.testMarksResponseData.TestMarksResponseData;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Spinner;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 1/1/16.
 */
public class StudentMarksCardFargment extends Fragment implements View.OnClickListener {

    View rootView;

//    ImageView attdP1;
//    ImageView attdP2;
//    ImageView attdP3;
//    ImageView attdP4;
//    ImageView attdP5;

    TextView textViewTitle;
    TextView textViewClass;
    TextView textViewRollNo;

    EditText editTextRemarks;

    String classAlias;


    Student student;

    AttendanceFragment attendanceFragment;

    ObservableScrollView scrollView;

    TestMarksResponseData testMarksResponseData;
    TextDrawable drawableAbsent;
    TextDrawable drawablePresent;
    TextView textViewMarks;
    EditText editTextFillMarks;
    Test test;

    Spinner testApplicability;

    boolean marksChanged;

    com.monkeybusiness.jaaar.objectClasses.addMarksData.Student studentForMarks;

    public static StudentMarksCardFargment newInstance(int page, String title) {

        StudentMarksCardFargment studentAttendanceCardFargment = new StudentMarksCardFargment();

//        studentAttendanceCardFargment.studentAttdData = MasterClass.getInstance().getStudentAttdDatas().get(page);

        studentAttendanceCardFargment.student = MasterClass.getInstance().getStudents().get(page);
        studentAttendanceCardFargment.classAlias = MasterClass.getInstance().getClassAlias();

        Bundle args = new Bundle();
        args.putString("title", title);
        studentAttendanceCardFargment.setArguments(args);
        return studentAttendanceCardFargment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student_marks, container, false);


        new ASSL(getActivity(), (ViewGroup) rootView.findViewById(R.id.scrollView), 1134, 720,
                false);

        attendanceFragment = MasterClass.getInstance().getAttendanceFragment();
//
//        attdP1 = (ImageView) rootView.findViewById(R.id.attdP1);
//        attdP2 = (ImageView) rootView.findViewById(R.id.attdP2);
//        attdP3 = (ImageView) rootView.findViewById(R.id.attdP3);
//        attdP4 = (ImageView) rootView.findViewById(R.id.attdP4);
//        attdP5 = (ImageView) rootView.findViewById(R.id.attdP5);

        textViewTitle = (TextView) rootView.findViewById(R.id.textViewTitle);
        textViewClass = (TextView) rootView.findViewById(R.id.textViewClass);
        textViewRollNo = (TextView) rootView.findViewById(R.id.textViewRollNo);
        textViewMarks = (TextView) rootView.findViewById(R.id.textViewMarks);
        editTextFillMarks = (EditText) rootView.findViewById(R.id.editTextFillMarks);

        testApplicability = (Spinner) rootView.findViewById(R.id.testApplicability);

        editTextRemarks = (EditText) rootView.findViewById(R.id.editTextRemarks);

        testMarksResponseData = Prefs.with(this.getContext()).getObject(PrefsKeys.TEST_MARKS_DATA, TestMarksResponseData.class);

        test = Prefs.with(this.getContext()).getObject(PrefsKeys.TEST_DATA, Test.class);

        int marks = 0;
        String remarks = "";

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this.getActivity(),
                R.array.test_applicable_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        testApplicability.setAdapter(adapter);

        testApplicability.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int position, long id) {

                Log.d("abc", "Position : " + position);
                if (position == 1) {
                    studentForMarks.setMarks(-777);
                    editTextRemarks.setEnabled(false);
                    editTextFillMarks.setEnabled(false);
                } else if (position == 2) {
                    studentForMarks.setMarks(-999);
                    editTextRemarks.setEnabled(false);
                    editTextFillMarks.setEnabled(false);
                } else {
                    editTextRemarks.setEnabled(true);
                    editTextFillMarks.setEnabled(true);
                    if (!editTextFillMarks.getText().toString().equalsIgnoreCase(""))
                    {
                        studentForMarks.setMarks(Integer.parseInt(editTextFillMarks.getText().toString()));
                    }
//
                }
                return true;
            }
        });

        for (TestMark testMark : testMarksResponseData.getData().getTestMarks())
        {
            if (student.getId() == testMark.getStudentId())
            {
                marks = testMark.getMarks();
                remarks = testMark.getRemarks();
            }
        }

        if (marks == -777)
        {
            testApplicability.setSelection(1);
            editTextRemarks.setEnabled(false);
            editTextFillMarks.setEnabled(false);
        }else if (marks == -999)
        {
            testApplicability.setSelection(2);
            editTextRemarks.setEnabled(false);
            editTextFillMarks.setEnabled(false);
        }else {
            editTextFillMarks.setText(String.valueOf(marks));
            editTextRemarks.setText(remarks);
        }

        textViewMarks.setText("/"+test.getMaxMarks());

        Log.d("StudentMark","test : "+new Gson().toJson(test));

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.scrollView);


        drawableAbsent = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound("A", getResources().getColor(R.color.absent_button));

        drawablePresent = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound("P", getResources().getColor(R.color.normal_present_button));

//        setStudentRecord(attdP1,0);
//        setStudentRecord(attdP2,1);
//        setStudentRecord(attdP3,2);
//        setStudentRecord(attdP4,3);
//        setStudentRecord(attdP5,4);

        textViewTitle.setText(student.getStudentName());
        textViewRollNo.setText("Roll No " + student.getRollno());
        textViewClass.setText("Class " + classAlias);

        studentForMarks = new com.monkeybusiness.jaaar.objectClasses.addMarksData.Student();
        studentForMarks.setRemarks(remarks);
        studentForMarks.setStudentId(student.getId());
        studentForMarks.setMarks(marks);

        MasterClass.getInstance().getStudentsForMarks().add(studentForMarks);

        editTextRemarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0)
                {
                    studentForMarks.setRemarks(String.valueOf(s));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        editTextFillMarks.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                if (s.length()!=0)
                {
                    studentForMarks.setMarks(Integer.parseInt(String.valueOf(s)));
                }
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

        return rootView;
    }

//    public void setStudentRecord(ImageView imageView, int pos)
//    {
//        if (studentAttdData.getRecentRecord().get(pos).equals("0"))
//        {
//            imageView.setImageDrawable(drawableAbsent);
//        }
//        else
//        {
//            imageView.setImageDrawable(drawablePresent);
//        }
//    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);
    }
}
