package com.monkeybusiness.jaaar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.singleAttdDetailsData.StudentsInfo;

import java.util.ArrayList;
import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 2/2/16.
 */
public class ReviewListAdapter extends BaseAdapter {

    Activity activity;
    List<StudentAttdData> studentAttdDatas;
    LayoutInflater inflater;
    List<Integer> studentId;
    List<StudentsInfo> studentsInfos;

    public ReviewListAdapter(Activity activity, List<Integer> studentId) {
        this.studentId = studentId;
        this.activity = activity;
        studentsInfos = MasterClass.getInstance().getStudentsInfos();
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
//        studentAttdDatas = new ArrayList<>();

//        List<StudentAttdData> studentAttdDatasLocal = MasterClass.getInstance().getStudentAttdDatas();
//
//        for (StudentAttdData studentAttdData : studentAttdDatasLocal)
//        {
//            if (studentAttdData.getsCurrentAttd().equals("0"))
//            {
//                studentAttdDatas.add(studentAttdData);
//            }
//        }
    }

//    public void setData()
//    {
//        studentAttdDatas.clear();
//
//        List<StudentAttdData> studentAttdDatasLocal = MasterClass.getInstance().getStudentAttdDatas();
//
//        for (StudentAttdData studentAttdData : studentAttdDatasLocal)
//        {
//            if (studentAttdData.getsCurrentAttd().equals("0"))
//            {
//                studentAttdDatas.add(studentAttdData);
//            }
//        }
//
//        notifyDataSetChanged();
//    }

    @Override
    public int getCount() {
        return studentId.size();
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

        if (view == null) {
            view = inflater.inflate(R.layout.list_view_review_attd, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNameListView = (TextView) view.findViewById(R.id.textViewNameListView);
            viewHolder.textViewRollnoListView = (TextView) view.findViewById(R.id.textViewRollnoListView1);
            viewHolder.linearLayoutMain = (LinearLayout) view.findViewById(R.id.root);

            view.setTag(viewHolder);

            ASSL.DoMagic(viewHolder.linearLayoutMain);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        int id = studentId.get(position);
        int index = getIndexOfId(id);


        viewHolder.textViewNameListView.setText(studentsInfos.get(index).getStudentName());
        viewHolder.textViewRollnoListView.setText(studentsInfos.get(index).getRollno()+"");


        return view;
    }

    private int getIndexOfId(int id) {

        for (int i = 0; i < studentsInfos.size(); i++) {
            if (studentsInfos.get(i).getId() == id)
            {
                return i;
            }
        }
        return 0;
    }

    public class ViewHolder {
        TextView textViewRollnoListView;
        TextView textViewNameListView;

        LinearLayout linearLayoutMain;
    }
}
