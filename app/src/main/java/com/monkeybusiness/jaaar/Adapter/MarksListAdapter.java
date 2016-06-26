package com.monkeybusiness.jaaar.Adapter;

import android.app.Activity;
import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;
import com.monkeybusiness.jaaar.objectClasses.addMarksData.Student;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.StudentDetailsForMarksResponse;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 2/2/16.
 */
public class MarksListAdapter extends BaseAdapter {

    Activity activity;
    List<StudentAttdData> studentAttdDatas;
    LayoutInflater inflater;
    List<Student> studentsForMarks;
    StudentDetailsForMarksResponse studentDetailsForMarksResponse;

    public MarksListAdapter(Activity activity, List<Student> studentsForMarks) {

        Log.d("MarksListAdapter","Students size : "+studentsForMarks.size());

        this.studentsForMarks = studentsForMarks;
        this.activity = activity;
        inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        studentDetailsForMarksResponse = Prefs.with(activity).getObject(PrefsKeys.STUDENT_DATA_TEST, StudentDetailsForMarksResponse.class);

        Log.d("MarksListAdapter","studentsfor marks : "+studentDetailsForMarksResponse.getData().getStudents().size());

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
        return studentsForMarks.size();
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
            view = inflater.inflate(R.layout.list_view_review_marks, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewNameListView = (TextView) view.findViewById(R.id.textViewNameListView);
            viewHolder.textViewRollnoListView = (TextView) view.findViewById(R.id.textViewRollnoListView1);
            viewHolder.textViewMarksListView = (TextView) view.findViewById(R.id.textViewMarksListView);
            viewHolder.linearLayoutRoot = (LinearLayout) view.findViewById(R.id.root);

            ASSL.DoMagic(viewHolder.linearLayoutRoot);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }
//
//        int id = studentId.get(position);
//        int index = getIndexOfId(id);

        viewHolder.textViewNameListView.setText(studentDetailsForMarksResponse.getData().getStudents().get(position).getStudentName()+"");
        viewHolder.textViewRollnoListView.setText(studentDetailsForMarksResponse.getData().getStudents().get(position).getRollno()+"");
        if (studentsForMarks.get(position).getMarks()!=null)
        {
            viewHolder.textViewMarksListView.setText(studentsForMarks.get(position).getMarks()+"");
        }


        return view;
    }


    public class ViewHolder {
        TextView textViewRollnoListView;
        TextView textViewNameListView;
        TextView textViewMarksListView;

        LinearLayout linearLayoutRoot;
    }
}
