package com.monkeybusiness.jaaar.Activity;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.retrofit.RestClient;
import com.monkeybusiness.jaaar.utils.Constants;
import com.monkeybusiness.jaaar.utils.preferences.Prefs;
import com.monkeybusiness.jaaar.utils.preferences.PrefsKeys;
import com.rey.material.widget.FloatingActionButton;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class AttendanceDetailsActivity extends BaseActivity implements View.OnClickListener{

    private static final String TAG = "AttendanceDetails";
    private RelativeLayout relativeLayoutMenu;
    private TextView textViewActionTitle;
    ListView listViewLectures;

    FloatingActionButton fabAddAttd;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_attendance_details);

        initialization();

        getAttendanceDetailsServerCall(getIntent().getIntExtra(Constants.BATCH_ID,0));
    }

    private void initialization() {

        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        fabAddAttd = (FloatingActionButton) findViewById(R.id.fabAddAttd);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);
        fabAddAttd.setOnClickListener(this);

        textViewActionTitle.setText("My Batches");

        listViewLectures = (ListView) findViewById(R.id.listViewLectures);

    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
            case R.id.fabAddAttd:
                break;
        }
    }

    public void getAttendanceDetailsServerCall(int id) {

        String xCookies = Prefs.with(this).getString(PrefsKeys.X_COOKIES, "");
        String aCookies = Prefs.with(this).getString(PrefsKeys.A_COOKIES, "");

        RestClient.getApiService(xCookies,aCookies).apiCallGetAttendanceDetail(String.valueOf(id), "day", "1","10", new Callback<String>() {
            @Override
            public void success(String s, Response response) {
                Log.d(TAG,"Response : "+s);
            }

            @Override
            public void failure(RetrofitError error) {
                Log.d(TAG,"error : "+error.toString());
            }
        });
    }

}
