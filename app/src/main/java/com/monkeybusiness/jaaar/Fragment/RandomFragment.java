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
public class RandomFragment extends Fragment {

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_random, container, false);

        return rootView;
    }
    static RandomFragment instance;
    public static RandomFragment getInstance() {
        if( instance == null ) {
            instance = new RandomFragment();
        }
        return instance;
    }
}
