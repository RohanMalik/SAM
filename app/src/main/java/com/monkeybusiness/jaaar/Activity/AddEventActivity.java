package com.monkeybusiness.jaaar.Activity;

import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AlertDialog;
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

import com.google.gson.Gson;
import com.monkeybusiness.jaaar.Fragment.MyCalendarFragment;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.addEventObject.AddEventObject;
import com.monkeybusiness.jaaar.objectClasses.addEventObject.Events;
import com.monkeybusiness.jaaar.objectClasses.addEventResponse.AddEventResponseData;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.ISO8601;
import com.monkeybusiness.jaaar.utils.Utils;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.Button;
import com.rey.material.widget.Spinner;
import com.wdullaer.materialdatetimepicker.date.DatePickerDialog;
import com.wdullaer.materialdatetimepicker.time.RadialPickerLayout;
import com.wdullaer.materialdatetimepicker.time.TimePickerDialog;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
import retrofit.mime.TypedByteArray;
import retrofit.mime.TypedInput;

public class AddEventActivity extends BaseActivity implements View.OnClickListener,TimePickerDialog.OnTimeSetListener,DatePickerDialog.OnDateSetListener {

    EditText input_event_name;
    EditText input_event_desc;

    TextView textViewFrom;
    TextView textViewTo;

    Button buttonAddEvent;

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    Spinner spinner;

    boolean fromTo;
    private String TAG = "AddEventActivity";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_test_event);

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
                showDateDialog();
                fromTo = true;
                 break;
            case R.id.textViewTo:
                showDateDialog();
                fromTo = false;
                break;
            case android.R.id.home:
                super.onBackPressed();
                break;
            case R.id.buttonAddEvent:
                postEventServerCall();
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

    public void showDateDialog()
    {
        Calendar now = Calendar.getInstance();

        DatePickerDialog dpd = DatePickerDialog.newInstance(
                this,
                now.get(Calendar.YEAR),
                now.get(Calendar.MONTH),
                now.get(Calendar.DAY_OF_MONTH)
        );

        dpd.show(getFragmentManager(), "Datepickerdialog");
    }


    Date startDate;
    Date endDate;
    @Override
    public void onTimeSet(RadialPickerLayout view, int hourOfDay, int minute, int second) {

        Log.d("AddEvnt","h: "+hourOfDay+" m: "+minute+"h1: "+hourOfDay+" m1: "+second);

        if (fromTo)
        {
            startDate = new Date();
            startDate.setYear(year);
            startDate.setMonth(monthOfYear);
            startDate.setDate(dayOfMonth);

            startDate.setHours(hourOfDay);
            startDate.setMinutes(minute);
            textViewFrom.setText(startDate.toString());
        }
        else
        {
            endDate = new Date();
            endDate.setYear(year);
            endDate.setMonth(monthOfYear);
            endDate.setDate(dayOfMonth);

            endDate.setHours(hourOfDay);
            endDate.setMinutes(minute);
            textViewTo.setText(endDate.toString());
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

    int year;
    int monthOfYear;
    int dayOfMonth;

    @Override
    public void onDateSet(DatePickerDialog view, int year, int monthOfYear, int dayOfMonth) {
        Log.d(TAG," "+year+" : "+monthOfYear+" : "+dayOfMonth);
        this.year = year;
        this.monthOfYear = monthOfYear;
        this.dayOfMonth = dayOfMonth;

        showTimeDialog();
    }

    public void postEventServerCall()
    {
        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES,"");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES,"");

        String eventName = input_event_name.getText().toString();
//        String eventDesc = input_event_desc.getText().toString();
        String type = "";

        if (spinner.getSelectedItem().toString().equalsIgnoreCase("personal"))
        {
            type = "UserLogin";
        }

        if (textViewFrom.getText().toString().equalsIgnoreCase("select date") || textViewTo.getText().toString().equalsIgnoreCase("select date"))
        {
            Utils.failureDialog(this,"Warning","Please select date.");
        }
        else
        {
            AddEventObject object = new AddEventObject();

            Events events = new Events();
            events.setEventName(eventName);
            events.setEventTypeType(type);

            Calendar startCalendar = Calendar.getInstance();
            startCalendar.set(startDate.getYear(),startDate.getMonth(),startDate.getDate(),startDate.getHours(),startDate.getMinutes());

            Calendar endCalendar = Calendar.getInstance();
            endCalendar.set(endDate.getYear(),endDate.getMonth(),endDate.getDate(),endDate.getHours(),endDate.getMinutes());

            events.setStartTime(ISO8601.fromCalendar(startCalendar));
            events.setEndTime(ISO8601.fromCalendar(endCalendar));

            object.setEvents(events);

            Log.d(TAG,"object : "+new Gson().toJson(object));

            String jsonObject = new Gson().toJson(object);

            ProgressDialog dialog  = ProgressDialog.show(this,"Please Wait...","Adding Event");
            try {
                TypedInput typedInput = new TypedByteArray("application/json",jsonObject.getBytes("UTF-8"));
                RestClient.getApiServicePojo(xCookies,aCookies).apiCallPostEvent(typedInput, new Callback<AddEventResponseData>() {
                    @Override
                    public void success(AddEventResponseData addEventResponseData, Response response) {
                        Log.d(TAG,"Response : "+new Gson().toJson(addEventResponseData));
                        dialog.dismiss();
                        if (addEventResponseData.getResponseMetadata().getSuccess().equalsIgnoreCase("yes"))
                        {
                            AlertDialog.Builder alert = new AlertDialog.Builder(AddEventActivity.this);
                            alert.setTitle("Event Added");
                            alert.setMessage("You Have successfully created event.");
                            alert.setPositiveButton("OK", new DialogInterface.OnClickListener() {
                                @Override
                                public void onClick(DialogInterface dialog, int which) {
                                    finish();
                                    Intent intent = new Intent(AddEventActivity.this
                                            , MyCalendarFragment.class);
                                    startActivity(intent);
                                }
                            });
                            alert.show();
                        }
                        else
                        {
                            Utils.failureDialog(AddEventActivity.this,"Warning...","Something went wrong, please try again.");
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {
                        Log.d(TAG,"error : "+error.toString());
                        dialog.dismiss();
                        Utils.failureDialog(AddEventActivity.this,"Something went wrong","Plaese try again.");
                    }
                });
            } catch (UnsupportedEncodingException e) {
                e.printStackTrace();
            }

        }
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        Intent intent = new Intent(AddEventActivity.this
                , MyCalendarFragment.class);
        startActivity(intent);
    }
}
