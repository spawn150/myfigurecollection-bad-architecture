package com.example.architecture.bad.myfigurecollection.bestpictures;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheDayFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheDayFragment.class.getName();

    public PictureOfTheDayFragment() {
    }

    public static PictureOfTheDayFragment newInstance() {
        return new PictureOfTheDayFragment();
    }

    @Override
    protected String getTextValue() {
        return "Pictures of the day!";
    }
}
