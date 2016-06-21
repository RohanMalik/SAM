package com.monkeybusiness.jaaar.Fragment;

import android.app.ProgressDialog;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.TextWatcher;
import android.text.util.Rfc822Tokenizer;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.Adapter.StudentNameAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.addRemarksObject.Remarks;
import com.monkeybusiness.jaaar.objectClasses.addRemarksObject.RemarksRequestObject;
import com.monkeybusiness.jaaar.objectClasses.addRemarksResponseData.AddRemarksResponseData;
import com.monkeybusiness.jaaar.objectClasses.studentSearchdata.SearchStudentData;
import com.monkeybusiness.jaaar.objectClasses.studentSearchdata.Student;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

/**
 * Created by rakesh on 2/2/16.
 */
public class RemarksFragment extends BaseActivity {

    private static final String TAG = "RemarksFragment";
    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    AutoCompleteTextView recipientEditTextView;

    Button buttonSendRemarks;

    EditText editTextRemarks;
    ArrayAdapter adapter;

    GridView gridviewStudentName;

    ArrayList<String> studentList;

    StudentNameAdapter studentNameAdapter;

    public ArrayList<Student> studentNameadapterList = new ArrayList<>();

    public List<Student> studentListObject;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_remarks_student);

        Utils.classFlag = 6;

        toggleLayouts(linearlayoutRemarks, textViewRemarks);

        buttonSendRemarks = (Button) findViewById(R.id.buttonSendRemarks);
        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        editTextRemarks = (EditText) findViewById(R.id.editTextRemarks);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        buttonSendRemarks.setOnClickListener(this);

        textViewActionTitle.setText("Remarks");

        recipientEditTextView = (AutoCompleteTextView) findViewById(R.id.recipientEditTextView);

        studentList = new ArrayList<>();

        adapter = new ArrayAdapter(this, android.R.layout.simple_list_item_1, studentList);

        recipientEditTextView.setAdapter(adapter);
        recipientEditTextView.setThreshold(1);

        gridviewStudentName = (GridView) findViewById(R.id.gridviewStudentName);

        studentNameAdapter = new StudentNameAdapter(this, new ArrayList<>());
        gridviewStudentName.setAdapter(studentNameAdapter);

        gridviewStudentName.setVisibility(View.VISIBLE);


        recipientEditTextView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Log.d(TAG, "Position : " + position + " name : " + studentList.get(position));

                recipientEditTextView.setText("");

                boolean isAvailable = false;

                for (Student student : studentNameAdapter.studentNames)
                {
                    if (student.getId() == studentListObject.get(position).getId())
                    {
                        isAvailable = true;
                    }
                }

                if (!isAvailable)
                {
                    studentNameadapterList.add(studentListObject.get(position));

                    studentNameAdapter = new StudentNameAdapter(RemarksFragment.this, studentNameadapterList);

                    gridviewStudentName.setAdapter(studentNameAdapter);

                }
            }
        });

        recipientEditTextView.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                Log.d(TAG, "text : " + s);
                searchStudentServerCall(s + "");
            }

            @Override
            public void afterTextChanged(Editable s) {

            }
        });

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//        return rootView;
//    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId()) {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.buttonSendRemarks:
                sendRemarksServerCall();
                break;
        }
    }

    private void sendRemarksServerCall() {

        Log.d(TAG, "Array : " + new Gson().toJson(studentNameAdapter.studentNames));

        List<Student> students = studentNameAdapter.studentNames;

        RemarksRequestObject remarksRequestObject = new RemarksRequestObject();

        String remarksStr = editTextRemarks.getText().toString();

        if (remarksStr.equalsIgnoreCase("") || students.isEmpty()) {
            Utils.failureDialog(this, "Warning", "Please enter student name or remarks");
        } else {
            Remarks remarks = new Remarks();
            remarks.setDateOfRemark(ISO8601.fromCalendar(Calendar.getInstance()));
            remarks.setRemark(remarksStr);
            ArrayList<Integer> studentList = new ArrayList<>();

            for (Student student : students) {
                studentList.add(student.getId());
            }

            remarks.setStudents(studentList);

            remarksRequestObject.setRemarks(remarks);

            String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
            String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

            String jsonRemarks = new Gson().toJson(remarksRequestObject);

            ProgressDialog progressDialog = ProgressDialog.show(this, "Please Wait", "Sending Remarks...", false);

            try {
                TypedInput typedInput = new TypedByteArray("application/json", jsonRemarks.getBytes("UTF-8"));
                RestClient.getApiServicePojo(xCookies, aCookies).apiCallSendRemarks(typedInput, new Callback<AddRemarksResponseData>() {
                    @Override
                    public void success(AddRemarksResponseData addRemarksResponseData, Response response) {
                        Log.d(TAG, "Response : " + new Gson().toJson(addRemarksResponseData));
                        progressDialog.dismiss();

                        if (addRemarksResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                            Utils.failureDialog(RemarksFragment.this, "Success", "You have successfully sent remarks");
                        } else {
                            Utils.failureDialog(RemarksFragment.this, "Failure", "Something went wrong, please try again.");
                        }
                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG, "error : " + error.toString());
                        progressDialog.dismiss();
                        Utils.failureDialog(RemarksFragment.this, "Failure", "Something went wrong, please try again.");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }
        }
    }

    public void searchStudentServerCall(String studentName) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiServicePojo(xCookies, aCookies).apiCallSearchStudent(studentName, new Callback<SearchStudentData>() {
            @Override
            public void success(SearchStudentData searchStudentData, Response response) {
                Log.d(TAG, "response : " + new Gson().toJson(searchStudentData));

                if (searchStudentData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes")) {
                    Log.d(TAG, "Student Name:" + new Gson().toJson(studentList));

                    studentListObject = searchStudentData.getData().getStudents();
                    studentList.clear();
                    for (Student student : searchStudentData.getData().getStudents()) {
                        studentList.add(student.getStudentName());
                    }

                    Log.d(TAG, "Student Name:" + new Gson().toJson(studentList));
                    adapter = new ArrayAdapter(RemarksFragment.this, android.R.layout.simple_list_item_1, studentList);
                    recipientEditTextView.setAdapter(adapter);
                }
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG, "error : " + error.toString());
            }
        });
    }
}
