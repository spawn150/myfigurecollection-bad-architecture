package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.view.View;

import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheMonthFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheMonthFragment.class.getName();

    public PictureOfTheMonthFragment() {
    }

    public static PictureOfTheMonthFragment newInstance() {
        return new PictureOfTheMonthFragment();
    }

    @Override
    protected void loadCollection() {

    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {

    }
}
