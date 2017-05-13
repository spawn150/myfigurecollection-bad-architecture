package com.example.architecture.bad.myfigurecollection.search;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.example.architecture.bad.myfigurecollection.R;

public class SearchActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_search);
    }

    @Override
    public void finish() {
        super.finish();
        overridePendingTransition(R.anim.anim_scale_in, R.anim.anim_slide_out);
    }
}

