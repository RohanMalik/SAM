package com.monkeybusiness.jaaar.Adapter;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentForMarks;
import com.monkeybusiness.jaaar.objectClasses.examData.Exam;
import com.monkeybusiness.jaaar.objectClasses.examStudentMarks.ExamMark;
import com.monkeybusiness.jaaar.objectClasses.examStudentMarks.ExamStudentMarks;
import com.monkeybusiness.jaaar.objectClasses.studentDetailsForMarks.Student;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Spinner;

import java.util.ArrayList;
import java.util.List;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 22/12/16.
 */

public class ExamMarksAdapter extends BaseAdapter {

    private static final String TAG = ExamMarksAdapter.class.getSimpleName();
    Context context;
    LayoutInflater inflater;
    List<Student> students;
    String classAlias;
    ExamStudentMarks examStudentMarks;
    Exam exam;
    public ArrayList<StudentForMarks> studentForMarkses = new ArrayList();

    public ExamMarksAdapter(Context context, List<Student> students, String classAlias) {
        this.context = context;
        inflater = (LayoutInflater) context.getSystemService(context.LAYOUT_INFLATER_SERVICE);
        this.students = students;
        examStudentMarks = Prefs.with(context).getObject(PrefsKeys.EXAM_MARKS_DATA, ExamStudentMarks.class);
        this.classAlias = classAlias;
        exam = Prefs.with(context).getObject(PrefsKeys.EXAM_DATA, Exam.class);
    }

    @Override
    public int getCount() {
        return students.size();
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
            view = inflater.inflate(R.layout.item_exam_student, parent, false);
            viewHolder = new ViewHolder();

            viewHolder.linearlayoutExamItem = (LinearLayout) view.findViewById(R.id.linearlayoutExamItem);
            viewHolder.textViewTitle = (TextView) view.findViewById(R.id.textViewTitle);
            viewHolder.textViewClass = (TextView) view.findViewById(R.id.textViewClass);
            viewHolder.textViewRollNo = (TextView) view.findViewById(R.id.textViewRollNo);
            viewHolder.textViewMarks = (TextView) view.findViewById(R.id.textViewMarks);
            viewHolder.editTextFillMarks = (EditText) view.findViewById(R.id.editTextFillMarks);

            viewHolder.testApplicability = (Spinner) view.findViewById(R.id.testApplicability);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(context,
                    R.array.test_applicable_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
            viewHolder.testApplicability.setAdapter(adapter);

            ASSL.DoMagic(viewHolder.linearlayoutExamItem);
            view.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) view.getTag();
        }

        String marks = "0";
        String remarks = "";

        Student student = students.get(position);

        for (ExamMark examMark : examStudentMarks.getData().getExamMarks()) {
            if (student.getId() == examMark.getStudentId()) {
                marks = examMark.getMarks();
                remarks = examMark.getRemarks();
            }
        }

        StudentForMarks studentForMarks = studentExistInMaster(student.getId());
        if (studentForMarks == null) {
            studentForMarks = new StudentForMarks();
            studentForMarks.setRemarks(remarks);
            studentForMarks.setStudentId(student.getId());
            studentForMarks.setMarks(marks);
            studentForMarkses.add(studentForMarks);
            Log.d(TAG, "1Student : " + new Gson().toJson(studentForMarks) + " " + studentForMarks);
        }

        if (studentForMarkses.get(position).getMarks().equalsIgnoreCase("-777")) {
            viewHolder.testApplicability.setSelection(1);
            viewHolder.editTextFillMarks.setEnabled(false);
        } else if (studentForMarkses.get(position).getMarks().equalsIgnoreCase("-999")) {
            viewHolder.testApplicability.setSelection(2);
            viewHolder.editTextFillMarks.setEnabled(false);
        } else {
            viewHolder.editTextFillMarks.setEnabled(true);
            viewHolder.editTextFillMarks.setText(studentForMarkses.get(position).getMarks());
            viewHolder.testApplicability.setSelection(0);
        }

        viewHolder.textViewMarks.setText("/" + exam.getMaxMarks());
        viewHolder.textViewTitle.setText(student.getStudentName());
        viewHolder.textViewRollNo.setText("Roll No " + student.getRollno());
        viewHolder.textViewClass.setText("Class " + classAlias);

        viewHolder.testApplicability.setOnItemClickListener(new Spinner.OnItemClickListener() {
            @Override
            public boolean onItemClick(Spinner parent, View view, int spPosition, long id) {

                Log.d("abc", "Position : " + spPosition);
                if (spPosition == 1) {
                    studentForMarkses.get(position).setMarks("-777");
                    Log.d(TAG, "1Student : " + new Gson().toJson(studentForMarkses.get(position)) + " " + studentForMarkses.get(position));
                    viewHolder.editTextFillMarks.setEnabled(false);
                } else if (spPosition == 2) {
                    studentForMarkses.get(position).setMarks("-999");
                    Log.d(TAG, "2Student : " + new Gson().toJson(studentForMarkses.get(position)) + " " + studentForMarkses.get(position));
                    viewHolder.editTextFillMarks.setEnabled(false);
                } else {
                    viewHolder.editTextFillMarks.setEnabled(true);
                    Log.d(TAG, "3Student : " + new Gson().toJson(studentForMarkses.get(position)) + " " + studentForMarkses.get(position));
                    if (!viewHolder.editTextFillMarks.getText().toString().equalsIgnoreCase("")) {
                        studentForMarkses.get(position).setMarks(viewHolder.editTextFillMarks.getText().toString());
                    }
                }
                return true;
            }
        });


        viewHolder.editTextFillMarks.setOnFocusChangeListener(new View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                Log.d(TAG, "HasFocus : " + hasFocus);
                if (!hasFocus) {
                    studentForMarkses.get(position).setMarks(viewHolder.editTextFillMarks.getText().toString());
                }
            }
        });
//        viewHolder.editTextFillMarks.addTextChangedListener(new TextWatcher() {
//            @Override
//            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
//
//            }
//
//            @Override
//            public void onTextChanged(CharSequence s, int start, int before, int count) {
//                if (s.length()!=0)
//                {
//                    Log.d(TAG,"TextChanged : "+s+position);
//                    studentForMarkses.get(position).setMarks(s.toString());
//                }
//            }
//
//            @Override
//            public void afterTextChanged(Editable s) {
//
//            }
//        });

        if (position == students.size() - 1) {
            for (StudentForMarks student1 : studentForMarkses) {
                Log.d(TAG, "Student : " + new Gson().toJson(student1) + " " + student1);
            }
        }

        return view;
    }

    public StudentForMarks studentExistInMaster(int id) {
        for (StudentForMarks studentForMarks : studentForMarkses) {
            if (studentForMarks.getStudentId() == id) {
                return studentForMarks;
            }
        }
        return null;
    }

    public class ViewHolder {
        public LinearLayout linearlayoutExamItem;
        public EditText editTextFillMarks;
        TextView textViewRollNo;
        TextView textViewTitle;
        TextView textViewClass;
        TextView textViewMarks;
        Spinner testApplicability;
    }
}
