package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.FillMarksActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.TestData;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;

import java.text.ParseException;
import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 19/1/16.
 */
public class TestListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Test> testDatas;
    String buttonName;

    public TestListAdapter(Context context, List<Test> testDatas, String buttonName) {
        this.context = context;
        this.testDatas = testDatas;
        this.buttonName = buttonName;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return testDatas.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
    }

    @Override
    public long getItemId(int position) {
        return 0;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_test_item, parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.subjectName = (TextView) view.findViewById(R.id.subjectName);
//            viewHolder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            viewHolder.textViewTime = (TextView) view.findViewById(R.id.textViewDate);
            viewHolder.topicName = (TextView) view.findViewById(R.id.topicName);
            viewHolder.buttonFillMarksStudent = (Button) view.findViewById(R.id.buttonFillMarksStudent);
            viewHolder.linearLayoutRoot = (RelativeLayout) view.findViewById(R.id.root);

            viewHolder.textViewTime.setTypeface(FontClass.proximaRegular(context));
            viewHolder.topicName.setTypeface(FontClass.proximaRegular(context));
            viewHolder.buttonFillMarksStudent.setTypeface(FontClass.proximaRegular(context));

            ASSL.DoMagic(viewHolder.linearLayoutRoot);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewTime.setText(Utils.formatDateAndTime(testDatas.get(position).getTestDate()));
//        viewHolder.textViewDate.setText(testDatas.get(position).getDate());
        viewHolder.topicName.setText(testDatas.get(position).getTestName());
        viewHolder.buttonFillMarksStudent.setText(buttonName);
        viewHolder.buttonFillMarksStudent.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FillMarksActivity.class);
                Prefs.with(context).save(PrefsKeys.TEST_DATA,testDatas.get(position));
                context.startActivity(intent);
            }
        });

        return view;
    }

    public class ViewHolder {
        TextView subjectName;
        //        TextView textViewDate;
        TextView textViewTime;
        TextView topicName;
        Button buttonFillMarksStudent;

        RelativeLayout linearLayoutRoot;
    }

}
