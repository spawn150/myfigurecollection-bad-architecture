package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.view.View;

import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheWeekFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheWeekFragment.class.getName();

    public PictureOfTheWeekFragment() {
    }

    public static PictureOfTheWeekFragment newInstance() {
        return new PictureOfTheWeekFragment();
    }


    @Override
    protected void loadCollection() {

    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {

    }
}
