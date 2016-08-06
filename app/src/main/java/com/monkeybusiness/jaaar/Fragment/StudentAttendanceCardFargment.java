package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.github.florent37.hollyviewpager.HollyViewPagerBus;
import com.github.ksoichiro.android.observablescrollview.ObservableScrollView;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.SingleIdDetail;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;
import com.monkeybusiness.jaaar.utils.FontClass;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 1/1/16.
 */
public class StudentAttendanceCardFargment extends Fragment implements View.OnClickListener {

    View rootView;

//    ImageView attdP1;
//    ImageView attdP2;
//    ImageView attdP3;
//    ImageView attdP4;
//    ImageView attdP5;

    TextView textViewTitle;
    TextView textViewClass;
    TextView textViewRollNo;
    Button buttonAbsent;

    StudentAttdData studentAttdData;

    SingleIdDetail singleIdDetails;
    StudentsInfo studentsInfos;
    String classAlias;

    AttendanceFragment attendanceFragment;

    ObservableScrollView scrollView;
    TextDrawable drawableAbsent;
    TextDrawable drawablePresent;

    public static StudentAttendanceCardFargment newInstance(int page, String title) {

        StudentAttendanceCardFargment studentAttendanceCardFargment = new StudentAttendanceCardFargment();

//        studentAttendanceCardFargment.studentAttdData = MasterClass.getInstance().getStudentAttdDatas().get(page);
        studentAttendanceCardFargment.singleIdDetails = MasterClass.getInstance().getSingleIdDetails().get(page);
        studentAttendanceCardFargment.studentsInfos = MasterClass.getInstance().getStudentsInfos().get(page);
        studentAttendanceCardFargment.classAlias = MasterClass.getInstance().getClassAlias();

        Bundle args = new Bundle();
        args.putString("title", title);
        studentAttendanceCardFargment.setArguments(args);
        return studentAttendanceCardFargment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student_card, container, false);


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

        scrollView = (ObservableScrollView) rootView.findViewById(R.id.scrollView);

        buttonAbsent = (Button) rootView.findViewById(R.id.buttonAbsent);

        buttonAbsent.setOnClickListener(this);

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

        if (singleIdDetails.getStatus() != null) {
            if (singleIdDetails.getStatus().equals("A")) {
                buttonAbsent.setText("MARK PRESENT");
                buttonAbsent.setBackgroundColor(getResources().getColor(R.color.normal_absent_button));
            } else {
                buttonAbsent.setText("MARK ABSENT");
                buttonAbsent.setBackgroundColor(getResources().getColor(R.color.primary));
            }
        }

        textViewTitle.setText(studentsInfos.getStudentName());
        textViewRollNo.setText("Roll No " + studentsInfos.getRollno());
        textViewClass.setText("Class " + classAlias);

        setFont();

        return rootView;
    }

    private void setFont() {
        textViewTitle.setTypeface(FontClass.proximaBold(getContext()));
        textViewClass.setTypeface(FontClass.proximaRegular(getContext()));
        textViewRollNo.setTypeface(FontClass.proximaRegular(getContext()));
        buttonAbsent.setTypeface(FontClass.proximaBold(getContext()));
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
            case R.id.buttonAbsent:
                toggleAttd(buttonAbsent);
//                attendanceFragment.viewPagerAttd.setCurrentItem(attendanceFragment.viewPagerAttd.getCurrentItem()+1);
                break;
        }
    }


    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        HollyViewPagerBus.registerScrollView(getActivity(), scrollView);
    }

    public void toggleAttd(Button button) {
        if (button.getText().toString().equalsIgnoreCase("mark absent")) {
            Log.d("abc", "presentKaro");
//            studentAttdData.setsCurrentAttd("0");
            singleIdDetails.setStatus("A");
            button.setText("MARK PRESENT");
            button.setBackgroundColor(getResources().getColor(R.color.normal_absent_button));
        } else {
            Log.d("abc", "absent karo");
            singleIdDetails.setStatus("P");
//            studentAttdData.setsCurrentAttd("1");
            button.setText("MARK ABSENT");
            button.setBackgroundColor(getResources().getColor(R.color.primary));
        }
    }
}
