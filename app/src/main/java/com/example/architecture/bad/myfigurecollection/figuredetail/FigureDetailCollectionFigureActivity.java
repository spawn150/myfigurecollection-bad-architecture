package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;

import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureDetailCollectionFigureActivity extends FigureDetailActivity {

    @Override
    protected View.OnClickListener getImageViewClickListener(final DetailedFigure detailedFigure) {
        return v -> ActivityUtils.startItemCollectionFiguresGalleryActivity(FigureDetailCollectionFigureActivity.this, detailedFigure.getId());
    }
}
