package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.announcementResponse.Announcement;
import com.monkeybusiness.jaaar.utils.FontClass;
import com.monkeybusiness.jaaar.utils.Utils;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 25/7/16.
 */
public class AnnouncementAdapter extends BaseAdapter {

    private static final String TAG = "EventsListAdapter";
    Context context;
    LayoutInflater inflater;
    List<Announcement> announcements;

    public AnnouncementAdapter(Context context, List<Announcement> announcements) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.announcements = announcements;
    }

    @Override
    public int getCount() {
        return announcements.size();
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

        if (view == null) {
            view = inflater.inflate(R.layout.item_student_detail_review, parent, false);
            viewHolder = new ViewHolder();
            viewHolder.linearLayoutMain = (LinearLayout) view.findViewById(R.id.linearLayoutMain);
            viewHolder.imageViewEventType = (ImageView) view.findViewById(R.id.imageViewEventType);
            viewHolder.textViewEventTitle = (TextView) view.findViewById(R.id.textViewEventTitle);
            viewHolder.textViewEventDesc = (TextView) view.findViewById(R.id.textViewEventDesc);
            viewHolder.textViewEventTime = (TextView) view.findViewById(R.id.textViewEventTime);

            viewHolder.textViewEventTime.setTypeface(FontClass.proximaRegular(context));
            viewHolder.textViewEventDesc.setTypeface(FontClass.proximaRegular(context));
            viewHolder.textViewEventTitle.setTypeface(FontClass.proximaRegular(context));

            ASSL.DoMagic(viewHolder.linearLayoutMain);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        viewHolder.textViewEventDesc.setVisibility(View.VISIBLE);

        String title = "";
        if (announcements.get(position).getAnnouncementMaps().get(0).getAnnouncementTypeType().equalsIgnoreCase("lecture"))
        {
            title = "Lecture Announcement";
        }else {
            title = "Batch Announcement";
        }

        String teacherName = announcements.get(position).getUserLogin().getUserType().getName();
//        String className = announcements.get(position).getAnnouncementMaps().get(0).getAnnouncementType().getName();
        viewHolder.textViewEventTitle.setText(title+" By "+teacherName);

        viewHolder.textViewEventDesc.setText(Utils.formatDateAndTime(announcements.get(position).getCreatedAt()));

        viewHolder.textViewEventTime.setText(announcements.get(position).getAnnouncementDetails());

        return view;
    }

    public class ViewHolder {
        LinearLayout linearLayoutMain;
        ImageView imageViewEventType;
        TextView textViewEventTitle;
        TextView textViewEventDesc;
        TextView textViewEventTime;
    }
}
