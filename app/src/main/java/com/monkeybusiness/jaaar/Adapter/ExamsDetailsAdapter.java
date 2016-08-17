package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Activity.FillMarksForExamsCategoryActivity;
import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.attendanceResponse.Datum;
import com.monkeybusiness.jaaar.objectClasses.examData.Exam;
import com.monkeybusiness.jaaar.objectClasses.examData.ExamData;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 16/6/16.
 */
public class ExamsDetailsAdapter extends BaseAdapter {

    private static final String TAG = "AttendanceDetailAdapter";
    Context context;
    LayoutInflater inflater;
    ExamData examData;
    int id;
    String examGrpName;

    public ExamsDetailsAdapter(Context context, ExamData examData, String examGrpName) {
        this.context = context;
        this.examData = examData;
        this.examGrpName = examGrpName;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return examData.getData().getExams().size();
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
            view = inflater.inflate(R.layout.adapter_list_item_exams, parent, false);

            holder = new ViewHolder();

            holder.linearLayoutMainItemLecture = (LinearLayout) view.findViewById(R.id.linearLayoutMainItemLecture);
            holder.textViewAbsent = (TextView) view.findViewById(R.id.textViewAbsent);
            holder.textViewPresent = (TextView) view.findViewById(R.id.textViewPresent);
            holder.textViewTotal = (TextView) view.findViewById(R.id.textViewTotal);
            holder.textViewSubmitStatus = (TextView) view.findViewById(R.id.textViewSubmitStatus);
            holder.textViewBatchDate = (TextView) view.findViewById(R.id.textViewBatchDate);
            holder.textViewDesc = (TextView) view.findViewById(R.id.textViewDesc);

            holder.textViewLectureNo = (TextView) view.findViewById(R.id.textViewLectureNo);
            holder.textViewType = (TextView) view.findViewById(R.id.textViewType);

            holder.textViewLectureNo.setTypeface(FontClass.proximaBold(context));
            holder.textViewBatchDate.setTypeface(FontClass.proximaBold(context));
            holder.textViewPresent.setTypeface(FontClass.proximaRegular(context));
            holder.textViewTotal.setTypeface(FontClass.proximaRegular(context));
            holder.textViewSubmitStatus.setTypeface(FontClass.proximaRegular(context));
            holder.textViewAbsent.setTypeface(FontClass.proximaRegular(context));
            holder.textViewDesc.setTypeface(FontClass.proximaRegular(context));
            holder.textViewType.setTypeface(FontClass.proximaRegular(context));

            ASSL.DoMagic(holder.linearLayoutMainItemLecture);

            view.setTag(holder);

        } else {
            holder = (ViewHolder) view.getTag();
        }

        holder.textViewLectureNo.setText(String.valueOf(position + 1) + ". ");

        List<Exam> exams = examData.getData().getExams();

        holder.textViewBatchDate.setText(" " + exams.get(position).getExamName());
        holder.textViewDesc.setText(exams.get(position).getDescription());
        holder.textViewSubmitStatus.setText("Status : " +exams.get(position).getStatus());
        holder.textViewTotal.setText("Date : " + Utils.formatDateAndTime(exams.get(position).getExamDate()));
        holder.textViewPresent.setText(exams.get(position).getMinMarks() + "");
        holder.textViewAbsent.setText(exams.get(position).getMaxMarks()+ "");
        holder.textViewType.setText(examGrpName);
//
        holder.linearLayoutMainItemLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(context, FillMarksForExamsCategoryActivity.class);
                Prefs.with(context).save(PrefsKeys.EXAM_DATA,exams.get(position));
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
        TextView textViewDesc;
        TextView textViewType;

        TextView textViewLectureNo;
    }
}
