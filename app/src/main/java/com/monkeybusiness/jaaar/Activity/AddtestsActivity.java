package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;

import com.monkeybusiness.jaaar.R;
import com.rey.material.widget.Spinner;

import rmn.androidscreenlibrary.ASSL;

/**
 * Created by rakesh on 28/7/16.
 */
public class AddtestsActivity extends BaseActivity {

    Spinner spinner;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_test_new);

        new ASSL(this, (ViewGroup) findViewById(R.id.root), 1134, 720,
                false);

//        spinner = (Spinner) findViewById(R.id.testSpinner);

        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.example_array, android.R.layout.simple_spinner_item);
// Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
// Apply the adapter to the spinner
        spinner.setAdapter(adapter);


    }
}
