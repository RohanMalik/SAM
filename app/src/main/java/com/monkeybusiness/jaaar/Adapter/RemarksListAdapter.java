package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.studentRemarksData.Remark;
import com.monkeybusiness.jaaar.objectClasses.studentRemarksData.StudentsRemarksResponse;
import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.Utils;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 19/1/16.
 */
public class RemarksListAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Remark> remarks;

    public RemarksListAdapter(Context context, List<Remark> remarks) {
        this.context = context;
        this.remarks = remarks;
        for (Remark remark : remarks)
        {
            Log.d("adapter","remark : "+new Gson().toJson(remark));
        }
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return remarks.size();
    }

    @Override
    public Object getItem(int position) {
        return null;
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
            view = inflater.inflate(R.layout.adapter_remark_item, parent, false);
            viewHolder = new ViewHolder();
//            viewHolder.subjectName = (TextView) view.findViewById(R.id.subjectName);
//            viewHolder.textViewDate = (TextView) view.findViewById(R.id.textViewDate);
            viewHolder.textViewTime = (TextView) view.findViewById(R.id.textViewTime);
            viewHolder.topicName = (TextView) view.findViewById(R.id.topicName);
            viewHolder.linearLayoutMainRoot = (LinearLayout) view.findViewById(R.id.linearLayoutMainRoot);

            ASSL.DoMagic(viewHolder.linearLayoutMainRoot);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

//        Log.d("getView","getting view : "+getCount());

        viewHolder.topicName.setText(Utils.formatDateAndTime(remarks.get(position).getDateOfRemark()));
//        viewHolder.textViewDate.setText(testDatas.get(position).getDate());
        viewHolder.textViewTime.setText(remarks.get(position).getRemark());

        return view;
    }

    public class ViewHolder {
        TextView subjectName;
        //        TextView textViewDate;
        TextView textViewTime;
        TextView topicName;
        LinearLayout linearLayoutMainRoot;
    }

}
