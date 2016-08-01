package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class FigureGalleryActivity extends AppCompatActivity implements FigureGalleryFragment.OnGalleryListener {

    private TextView textViewGalleryCounter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_gallery);

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

        textViewGalleryCounter = (TextView)findViewById(R.id.text_view_gallery_counter);

    }

    @Override
    public void onFigureChanged(int position, int total) {
        textViewGalleryCounter.setText(getString(R.string.label_gallery_counter, position, total));
    }
}
