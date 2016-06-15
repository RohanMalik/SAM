package com.monkeybusiness.jaaar.interfaces;

import android.content.Context;

import com.monkeybusiness.jaaar.objectClasses.testListResponseData.Test;

import java.util.List;

/**
 * Created by rakesh on 14/6/16.
 */
public interface TestFragmentListner {

    void onResumeFragment(Context context,List<Test> tests);
}
