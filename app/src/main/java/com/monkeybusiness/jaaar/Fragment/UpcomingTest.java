package com.monkeybusiness.jaaar.Fragment;

import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.AddTestActivity;
import com.monkeybusiness.jaaar.Adapter.TestListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.TestFragmentListner;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.utils.Constants;
import com.rey.material.widget.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 2/6/16.
 */
public class UpcomingTest extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener, TestFragmentListner {

    private static final String TAG = "UpcomingTest";
    String title;
    int pageNo;

    FloatingActionButton fabAddTest;

    ListView listViewTest;

    TestListAdapter testListAdapter;

    AutoCompleteTextView autoCompleteTextView;

    DateFormat dateFormat;

    String date, time;

    String subject;
    String topic;

    Dialog subjectDialog;
    Dialog topicDialog;

//    RelativeLayout relativeLayoutMenu;
//    TextView textViewActionTitle;

    boolean nextButton;
    Button buttonNext;
    Button buttonCreateTest;
    private Context context;

    public static UpcomingTest newInstance(int page, String title) {
        UpcomingTest fragmentFirst = new UpcomingTest();
        Bundle args = new Bundle();
        args.putInt("pageNo", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_list, container, false);

        new ASSL(getActivity(), (ViewGroup) view.findViewById(R.id.root), 1134, 720,
                false);

        context = getActivity().getApplicationContext();

        initialization(view);

        return view;
    }

    public void initialization(View view) {

        fabAddTest = (FloatingActionButton) view.findViewById(R.id.fabAddTest);
        listViewTest = (ListView) view.findViewById(R.id.listViewTest);

//        testListAdapter = new TestListAdapter(context, testDatas);
//
//        listViewTest.setAdapter(testListAdapter);

        fabAddTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddTest:
                Intent intent = new Intent(getContext(), AddTestActivity.class);
                startActivity(intent);
                break;
//            case R.id.buttonCreateTest:
//                if (nextButton) {
//                    subject = autoCompleteTextView.getText().toString();
//                    if (subject.isEmpty()) {
//                        Toast.makeText(context, "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
//                    } else {
//                        subjectDialog.dismiss();
//                        nextButton = false;
//                        showTopicDialog();
//
//                    }
//                } else {
//                    topic = autoCompleteTextView.getText().toString();
//                    if (topic.isEmpty()) {
//                        Toast.makeText(context, "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
//                    } else {
//                        testDatas.add(new TestData(date, time, subject, topic));
//                        testListAdapter.notifyDataSetChanged();
//                        topicDialog.dismiss();
//                    }
//                }
//                break;
        }
    }

    public void showTimeDialog() {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setAccentColor(getResources().getColor(R.color.primary));
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
    }

    public void showDateDialog() {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.primary));
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    public void showSubjectDialog() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("Physics");
        arrayList.add("Chemistry");
        arrayList.add("Maths");
        arrayList.add("SST");
        arrayList.add("Hindi");

        nextButton = true;

        subjectDialog = new Dialog(getActivity());
        subjectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        subjectDialog.setContentView(R.layout.dialog_custom_msg_test);
//        autoCompleteTextView = (AutoCompleteTextView) subjectDialog.findViewById(R.id.autoCompleteSubject);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        autoCompleteTextView.setAdapter(adapter);

//        buttonNext = (Button) subjectDialog.findViewById(R.id.buttonCreateTest);

        buttonNext.setText("Next");
        buttonNext.setOnClickListener(this);
        subjectDialog.setCancelable(true);
        subjectDialog.setCanceledOnTouchOutside(true);
        subjectDialog.show();
    }

    public void showTopicDialog() {
        List<String> arrayList = new ArrayList<>();
        arrayList.add("GEOMETRY");
        arrayList.add("PROBABILITY");
        arrayList.add("TRIGONOMETRY");
        arrayList.add("ALGEBRA");
        arrayList.add("NUMBER SYSTEMS");

        topicDialog = new Dialog(getActivity());
        topicDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        topicDialog.setContentView(R.layout.dialog_custom_msg_test);
//        autoCompleteTextView = (AutoCompleteTextView) topicDialog.findViewById(R.id.autoCompleteSubject);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(context, android.R.layout.simple_list_item_1, arrayList);
        autoCompleteTextView.setAdapter(adapter);

        autoCompleteTextView.setHint("Enter Topic Name");

//        buttonCreateTest = (Button) topicDialog.findViewById(R.id.buttonCreateTest);

        buttonCreateTest.setOnClickListener(this);
        topicDialog.setCancelable(true);
        topicDialog.setCanceledOnTouchOutside(true);
        topicDialog.show();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar mDate;
        mDate = Calendar.getInstance();
        mDate.set(year, monthOfYear, dayOfMonth);
        date = dateFormat.format(mDate.getTime());

        Log.d("TestListFrag", "DateSet : " + date);
        showTimeDialog();
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {
        time = hourOfDay + ":" + minute;
        Log.d("TestListFrag", "timeSet");
        showSubjectDialog();
    }

    @Override
    public void onResumeFragment(Context context, List<Test> tests) {
        Log.d(TAG, "OnResumeFragment context : " + context);

        for (Test test : tests) {
            Log.d(TAG, "Test : " + new Gson().toJson(test));
        }

        testListAdapter = new TestListAdapter(context, tests,"FILL MARKS");
        listViewTest.setAdapter(testListAdapter);
        listViewTest.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                com.monkeybusiness.jaaar.utils.Log.d(TAG,"position ; "+position);
                Intent intent = new Intent(context,AddTestActivity.class);
                intent.putExtra(Constants.TEST_ID,tests.get(position).getId());
                intent.putExtra(Constants.LECTURE_ID,tests.get(position).getLecture().getLectureName());
                intent.putExtra(Constants.DATE,tests.get(position).getTestDate());
                intent.putExtra(Constants.MIN_MARKS,tests.get(position).getMinMarks());
                intent.putExtra(Constants.MAX_MARKS,tests.get(position).getMaxMarks());
                intent.putExtra(Constants.DURATION,tests.get(position).getDurationMinutes());
                intent.putExtra(Constants.TEST_NAME,tests.get(position).getTestName());
                startActivity(intent);
            }
        });
    }
}
