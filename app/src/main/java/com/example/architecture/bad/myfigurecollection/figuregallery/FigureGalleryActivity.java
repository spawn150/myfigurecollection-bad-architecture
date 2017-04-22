package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;

import com.example.architecture.bad.myfigurecollection.R;

public abstract class FigureGalleryActivity extends AppCompatActivity implements FigureGalleryFragment.OnGalleryListener {
    private Toolbar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_figure_gallery);

        toolbar = (Toolbar) findViewById(R.id.toolbar_gallery);
        toolbar.setTitle("");
        setSupportActionBar(toolbar);
        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.button_clear);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        loadFragment();
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return true;
    }

    protected abstract void loadFragment();

    @Override
    public void onFigureChanged(int position, int total) {
        toolbar.setTitle(getString(R.string.label_gallery_counter, position, total));
    }
}
