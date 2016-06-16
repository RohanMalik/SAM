package com.monkeybusiness.jaaar.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.Adapter.TestListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.monkeybusiness.jaaar.utils.Utils;
import com.rey.material.widget.FloatingActionButton;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rakesh on 18/1/16.
 */
public class TestListFragment extends BaseActivity implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{

    FloatingActionButton fabAddTest;

    ListView listViewTest;

    TestListAdapter testListAdapter;

    ArrayList<TestData> testDatas = new ArrayList<>();

    AutoCompleteTextView autoCompleteTextView;

    DateFormat dateFormat;

    String date,time;

    String subject;
    String topic;

    Dialog subjectDialog;
    Dialog topicDialog;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    boolean nextButton;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_test_list);

        Utils.classFlag = 3;

        toggleLayouts(linearlayoutTest,textViewTest);

        initialization();

        testDatas.add(new TestData("01 Jan 2015", "11:00", "Maths", "GEOMETRY"));
        testDatas.add(new TestData("01 Jan 2015", "11:00", "Maths","Trigonometry"));
        testDatas.add(new TestData("01 Jan 2015","11:00","Maths","TRIGONOMETRY"));
        testDatas.add(new TestData("01 Jan 2015", "11:00", "Maths","ALGEBRA"));
        testDatas.add(new TestData("01 Jan 2015", "11:00", "Maths","NUMBER SYSTEMS"));

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//        rootView = inflater.inflate(R.layout.fragment_test_list,container,false);
//
//        initialization();
//
//        testDatas.add(new TestData("01 Jan 2015", "11:00", "Chemistry"));
//        testDatas.add(new TestData("01 Jan 2015","11:00","Maths"));
//        testDatas.add(new TestData("01 Jan 2015","11:00","Physics"));
//        testDatas.add(new TestData("01 Jan 2015", "11:00", "Hindi"));
//        testDatas.add(new TestData("01 Jan 2015", "11:00", "English"));
//
//        return rootView;
//    }

    public void initialization()
    {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        fabAddTest = (FloatingActionButton) findViewById(R.id.fabAddTest);
        listViewTest = (ListView) findViewById(R.id.listViewTest);

//        testListAdapter = new TestListAdapter(this,testDatas);

        listViewTest.setAdapter(testListAdapter);

        fabAddTest.setOnClickListener(this);
        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("Test");
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.fabAddTest:
                showDateDialog();
                break;
//            case R.id.buttonCreateTest:
//                if (nextButton)
//                {
//                    subject = autoCompleteTextView.getText().toString();
//                    if (subject.isEmpty()) {
//                        Toast.makeText(this, "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
//                    } else
//                    {
//                        subjectDialog.dismiss();
//                        nextButton = false;
//                        showTopicDialog();
//
//                    }
//                }
//                else
//                {
//                    topic = autoCompleteTextView.getText().toString();
//                    if (topic.isEmpty()) {
//                        Toast.makeText(this, "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
//                    } else
//                    {
//                        testDatas.add(new TestData(date,time,subject,topic));
//                        testListAdapter.notifyDataSetChanged();
//                        topicDialog.dismiss();
//                    }
//                }
//                break;
        }
    }

    public void showTimeDialog()
    {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.setAccentColor(getResources().getColor(R.color.primary));
        tpd.show(this.getFragmentManager(), "Timepickerdialog");
    }

    public void showDateDialog()
    {
        Calendar now = Calendar.getInstance();
        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );
        dpd.setAccentColor(getResources().getColor(R.color.primary));
        dpd.show(this.getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        time = hourOfDay+":"+minute;
        Log.d("TestListFrag","timeSet");
//        showSubjectDialog();
    }

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {

        dateFormat = new SimpleDateFormat("dd MMM yyyy");
        Calendar mDate;
        mDate = Calendar.getInstance();
        mDate.set(year, monthOfYear, dayOfMonth);
        date = dateFormat.format(mDate.getTime());

        Log.d("TestListFrag","DateSet : "+date);
        showTimeDialog();
    }

    Button buttonNext;
//    public void showSubjectDialog()
//    {
//        List<String> arrayList  = new ArrayList<>();
//        arrayList.add("Physics");
//        arrayList.add("Chemistry");
//        arrayList.add("Maths");
//        arrayList.add("SST");
//        arrayList.add("Hindi");
//
//        nextButton = true;
//
//        subjectDialog = new Dialog(this);
//        subjectDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        subjectDialog.setContentView(R.layout.dialog_custom_msg_test);
//        autoCompleteTextView = (AutoCompleteTextView) subjectDialog.findViewById(R.id.autoCompleteSubject);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
//        autoCompleteTextView.setAdapter(adapter);
//
//        buttonNext = (Button) subjectDialog.findViewById(R.id.buttonCreateTest);
//
//        buttonNext.setText("Next");
//        buttonNext.setOnClickListener(this);
//        subjectDialog.setCancelable(true);
//        subjectDialog.setCanceledOnTouchOutside(true);
//        subjectDialog.show();
//    }

    Button buttonCreateTest;

//    public void showTopicDialog()
//    {
//        List<String> arrayList  = new ArrayList<>();
//        arrayList.add("GEOMETRY");
//        arrayList.add("PROBABILITY");
//        arrayList.add("TRIGONOMETRY");
//        arrayList.add("ALGEBRA");
//        arrayList.add("NUMBER SYSTEMS");
//
//        topicDialog = new Dialog(this);
//        topicDialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
//        topicDialog.setContentView(R.layout.dialog_custom_msg_test);
//        autoCompleteTextView = (AutoCompleteTextView) topicDialog.findViewById(R.id.autoCompleteSubject);
//
//        ArrayAdapter<String> adapter = new ArrayAdapter<String>(this,android.R.layout.simple_list_item_1,arrayList);
//        autoCompleteTextView.setAdapter(adapter);
//
//        autoCompleteTextView.setHint("Enter Topic Name");
//
//        buttonCreateTest = (Button) topicDialog.findViewById(R.id.buttonCreateTest);
//
//        buttonCreateTest.setOnClickListener(this);
//        topicDialog.setCancelable(true);
//        topicDialog.setCanceledOnTouchOutside(true);
//        topicDialog.show();
//    }

}
