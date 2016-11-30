package com.example.architecture.bad.myfigurecollection.bestpictures;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.architecture.bad.myfigurecollection.R;

/**
 * A placeholder fragment containing a simple view.
 */
public class BestPicturesFragment extends Fragment {

    public static final String TAG = BestPicturesFragment.class.getName();

    public BestPicturesFragment() {
    }

    public static BestPicturesFragment newInstance() {
        BestPicturesFragment fragment = new BestPicturesFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_best_pictures, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }
}
