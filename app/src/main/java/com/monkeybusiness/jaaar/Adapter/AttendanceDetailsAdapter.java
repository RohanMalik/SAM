package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.attendanceResponse.Datum;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 16/6/16.
 */
public class AttendanceDetailsAdapter extends BaseAdapter {

    private static final String TAG = "AttendanceDetailAdapter";
    Context context;
    LayoutInflater inflater;
    List<Datum> datumList;
    int id;

    public AttendanceDetailsAdapter(Context context, List<Datum> datumList, int id) {
        this.id = id;
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

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_list_item_attendance, parent, false);

            holder = new ViewHolder();

            holder.linearLayoutMainItemLecture = (LinearLayout) view.findViewById(R.id.linearLayoutMainItemLecture);
            holder.textViewAbsent = (TextView) view.findViewById(R.id.textViewAbsent);
            holder.textViewPresent = (TextView) view.findViewById(R.id.textViewPresent);
            holder.textViewTotal = (TextView) view.findViewById(R.id.textViewTotal);
            holder.textViewSubmitStatus = (TextView) view.findViewById(R.id.textViewSubmitStatus);
            holder.textViewBatchDate = (TextView) view.findViewById(R.id.textViewBatchDate);

            holder.textViewLectureNo = (TextView) view.findViewById(R.id.textViewLectureNo);

            holder.textViewLectureNo.setTypeface(FontClass.proximaBold(context));
            holder.textViewBatchDate.setTypeface(FontClass.proximaBold(context));
            holder.textViewPresent.setTypeface(FontClass.proximaRegular(context));
            holder.textViewTotal.setTypeface(FontClass.proximaRegular(context));
            holder.textViewSubmitStatus.setTypeface(FontClass.proximaRegular(context));
            holder.textViewAbsent.setTypeface(FontClass.proximaRegular(context));

            ASSL.DoMagic(holder.linearLayoutMainItemLecture);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textViewLectureNo.setText(String.valueOf(position + 1) + ". ");

        holder.textViewBatchDate.setText("Date : " + datumList.get(position).getDate());
        holder.textViewSubmitStatus.setText("Status : " + datumList.get(position).getAttendanceStatus());
        holder.textViewTotal.setText("Total Students : " + (datumList.get(position).getAbsent() + datumList.get(position).getPresent()));
        holder.textViewPresent.setText(datumList.get(position).getPresent() + "");
        holder.textViewAbsent.setText(datumList.get(position).getAbsent() + "");

        holder.linearLayoutMainItemLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, AttendanceFragment.class);
                intent.putExtra(Constants.BATCH_ID, id);
                intent.putExtra(Constants.DATE, datumList.get(position).getDate());
                context.startActivity(intent);
            }
        });
        return view;
    }

    public class ViewHolder {
        LinearLayout linearLayoutMainItemLecture;

        TextView textViewBatchDate;
        TextView textViewPresent;
        TextView textViewAbsent;
        TextView textViewTotal;
        TextView textViewSubmitStatus;

        TextView textViewLectureNo;
    }
}
