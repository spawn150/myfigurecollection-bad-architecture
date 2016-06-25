package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

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

        FigureDetailFragment figureDetailFragment = (FigureDetailFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_figure_detail);
        if (figureDetailFragment == null) {
            // Create the fragment
            figureDetailFragment = FigureDetailFragment.newInstance((ItemFigureDetail) getIntent().getParcelableExtra(ActivityUtils.ARG_FIGURE_DETAIL));
            ActivityUtils.addFragmentToActivity(
                    getSupportFragmentManager(), figureDetailFragment, R.id.fragment_figure_detail);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }

}
