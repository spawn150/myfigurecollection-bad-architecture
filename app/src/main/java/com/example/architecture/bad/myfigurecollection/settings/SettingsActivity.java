package com.example.architecture.bad.myfigurecollection.settings;

import android.app.ActionBar;
import android.app.Activity;
import android.os.Bundle;
import android.widget.Toolbar;

import com.example.architecture.bad.myfigurecollection.R;

public class SettingsActivity extends Activity {

    public static final String KEY_PREF_NSFW_CONTENT_ENABLED = "pref_nsfw_content_enabled";
    public static final String KEY_PREF_VERSION = "pref_version";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Display the fragment as the main content.
        getFragmentManager().beginTransaction()
                .replace(R.id.settings_container, new SettingsFragment())
                .commit();
    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }
}
