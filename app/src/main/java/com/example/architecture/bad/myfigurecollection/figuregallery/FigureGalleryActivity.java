package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class FigureGalleryActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_gallery);

        /*
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        //noinspection ConstantConditions
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_clear_24px);
        */

        FigureGalleryFragment figureGalleryFragment = (FigureGalleryFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_figure_detail);
        if (figureGalleryFragment == null) {
            figureGalleryFragment = FigureGalleryFragment.newInstance(getIntent().getStringExtra(ActivityUtils.ARG_FIGURE_ID));
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), figureGalleryFragment, R.id.fragment_figure_gallery);
        }

        //noinspection ConstantConditions
        findViewById(R.id.image_button_clear).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });

    }

}
