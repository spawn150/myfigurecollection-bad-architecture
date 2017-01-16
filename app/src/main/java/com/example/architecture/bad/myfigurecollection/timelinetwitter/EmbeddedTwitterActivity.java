package com.example.architecture.bad.myfigurecollection.timelinetwitter;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.architecture.bad.myfigurecollection.R;

public class EmbeddedTwitterActivity extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_embedded_twitter);
        setTitle(getString(R.string.menu_title_twitter));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }
    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }

}
