package com.monkeybusiness.jaaar.Fragment;

import android.app.Dialog;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.Toolbar;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.melnykov.fab.FloatingActionButton;
import com.monkeybusiness.jaaar.Adapter.TestListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

/**
 * Created by rakesh on 18/1/16.
 */
public class TestListFragment extends Fragment implements View.OnClickListener, DatePickerDialog.OnDateSetListener, TimePickerDialog.OnTimeSetListener{


    View rootView;
    FloatingActionButton fabAddTest;

    ListView listViewTest;

    TestListAdapter testListAdapter;

    ArrayList<TestData> testDatas = new ArrayList<>();

    AutoCompleteTextView autoCompleteTextView;

    DateFormat dateFormat;


    String date,time;

    String subject;

    Dialog dialog;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_test_list,container,false);

        initialization();

        testDatas.add(new TestData("01 Jan 2015", "11:00", "Chemistry"));
        testDatas.add(new TestData("01 Jan 2015","11:00","Chemistry"));
        testDatas.add(new TestData("01 Jan 2015","11:00","Chemistry"));
        testDatas.add(new TestData("01 Jan 2015", "11:00", "Chemistry"));
        testDatas.add(new TestData("01 Jan 2015", "11:00", "Chemistry"));

        return rootView;
    }

    public void initialization()
    {

        fabAddTest = (FloatingActionButton) rootView.findViewById(R.id.fabAddTest);
        listViewTest = (ListView) rootView.findViewById(R.id.listViewTest);

        testListAdapter = new TestListAdapter(getActivity(),testDatas);

        listViewTest.setAdapter(testListAdapter);

        fabAddTest.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.fabAddTest:
                showDateDialog();
                break;
            case R.id.buttonCreateTest:

                subject = autoCompleteTextView.getText().toString();
                if (subject.isEmpty()) {
                    Toast.makeText(getActivity(), "Please Enter Subject Name", Toast.LENGTH_SHORT).show();
                } else
                {
                    testDatas.add(new TestData(date,time,subject));
                    testListAdapter.notifyDataSetChanged();
                    dialog.dismiss();
                }
                break;
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
        tpd.show(getActivity().getFragmentManager(), "Timepickerdialog");
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
        dpd.show(getActivity().getFragmentManager(), "Datepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        time = hourOfDay+":"+minute;
        Log.d("TestListFrag","timeSet");
        showSubjectDialog();
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

    public void showSubjectDialog()
    {
        List<String> arrayList  = new ArrayList<>();
        arrayList.add("Physics");
        arrayList.add("Chemistry");
        arrayList.add("Maths");
        arrayList.add("SST");
        arrayList.add("Hindi");

        dialog = new Dialog(getActivity());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        dialog.setContentView(R.layout.dialog_custom_msg);
        autoCompleteTextView = (AutoCompleteTextView) dialog.findViewById(R.id.autoCompleteSubject);

        ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),android.R.layout.simple_list_item_1,arrayList);
        autoCompleteTextView.setAdapter(adapter);

        Button buttonCreateTest = (Button) dialog.findViewById(R.id.buttonCreateTest);

        buttonCreateTest.setOnClickListener(this);
        dialog.setCancelable(true);
        dialog.setCanceledOnTouchOutside(true);
        dialog.show();
    }

}
