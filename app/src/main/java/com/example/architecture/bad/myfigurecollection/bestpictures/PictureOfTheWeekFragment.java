package com.example.architecture.bad.myfigurecollection.bestpictures;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheWeekFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheWeekFragment.class.getName();

    public PictureOfTheWeekFragment() {
    }

    public static PictureOfTheWeekFragment newInstance() {
        PictureOfTheWeekFragment fragment = new PictureOfTheWeekFragment();
        return fragment;
    }

    @Override
    protected String getTextValue() {
        return "Pictures of the week!";
    }

}