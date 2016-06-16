package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.attendanceResponse.Datum;

import org.w3c.dom.Text;

import java.util.List;

/**
 * Created by rakesh on 16/6/16.
 */
public class AttendanceDetailsAdapter extends BaseAdapter {

    Context context;
    LayoutInflater inflater;
    List<Datum> datumList;

    public AttendanceDetailsAdapter(Context context, List<Datum> datumList)
    {
        this.context = context;
        this.datumList = datumList;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return datumList.size();
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
        ViewHolder holder;

        if (view == null)
        {
            view = inflater.inflate(R.layout.adapter_list_item_attendance,parent,false);

            holder = new ViewHolder();

            holder.linearLayoutMainItemLecture = (LinearLayout) view.findViewById(R.id.linearLayoutMainItemLecture);
            holder.textViewAbsent = (TextView) view.findViewById(R.id.textViewAbsent);
            holder.textViewPresent = (TextView) view.findViewById(R.id.textViewPresent);
            holder.textViewTotal = (TextView) view.findViewById(R.id.textViewTotal);
            holder.textViewSubmitStatus = (TextView) view.findViewById(R.id.textViewSubmitStatus);
            holder.textViewBatchDate = (TextView) view.findViewById(R.id.textViewBatchDate);

            holder.textViewLectureNo = (TextView) view.findViewById(R.id.textViewLectureNo);

            view.setTag(holder);

        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.textViewLectureNo.setText(String.valueOf(position+1)+". ");

        holder.textViewBatchDate.setText("Date : "+datumList.get(position).getDate());
        holder.textViewSubmitStatus.setText("Status : "+ (datumList.get(0).getAttendanceStatus() ? "Submitted":"Not Submitted"));
        holder.textViewTotal.setText("Total Students : "+(datumList.get(0).getAbsent()+datumList.get(0).getPresent()));
        holder.textViewPresent.setText("Present : "+datumList.get(0).getPresent()+"");
        holder.textViewAbsent.setText("Absent : "+datumList.get(0).getAbsent()+"");
        return view;
    }

    public class ViewHolder
    {
        LinearLayout linearLayoutMainItemLecture;

        TextView textViewBatchDate;
        TextView textViewPresent;
        TextView textViewAbsent;
        TextView textViewTotal;
        TextView textViewSubmitStatus;

        TextView textViewLectureNo;
    }
}
