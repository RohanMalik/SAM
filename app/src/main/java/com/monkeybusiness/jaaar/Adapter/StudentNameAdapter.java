package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.studentSearchdata.Student;

import java.util.List;

/**
 * Created by rakesh on 15/6/16.
 */
public class StudentNameAdapter extends BaseAdapter{

    Context context;
    LayoutInflater inflater;
    public List<Student> studentNames;

    public StudentNameAdapter(Context context, List<Student> studentNames)
    {
        this.context = context;
        this.studentNames = studentNames;
        inflater = (LayoutInflater) context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
    }

    @Override
    public int getCount() {
        return studentNames.size();
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
            view = inflater.inflate(R.layout.list_item_grid_view,parent,false);
            holder = new ViewHolder();

            holder.textViewNameItem = (TextView) view.findViewById(R.id.textViewNameItem);
            holder.imageViewCross = (ImageView) view.findViewById(R.id.imageViewCross);

            view.setTag(holder);
        }
        else
        {
            holder = (ViewHolder) view.getTag();
        }

        holder.textViewNameItem.setText(studentNames.get(position).getStudentName());

        final View finalView = view;
        holder.imageViewCross.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Log.d("Adapter","cross clicked : "+studentNames.get(position));
                studentNames.remove(position);
                notifyDataSetChanged();
            }
        });

        return view;
    }

    public class ViewHolder
    {
        ImageView imageViewCross;
        TextView textViewNameItem;
    }
}
