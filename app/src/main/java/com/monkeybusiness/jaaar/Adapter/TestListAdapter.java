package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.TestData;

import java.util.List;

/**
 * Created by rakesh on 19/1/16.
 */
public class TestListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<TestData> testDatas;

    public TestListAdapter(Context context,List<TestData> testDatas)
    {
        this.context = context;
        this.testDatas = testDatas;
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

        if (view==null)
        {
            view = inflater.inflate(R.layout.adapter_test_item,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.subjectName = (TextView) view.findViewById(R.id.subjectName);
            viewHolder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            viewHolder.textViewTime = (TextView) view.findViewById(R.id.textViewTime);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewTime.setText(testDatas.get(position).getTime());
        viewHolder.textViewDate.setText(testDatas.get(position).getDate());
        viewHolder.subjectName.setText(testDatas.get(position).getSubjectName());

        return view;
    }

    public class ViewHolder
    {
        TextView subjectName;
        TextView textViewDate;
        TextView textViewTime;
    }

}
