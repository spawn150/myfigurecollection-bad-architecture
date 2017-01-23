package com.example.architecture.bad.myfigurecollection.figuregallery;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureGalleryCollectionFiguresActivity extends FigureGalleryActivity {
    @Override
    protected void loadFragment() {
        FigureGalleryCollectionFiguresFragment figureGalleryCollectionFiguresFragment = (FigureGalleryCollectionFiguresFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_figure_detail);
        if (figureGalleryCollectionFiguresFragment == null) {
            figureGalleryCollectionFiguresFragment = FigureGalleryCollectionFiguresFragment.newInstance(getIntent().getStringExtra(ActivityUtils.ARG_FIGURE_ID));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), figureGalleryCollectionFiguresFragment, R.id.fragment_figure_gallery);
        }
    }
}
