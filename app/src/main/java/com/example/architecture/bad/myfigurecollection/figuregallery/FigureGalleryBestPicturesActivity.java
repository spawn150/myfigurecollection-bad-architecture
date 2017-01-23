package com.example.architecture.bad.myfigurecollection.figuregallery;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureGalleryBestPicturesActivity extends FigureGalleryActivity {
    @Override
    protected void loadFragment() {
        FigureGalleryBestPicturesFragment figureGalleryBestPicturesFragment = (FigureGalleryBestPicturesFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_figure_detail);
        if (figureGalleryBestPicturesFragment == null) {
            String figureId = getIntent().getStringExtra(ActivityUtils.ARG_FIGURE_ID);
            String pictureUrl = getIntent().getStringExtra(ActivityUtils.ARG_PICTURE_URL);
            figureGalleryBestPicturesFragment = FigureGalleryBestPicturesFragment.newInstance(figureId, pictureUrl);
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), figureGalleryBestPicturesFragment, R.id.fragment_figure_gallery);
        }
    }
}
