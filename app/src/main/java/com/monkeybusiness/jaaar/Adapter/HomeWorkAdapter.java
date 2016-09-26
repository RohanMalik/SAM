package com.monkeybusiness.jaaar.Adapter;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Vibrator;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.homeWorkResponseData.Hw;
import com.monkeybusiness.jaaar.utils.FontClass;

import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 25/7/16.
 */
public class HomeWorkAdapter extends BaseAdapter {

    private static final String TAG = "EventsListAdapter";
    Context context;
    LayoutInflater inflater;
    List<Hw> hws;

    public HomeWorkAdapter(Context context, List<Hw> hws) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        this.hws = hws;
    }

    @Override
    public int getCount() {
        return hws.size();
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
        if (hws.get(position).getLecture() == null) {
            title = hws.get(position).getTeacher().getName();
        } else {
            title = hws.get(position).getLecture().getLectureName() + " - " + hws.get(position).getTeacher().getName();
        }
        viewHolder.textViewEventTitle.setText(title);

        viewHolder.textViewEventDesc.setText(hws.get(position).getHwDate());

        viewHolder.textViewEventTime.setText(hws.get(position).getDetails());

        viewHolder.linearLayoutMain.setOnLongClickListener(new View.OnLongClickListener()

                                                            {
                                                                @Override
                                                                public boolean onLongClick(View v) {

                                                                    ClipboardManager clipboard = (ClipboardManager) context.getSystemService(context.CLIPBOARD_SERVICE);
                                                                    ClipData clip = ClipData.newPlainText("homeWork", viewHolder.textViewEventTime.getText().toString());
                                                                    clipboard.setPrimaryClip(clip);

                                                                    try {
                                                                        Vibrator vib = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
                                                                        vib.vibrate(200);
                                                                    } catch (Exception e) {
                                                                        e.printStackTrace();
                                                                    }


                                                                    Toast.makeText(context.getApplicationContext(), "Copied to clipboard", Toast.LENGTH_SHORT).show();
                                                                    return false;
                                                                }
                                                            }

        );

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
