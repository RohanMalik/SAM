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
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.ListView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Activity.AddTestActivity;
import com.monkeybusiness.jaaar.Adapter.TestListAdapter;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.interfaces.TestFragmentListner;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.rey.material.widget.FloatingActionButton;

import java.text.DateFormat;
import java.util.List;

/**
 * Created by rakesh on 2/6/16.
 */
public class PendingTestFragment extends Fragment implements TestFragmentListner,View.OnClickListener{

    private static final String TAG = "PendingTest";

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

    public static PendingTestFragment newInstance(int page, String title) {
        PendingTestFragment fragmentFirst = new PendingTestFragment();
        Bundle args = new Bundle();
        args.putInt("pageNo", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_list,container,false);
        context = getActivity().getApplicationContext();

        initialization(view);
        return view;
    }

    private void initialization(View view) {

        fabAddTest = (FloatingActionButton) view.findViewById(R.id.fabAddTest);
        listViewTest = (ListView) view.findViewById(R.id.listViewTest);

//        testListAdapter = new TestListAdapter(context, testDatas);
//
//        listViewTest.setAdapter(testListAdapter);

        fabAddTest.setOnClickListener(this);
    }

    @Override
    public void onResumeFragment(Context context, List<Test> tests) {
        for (Test test : tests)
        {
            Log.d(TAG,"Test : "+new Gson().toJson(test));
        }

        testListAdapter = new TestListAdapter(context, tests);
        listViewTest.setAdapter(testListAdapter);

//        testListAdapter = new TestListAdapter(context,tests);
//        listViewTest.setAdapter(testListAdapter);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.fabAddTest:
                Intent intent = new Intent(getContext(), AddTestActivity.class);
                startActivity(intent);
                break;
            case R.id.buttonCreateTest:
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
                break;
        }
    }
}
