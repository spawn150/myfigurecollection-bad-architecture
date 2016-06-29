package com.example.architecture.bad.myfigurecollection.figures;

import android.view.MenuItem;

import com.example.architecture.bad.myfigurecollection.BaseActivity;

public class FiguresActivity extends BaseActivity {

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {

        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    protected void setFragment(int contentFrameId) {
        //TODO Remove this method
    }


}
