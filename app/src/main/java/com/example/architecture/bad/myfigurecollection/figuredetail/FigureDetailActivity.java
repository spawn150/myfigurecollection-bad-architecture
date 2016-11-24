package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.Window;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

public class FigureDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_figure_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        DetailedFigure detailedFigure = getIntent().getParcelableExtra(ActivityUtils.ARG_FIGURE_DETAIL);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(
                StringUtils.extractStringBeforeSeparatorRepeatedNTimes(detailedFigure.getName(), '-', 2));

        loadBackdrop(detailedFigure.getId());

        FigureDetailFragment figureDetailFragment =
                (FigureDetailFragment) getSupportFragmentManager().findFragmentById(
                        R.id.fragment_figure_detail);
        if (figureDetailFragment == null) {

            @ActivityUtils.FragmentType int fragmentType =
                    getIntent().getIntExtra(ActivityUtils.ARG_FRAGMENT_TYPE, ActivityUtils.OWNED_FRAGMENT);
            figureDetailFragment =
                    FigureDetailFragmentFactory.createFragmentDetail(fragmentType, detailedFigure);

            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), figureDetailFragment,
                    R.id.fragment_figure_detail);
        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        supportFinishAfterTransition();
        return true;
    }

    private void loadBackdrop(final String figureId) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

        if (imageView != null) {
            imageView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    ActivityUtils.startItemFigureGalleryActivity(v.getContext(), figureId);
                }
            });

            DisplayMetrics metrics = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(metrics);

            int imageDimensionInPx = metrics.widthPixels;

            Picasso.with(this)
                    .load(getString(R.string.figure_big_image_url, figureId))
                    .resize(imageDimensionInPx, imageDimensionInPx)
                    .centerCrop()
                    .placeholder(R.drawable.placeholder)
                    .into(imageView);

            Picasso.with(this)
                    .load(getString(R.string.figure_large_image_url, figureId))
                    .resize(imageDimensionInPx, imageDimensionInPx)
                    .centerCrop()
                    .into(new Target() {
                        @Override
                        public void onBitmapLoaded(Bitmap bitmap, Picasso.LoadedFrom from) {
                            imageView.setImageBitmap(bitmap);
                        }

                        @Override
                        public void onBitmapFailed(Drawable errorDrawable) {

                        }

                        @Override
                        public void onPrepareLoad(Drawable placeHolderDrawable) {

                        }
                    });

        }
    }
}
