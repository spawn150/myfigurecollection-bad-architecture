package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.os.Bundle;
import android.util.Log;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.GalleryFigure;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * Created by spawn on 23/01/17.
 */

public class FigureGalleryCollectionFiguresFragment extends FigureGalleryFragment {

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureGalleryCollectionFiguresFragment.
     */
    public static FigureGalleryCollectionFiguresFragment newInstance(String figureId) {
        FigureGalleryCollectionFiguresFragment fragment = new FigureGalleryCollectionFiguresFragment();
        Bundle args = new Bundle();
        args.putString(ARG_FIGURE_ID, figureId);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            figureId = getArguments().getString(ARG_FIGURE_ID);
        }
    }

    @Override
    protected void loadGallery() {
        Call<PictureGallery> call = MFCRequest.getInstance().getGalleryService().getGalleryForItem(figureId, 1);
        call.enqueue(new Callback<PictureGallery>() {
            @Override
            public void onResponse(Call<PictureGallery> call, Response<PictureGallery> response) {

                if (getActivity() != null && isAdded()) {
                    List<GalleryFigure> galleryFigures = new ArrayList<>();
                    galleryFigures.add(new GalleryFigure(figureId, "", "", getString(R.string.figure_large_image_url, figureId)));

                    PictureGallery pictureGallery = response.body();
                    if (!"".equals(pictureGallery.getGallery().getNumPictures()) && Integer.valueOf(pictureGallery.getGallery().getNumPictures()) > 0) {
                        List<Picture> pictures = pictureGallery.getGallery().getPicture();

                        Picture picture;
                        int size = pictures.size();
                        for (int i = 0; i < size; i++) {
                            picture = pictures.get(i);
                            galleryFigures.add(new GalleryFigure(picture.getId(), picture.getAuthor(), StringUtils.formatDate(picture.getDate(), getString(R.string.not_available)), picture.getFull()));
                        }
                    }
                    try {
                        gallerySize = Integer.valueOf(pictureGallery.getGallery().getNumPictures());
                    } catch (NumberFormatException nfe) {
                        gallerySize = galleryFigures.size();
                    }
                    galleryPager.setAdapter(new FullScreenImageAdapter(galleryFigures));
                    galleryListener.onFigureChanged(1, gallerySize);
                }
            }

            @Override
            public void onFailure(Call<PictureGallery> call, Throwable t) {
                Log.e(TAG, "Error loading Gallery", t);
            }
        });
    }
}
