package com.monkeybusiness.jaaar.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;


import com.dd.processbutton.iml.ActionProcessButton;
import com.monkeybusiness.jaaar.R;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddEventActivity extends AppCompatActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener {

    EditText input_event_name;
    EditText input_event_desc;

    TextView textViewFrom;
    TextView textViewTo;

    Toolbar dayView_toolbar;

    ActionProcessButton buttonAddEvent;

    boolean fromTo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initialization();
    }

    public void initialization()
    {

        dayView_toolbar = (Toolbar) findViewById(R.id.dashboard_toolbar);

        setSupportActionBar(dayView_toolbar);
        getSupportActionBar().setTitle("01 JAN 2016");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        input_event_name = (EditText) findViewById(R.id.input_event_name);
        input_event_desc = (EditText) findViewById(R.id.input_event_desc);

        buttonAddEvent = (ActionProcessButton) findViewById(R.id.buttonAddEvent);

        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewTo = (TextView) findViewById(R.id.textViewTo);

        buttonAddEvent.setMode(ActionProcessButton.Mode.ENDLESS);

        buttonAddEvent.setOnClickListener(this);
        textViewFrom.setOnClickListener(this);
        textViewTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {

        switch (v.getId())
        {
            case R.id.textViewFrom:
                showTimeDialog();
                fromTo = true;
                 break;
            case R.id.textViewTo:
                showTimeDialog();
                fromTo = false;
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.buttonAddEvent:
                buttonAddEvent.setProgress(1);
                new Handler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        buttonAddEvent.setProgress(100);
                    }
                },2000);
                break;
        }
    }

    public void showTimeDialog()
    {
        Calendar now = Calendar.getInstance();
        TimePickerDialog tpd = TimePickerDialog.newInstance(
                AddEventActivity.this,
                now.get(Calendar.HOUR_OF_DAY),
                now.get(Calendar.MINUTE),
                false
        );
        tpd.show(getFragmentManager(), "Timepickerdialog");
    }

    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        Log.d("AddEvnt","h: "+hourOfDay+" m: "+minute+"h1: "+hourOfDay+" m1: "+minute);

        if (fromTo)
        {
            textViewFrom.setText(""+hourOfDay+":"+minute);
        }
        else
        {
            textViewTo.setText(""+hourOfDay+":"+minute);
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId())
        {
            case android.R.id.home:
                super.onBackPressed();
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
