package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.os.Bundle;

import com.example.architecture.bad.myfigurecollection.data.figures.GalleryFigure;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureGalleryBestPicturesFragment extends FigureGalleryFragment {

    private static final String ARG_PICTURE_URL = "pictureurl";
    private String pictureUrl;

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureGalleryBestPicturesFragment.
     */
    public static FigureGalleryBestPicturesFragment newInstance(String figureId, String pictureUrl) {
        FigureGalleryBestPicturesFragment fragment = new FigureGalleryBestPicturesFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FIGURE_ID, figureId);
        args.putString(ARG_PICTURE_URL, pictureUrl);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            figureId = getArguments().getString(ARG_FIGURE_ID);
            pictureUrl = getArguments().getString(ARG_PICTURE_URL);
        }
    }

    @Override
    protected void loadGallery(int page) {
        List<GalleryFigure> galleryFigures = new ArrayList<>();
        galleryFigures.add(new GalleryFigure(figureId, "", "", pictureUrl));

        gallerySize = galleryFigures.size();
        galleryPager.setAdapter(new FullScreenImageAdapter(galleryFigures));
        galleryListener.onFigureChanged(1, gallerySize);
    }
}
