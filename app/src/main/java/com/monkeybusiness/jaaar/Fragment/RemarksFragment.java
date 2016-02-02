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

import com.android.ex.chips.BaseRecipientAdapter;
import com.android.ex.chips.RecipientEditTextView;
import com.monkeybusiness.jaaar.R;

import java.util.Arrays;

/**
 * Created by rakesh on 2/2/16.
 */
public class RemarksFragment extends Fragment{

    View rootView;

    RecipientEditTextView recipientEditTextView;

    ArrayAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_remarks_student,container,false);
        recipientEditTextView = (RecipientEditTextView) rootView.findViewById(R.id.recipientEditTextView);

        recipientEditTextView.setTokenizer(new MultiAutoCompleteTextView.CommaTokenizer());
        recipientEditTextView.setAdapter(new BaseRecipientAdapter(BaseRecipientAdapter.QUERY_TYPE_PHONE, getActivity()));

        return rootView;
    }
}
