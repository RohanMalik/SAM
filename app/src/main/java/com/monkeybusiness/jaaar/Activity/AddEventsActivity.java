package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.R;
import com.rey.material.widget.Spinner;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 28/7/16.
 */
public class AddEventsActivity extends BaseActivity {

    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_teacher_dashboard_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

    }
}
