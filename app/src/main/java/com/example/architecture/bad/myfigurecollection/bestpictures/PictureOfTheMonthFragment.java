package com.example.architecture.bad.myfigurecollection.bestpictures;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheMonthFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheMonthFragment.class.getName();

    public PictureOfTheMonthFragment() {
    }

    public static PictureOfTheMonthFragment newInstance() {
        PictureOfTheMonthFragment fragment = new PictureOfTheMonthFragment();
        return fragment;
    }

    @Override
    protected String getTextValue() {
        return "Pictures of the month!";
    }

}
