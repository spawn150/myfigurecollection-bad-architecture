package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.Handler;
import android.os.HandlerThread;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.io.IOException;

/**
 * Abstract Activity to centralize code for figures details.
 */
public abstract class FigureDetailActivity extends AppCompatActivity {

    private static final String TAG = FigureDetailActivity.class.getName();
    private HandlerThread fullImageLoaderThread;
    private ImageView imageView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_figure_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        //delays entering transition
        postponeEnterTransition();

        DetailedFigure detailedFigure = getIntent().getParcelableExtra(ActivityUtils.ARG_FIGURE_DETAIL);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(
                StringUtils.extractStringBeforeSeparatorRepeatedNTimes(detailedFigure.getName(), '-', 2));

        fullImageLoaderThread = new HandlerThread("Full Image loader thread");
        fullImageLoaderThread.start();

        loadBackdrop(detailedFigure);

        //Workaround to fix issue on NestedScrollView scrolling (http://stackoverflow.com/questions/31795483/collapsingtoolbarlayout-doesnt-recognize-scroll-fling)
        FrameLayout viewGroup = (FrameLayout) findViewById(R.id.fragment_figure_detail);
        viewGroup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                view.getId();
            }
        });

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
    protected void onStart() {
        super.onStart();
    }

    @Override
    public boolean onSupportNavigateUp() {
        //onBackPressed();
        supportFinishAfterTransition();
        return true;
    }

    private void loadBackdrop(final DetailedFigure detailedFigure) {
        imageView = (ImageView) findViewById(R.id.backdrop);

        if (imageView != null) {

            imageView.setOnClickListener(getImageViewClickListener(detailedFigure));

            Picasso.with(this)
                    .load(detailedFigure.getImageUrlMedium() /*getImageUrl(detailedFigure)*/)
                    .into(imageView, new Callback() {
                        @Override
                        public void onSuccess() {
                            if (imageView != null) {
                                resizeImage(((BitmapDrawable) imageView.getDrawable()).getBitmap());
                                startPostponedEnterTransition();
                                Log.d("Detail", "Thread info: " + Thread.currentThread().getName());
                                final Handler handler = new Handler();
                                handler.postDelayed(new Runnable() {
                                    @Override
                                    public void run() {
                                        loadFullImage(detailedFigure.getImageUrlFull());
                                    }
                                }, 1000);
                            }
                        }

                        @Override
                        public void onError() {
                            startPostponedEnterTransition();
                            loadFullImage(detailedFigure.getImageUrlFull());
                        }
                    });
        }

    }

    private void resizeImage(Bitmap bitmap) {
        double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
        double newWidth = CodeUtils.getScreenWidth(FigureDetailActivity.this) * ratio;
        imageView.getLayoutParams().height = (int) newWidth;
        imageView.requestLayout();
    }

    @Override
    protected void onDestroy() {
        if (fullImageLoaderThread != null) {
            fullImageLoaderThread.quit();
        }
        imageView.setImageBitmap(null);
        super.onDestroy();
    }

    private void loadFullImage(final String imageUrl) {

        if (fullImageLoaderThread != null && fullImageLoaderThread.isAlive()) {
            new Handler(fullImageLoaderThread.getLooper()).post(new Runnable() {
                @Override
                public void run() {
                    try {
                        final Bitmap bitmap = Picasso.with(FigureDetailActivity.this).load(imageUrl).get();
                        if (bitmap != null) {
                            runOnUiThread(new Runnable() {
                                @Override
                                public void run() {
                                    imageView.setImageBitmap(bitmap);
                                    resizeImage(bitmap);
                                }
                            });
                        }
                    } catch (IOException e) {
                        Log.w(TAG, "Full Image not loaded.");
                    }
                }
            });
        }
    }

    protected abstract View.OnClickListener getImageViewClickListener(DetailedFigure detailedFigure);
}
