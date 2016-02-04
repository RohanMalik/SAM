package com.monkeybusiness.jaaar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by rakesh on 2/2/16.
 */
public class ReviewListAdapter extends BaseAdapter {

    Activity activity;
    List<StudentAttdData> studentAttdDatas;
    LayoutInflater inflater;

    public ReviewListAdapter(Activity activity)
    {
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        studentAttdDatas = new ArrayList<>();

        List<StudentAttdData> studentAttdDatasLocal = MasterClass.getInstance().getStudentAttdDatas();

        for (StudentAttdData studentAttdData : studentAttdDatasLocal)
        {
            if (studentAttdData.getsCurrentAttd().equals("0"))
            {
                studentAttdDatas.add(studentAttdData);
            }
        }
    }

    public void setData()
    {
        studentAttdDatas.clear();

        List<StudentAttdData> studentAttdDatasLocal = MasterClass.getInstance().getStudentAttdDatas();

        for (StudentAttdData studentAttdData : studentAttdDatasLocal)
        {
            if (studentAttdData.getsCurrentAttd().equals("0"))
            {
                studentAttdDatas.add(studentAttdData);
            }
        }

        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return 10;
    }

    @Override
    public Object getItem(int position) {
        return position;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        View view = convertView;
        ViewHolder viewHolder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.list_view_review_attd,parent,false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNameListView = (TextView) view.findViewById(R.id.textViewNameListView);
            viewHolder.textViewRollnoListView = (TextView) view.findViewById(R.id.textViewRollnoListView);

            view.setTag(viewHolder);
        }
        else
        {
            viewHolder = (ViewHolder) view.getTag();
        }

//        viewHolder.textViewNameListView.setText(""+studentAttdDatas.get(position).getsName());
//        viewHolder.textViewRollnoListView.setText(""+studentAttdDatas.get(position).getsRollNo());

        return view;
    }

    public class ViewHolder
    {
        TextView textViewRollnoListView;
        TextView textViewNameListView;
    }
}
