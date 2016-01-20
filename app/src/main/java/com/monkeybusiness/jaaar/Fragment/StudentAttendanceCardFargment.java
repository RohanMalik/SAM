package com.monkeybusiness.jaaar.Fragment;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.amulyakhare.textdrawable.TextDrawable;
import com.monkeybusiness.jaaar.R;

/**
 * Created by rakesh on 1/1/16.
 */
public class StudentAttendanceCardFargment extends Fragment {

    View rootView;

    ImageView attdP1;
    ImageView attdP2;
    ImageView attdP3;
    ImageView attdP4;
    ImageView attdP5;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        rootView = inflater.inflate(R.layout.fragment_student_card,container,false);

        attdP1 = (ImageView) rootView.findViewById(R.id.attdP1);
        attdP2 = (ImageView) rootView.findViewById(R.id.attdP2);
        attdP3 = (ImageView) rootView.findViewById(R.id.attdP3);
        attdP4 = (ImageView) rootView.findViewById(R.id.attdP4);
        attdP5 = (ImageView) rootView.findViewById(R.id.attdP5);

        TextDrawable drawableAbsent = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound("A", getResources().getColor(R.color.absent_button));

        TextDrawable drawablePresent = TextDrawable.builder()
                .beginConfig()
                .withBorder(4) /* thickness in px */
                .endConfig()
                .buildRound("P", getResources().getColor(R.color.normal_present_button));

        attdP1.setImageDrawable(drawableAbsent);
        attdP3.setImageDrawable(drawableAbsent);
        attdP5.setImageDrawable(drawableAbsent);

        attdP2.setImageDrawable(drawablePresent);
        attdP4.setImageDrawable(drawablePresent);
        return rootView;
    }
}
