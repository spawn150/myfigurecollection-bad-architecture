package com.example.architecture.bad.myfigurecollection.settings;


import android.os.Bundle;
import android.preference.PreferenceFragment;
import android.view.View;

import com.example.architecture.bad.myfigurecollection.BuildConfig;
import com.example.architecture.bad.myfigurecollection.R;

/**
 * A simple {@link PreferenceFragment} subclass to manage Settings.
 */
public class SettingsFragment extends PreferenceFragment {


    public SettingsFragment() {
        // Required empty public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        addPreferencesFromResource(R.xml.settings);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        findPreference(SettingsActivity.KEY_PREF_VERSION).setSummary(BuildConfig.VERSION_NAME);
    }
}
