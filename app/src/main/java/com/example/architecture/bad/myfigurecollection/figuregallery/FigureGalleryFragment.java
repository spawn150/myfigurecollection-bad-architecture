package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;

import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class FigureGalleryFragment extends Fragment {

    private static final String ARG_FIGURE_ID = "figureid";

    private String figureId;

    public FigureGalleryFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureGalleryFragment.
     */
    public static FigureGalleryFragment newInstance(String figureId) {
        FigureGalleryFragment fragment = new FigureGalleryFragment();
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
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_figure_gallery, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);


        MFCRequest.INSTANCE.getGalleryService().getGalleryForItem(figureId, 0, new Callback<PictureGallery>() {
            @Override
            public void success(PictureGallery pictureGallery, Response response) {
                List<Picture> pictures = pictureGallery.getGallery().getPicture();

                Log.d("GALLERY", pictures.toString());
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }
}
