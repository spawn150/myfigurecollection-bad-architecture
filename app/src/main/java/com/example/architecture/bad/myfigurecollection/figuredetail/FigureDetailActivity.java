package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

public class FigureDetailActivity extends AppCompatActivity {

  @Override protected void onCreate(Bundle savedInstanceState) {
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

      int fragmentType =
          getIntent().getIntExtra(ActivityUtils.ARG_FRAGMENT_TYPE, ActivityUtils.OWNED_FRAGMENT);
      //noinspection WrongConstant
      figureDetailFragment =
          FigureDetailFragmentFactory.createFragmentDetail(fragmentType, detailedFigure);

      ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), figureDetailFragment,
          R.id.fragment_figure_detail);
    }
  }

  @Override public boolean onSupportNavigateUp() {
    onBackPressed();
    return true;
  }

  private void loadBackdrop(final String figureId) {
    final ImageView imageView = (ImageView) findViewById(R.id.backdrop);

    if (imageView != null) {
      imageView.setOnClickListener(new View.OnClickListener() {
        @Override public void onClick(View v) {
          ActivityUtils.startItemFigureGalleryActivity(v.getContext(), figureId);
        }
      });

      Glide.with(this)
          .load(getString(R.string.figure_large_image_url, figureId))
          .centerCrop()
          .into(imageView);
    }
  }
}
