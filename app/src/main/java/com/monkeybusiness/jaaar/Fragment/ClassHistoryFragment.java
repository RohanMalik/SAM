package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.R;

/**
 * Created by rohanmalik on 29/12/15.
 */
public class ClassHistoryFragment extends Fragment{

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_history, container, false);

        return rootView;
    }
    static ClassHistoryFragment instance;
    public static ClassHistoryFragment getInstance() {
        if( instance == null ) {
            instance = new ClassHistoryFragment();
        }
        return instance;
    }
}
