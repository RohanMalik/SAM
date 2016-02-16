package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.util.Rfc822Tokenizer;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.MultiAutoCompleteTextView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.monkeybusiness.jaaar.Activity.BaseActivity;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.utils.Utils;

import java.util.Arrays;

/**
 * Created by rakesh on 2/2/16.
 */
public class RemarksFragment extends BaseActivity{

    RelativeLayout relativeLayoutMenu;
    TextView textViewActionTitle;

    RecipientEditTextView recipientEditTextView;

    ArrayAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.fragment_remarks_student);

        Utils.classFlag = 5;

        toggleLayouts(linearlayoutRemarks,textViewRemarks);
        relativeLayoutMenu = (RelativeLayout) findViewById(R.id.relativeLayoutMenu);
        textViewActionTitle = (TextView) findViewById(R.id.textViewActionTitle);

        relativeLayoutMenu.setOnClickListener(this);
        textViewActionTitle.setOnClickListener(this);

        textViewActionTitle.setText("Remarks");

        recipientEditTextView = (RecipientEditTextView) findViewById(R.id.recipientEditTextView);

        recipientEditTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        recipientEditTextView.setAdapter(new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, this));

    }

//    @Nullable
//    @Override
//    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
//
//
//        return rootView;
//    }

    @Override
    public void onClick(View v) {
        super.onClick(v);
        switch (v.getId())
        {
            case R.id.relativeLayoutMenu:
                toggle();
                break;
        }
    }
}
