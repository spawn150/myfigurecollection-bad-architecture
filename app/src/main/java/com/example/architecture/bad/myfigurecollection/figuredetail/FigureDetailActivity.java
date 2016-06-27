package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.bestpictures.BestPicturesFragment;
import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class FigureDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_detail);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        ItemFigureDetail itemFigureDetail = getIntent().getParcelableExtra(ActivityUtils.ARG_FIGURE_DETAIL);

        CollapsingToolbarLayout collapsingToolbar =
                (CollapsingToolbarLayout) findViewById(R.id.collapsing_toolbar);
        collapsingToolbar.setTitle(itemFigureDetail.getName());

        loadBackdrop(itemFigureDetail.getId());

        FigureDetailFragment figureDetailFragment = (FigureDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_figure_detail);
        if (figureDetailFragment == null) {
            // Create the fragment
            figureDetailFragment = FigureDetailFragment.newInstance(itemFigureDetail);
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), figureDetailFragment, R.id.fragment_figure_detail);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }


    private void loadBackdrop(String figureId) {
        final ImageView imageView = (ImageView) findViewById(R.id.backdrop);
        Glide.with(this).load(getString(R.string.figure_large_image_url, figureId)).centerCrop().into(imageView);
    }

}
