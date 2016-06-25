package com.example.architecture.bad.myfigurecollection.bestpictures;

import com.example.architecture.bad.myfigurecollection.BaseActivity;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class BestPicturesActivity extends BaseActivity {


    @Override
    protected void setFragment(int contentFrameId) {
        BestPicturesFragment bestPicturesFragment = (BestPicturesFragment) getSupportFragmentManager().findFragmentById(contentFrameId);
        if (bestPicturesFragment == null) {
            // Create the fragment
            bestPicturesFragment = BestPicturesFragment.newInstance();
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), bestPicturesFragment, contentFrameId);
        }
    }
}
