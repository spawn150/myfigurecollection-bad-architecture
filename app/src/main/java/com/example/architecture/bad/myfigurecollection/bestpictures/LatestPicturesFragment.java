package com.example.architecture.bad.myfigurecollection.bestpictures;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestPicturesFragment extends BestPicturesFragment {

    public static final String TAG = LatestPicturesFragment.class.getName();

    public LatestPicturesFragment() {
    }

    public static LatestPicturesFragment newInstance() {
        return new LatestPicturesFragment();
    }

    @Override
    protected String getTextValue() {
        return "Latest Pictures!";
    }
}
