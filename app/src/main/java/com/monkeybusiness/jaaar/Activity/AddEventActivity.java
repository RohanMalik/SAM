package com.monkeybusiness.jaaar.Activity;

import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.RelativeLayout;
//import android.widget.Spinner;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.util.Calendar;

public class AddEventActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener {

    EditText input_event_name;
    EditText input_event_desc;

    TextView textViewFrom;
    TextView textViewTo;

    Button buttonAddEvent;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    Spinner spinner;

    boolean fromTo;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_event);

        initialization();
    }

    public void initialization()
    {

        input_event_name = (EditText) findViewById(R.id.input_event_name);
        input_event_desc = (EditText) findViewById(R.id.input_event_desc);

        buttonAddEvent = (Button) findViewById(R.id.buttonAddEvent);

        textViewFrom = (TextView) findViewById(R.id.textViewFrom);
        textViewTo = (TextView) findViewById(R.id.textViewTo);

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        spinner = (Spinner) findViewById(R.id.spinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.example_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);

        textViewActionTitle.setText("ADD EVENT");

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        buttonAddEvent.setOnClickListener(this);
        textViewFrom.setOnClickListener(this);
        textViewTo.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
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
                break;
            case R.id.relativeLayoutMenu:
                toggle();
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
