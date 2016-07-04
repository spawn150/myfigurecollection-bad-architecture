package com.example.architecture.bad.myfigurecollection.timelinetwitter;

import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.figuredetail.FigureDetailFragment;
import com.example.architecture.bad.myfigurecollection.figuredetail.FigureDetailFragmentFactory;
import com.example.architecture.bad.myfigurecollection.figuregallery.FigureGalleryFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

public class EmbeddedTwitterActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_twitter);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setDisplayHomeAsUpEnabled(true);

        EmbeddedTwitterFragment embeddedTwitterFragment = (EmbeddedTwitterFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_embedded_twitter);
        if (embeddedTwitterFragment == null) {
            embeddedTwitterFragment = EmbeddedTwitterFragment.newInstance();
            ActivityUtils.addFragmentToActivity(getSupportFragmentManager(), embeddedTwitterFragment, R.id.fragment_embedded_twitter);
        }

    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}
