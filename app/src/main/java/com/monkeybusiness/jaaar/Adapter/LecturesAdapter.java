package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.Fragment.FriendsActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.lectureResponse.LectureResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.Log;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * Created by rakesh on 4/6/16.
 */
public class LecturesAdapter extends BaseAdapter {

    LectureResponseData lectureResponseData;
    LayoutInflater inflater;
    Context context;

    public LecturesAdapter(Context context, LectureResponseData lectureResponseData) {
        this.context = context;
        this.lectureResponseData = lectureResponseData;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return lectureResponseData.getData().getLectures().size();
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

            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewLectureNo.setText(String.valueOf(position + 1) + ". ");
        viewHolder.textViewLectureName.setText(lectureResponseData.getData().getLectures().get(position).getLectureName());
        viewHolder.textViewSubjectName.setText(lectureResponseData.getData().getLectures().get(position).getSubject().getSubjectName());
        viewHolder.textViewLectDesc.setText(lectureResponseData.getData().getLectures().get(position).getSubject().getDescription());


        viewHolder.linearLayoutMainItemLecture.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(context, FriendsActivity.class);
                intent.putExtra(Constants.LECTURE_ID, lectureResponseData.getData().getLectures().get(position).getId());
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
