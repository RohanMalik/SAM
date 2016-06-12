package com.monkeybusiness.jaaar.Fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.monkeybusiness.jaaar.R;

/**
 * Created by rakesh on 2/6/16.
 */
public class TestTakenFragment extends Fragment {

    public static TestTakenFragment newInstance(int page, String title) {
        TestTakenFragment fragmentFirst = new TestTakenFragment();
        Bundle args = new Bundle();
        args.putInt("pageNo", page);
        args.putString("title", title);
        fragmentFirst.setArguments(args);
        return fragmentFirst;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_test_list,container,false);

        return view;
    }
}
