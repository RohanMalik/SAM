package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Activity.AllExamsActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.ExamGroupData.ExamGroupData;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.FontClass;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 4/6/16.
 */
public class ExamGroupAdapter extends BaseAdapter {

    private static final String TAG = "BatchesAdapter";
    ExamGroupData examGroupData;
    LayoutInflater inflater;
    Context context;

    public ExamGroupAdapter(Context context, ExamGroupData examGroupData) {
        this.context = context;
        this.examGroupData = examGroupData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return examGroupData.getData().getExamGroups().size();
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

        ViewHolder viewHolder;
        View view = convertView;

        if (view == null) {
            view = inflater.inflate(R.layout.adapter_lecture_item, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.textViewLectDesc = (TextView) view.findViewById(R.id.textViewLectDesc);
            viewHolder.textViewLectureName = (TextView) view.findViewById(R.id.textViewLectureName);
            viewHolder.textViewLectureNo = (TextView) view.findViewById(R.id.textViewLectureNo);
            viewHolder.textViewSubjectName = (TextView) view.findViewById(R.id.textViewSubjectName);

            viewHolder.linearLayoutMainItemLecture = (LinearLayout) view.findViewById(R.id.linearLayoutMainItemLecture);

            viewHolder.textViewLectDesc.setTypeface(FontClass.proximaRegular(context));
            viewHolder.textViewLectureName.setTypeface(FontClass.proximaBold(context));
            viewHolder.textViewLectureNo.setTypeface(FontClass.proximaBold(context));
            viewHolder.textViewSubjectName.setTypeface(FontClass.proximaRegular(context));

            viewHolder.textViewSubjectName.setVisibility(View.GONE);
            viewHolder.textViewLectDesc.setVisibility(View.GONE);

            ASSL.DoMagic(viewHolder.linearLayoutMainItemLecture);

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewLectureNo.setText(String.valueOf(position + 1) + ". ");
        viewHolder.textViewLectureName.setText(examGroupData.getData().getExamGroups().get(position).getExamGroupName());

        viewHolder.linearLayoutMainItemLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, AllExamsActivity.class);
                intent.putExtra(Constants.EXAM_GROUP_ID, examGroupData.getData().getExamGroups().get(position).getId());
                intent.putExtra(Constants.EXAM_GROUP_NAME, examGroupData.getData().getExamGroups().get(position).getExamGroupName());
                context.startActivity(intent);
            }
        });


        return view;
    }

    public class ViewHolder {

        LinearLayout linearLayoutMainItemLecture;

        TextView textViewLectureNo;
        TextView textViewLectureName;
        TextView textViewSubjectName;
        TextView textViewLectDesc;
    }
}
