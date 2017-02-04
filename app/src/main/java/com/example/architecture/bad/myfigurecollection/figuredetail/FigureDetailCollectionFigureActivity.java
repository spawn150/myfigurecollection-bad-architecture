package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureDetailCollectionFigureActivity extends FigureDetailActivity {

    @Override
    protected String getImageUrl(DetailedFigure detailedFigure) {
        //return getString(R.string.figure_large_image_url, detailedFigure.getId());
        return getString(R.string.figure_big_image_url, detailedFigure.getId());
    }

    @Override
    protected View.OnClickListener getImageViewClickListener(final DetailedFigure detailedFigure) {
        return new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ActivityUtils.startItemCollectionFiguresGalleryActivity(FigureDetailCollectionFigureActivity.this, detailedFigure.getId());
            }
        };
    }
}
