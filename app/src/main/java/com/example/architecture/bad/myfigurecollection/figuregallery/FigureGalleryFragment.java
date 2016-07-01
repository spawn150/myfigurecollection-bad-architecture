package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.content.Context;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.GalleryFigure;
import com.example.architecture.bad.myfigurecollection.util.GlideLoggingListener;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

import java.util.ArrayList;
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

        // retain this fragment
        setRetainInstance(true);

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

        final ViewPager galleryPager = (ViewPager)view.findViewById(R.id.pager_gallery);

        MFCRequest.INSTANCE.getGalleryService().getGalleryForItem(figureId, 0, new Callback<PictureGallery>() {
            @Override
            public void success(PictureGallery pictureGallery, Response response) {
                List<Picture> pictures = pictureGallery.getGallery().getPicture();

                Log.d("GALLERY", pictures.toString());

                int size = pictures.size();
                List<GalleryFigure> galleryFigures = new ArrayList<>(size);

                Picture picture;
                for (int i = 0; i < size; i++) {
                    picture = pictures.get(i);
                    galleryFigures.add(new GalleryFigure(picture.getId(), picture.getAuthor(), StringUtils.formatDate(picture.getDate(), getString(R.string.not_available)), picture.getFull()));
                }

                galleryPager.setAdapter(new FullScreenImageAdapter(galleryFigures));
            }

            @Override
            public void failure(RetrofitError error) {

            }
        });

    }

    private static class FullScreenImageAdapter extends PagerAdapter {

        private List<GalleryFigure> galleryFigures;
        private LayoutInflater inflater;

        public FullScreenImageAdapter(List<GalleryFigure> galleryFigures) {
            this.galleryFigures = galleryFigures;
        }

        @Override
        public int getCount() {
            return this.galleryFigures.size();
        }

        @Override
        public boolean isViewFromObject(View view, Object object) {
            return view == object;
        }

        @Override
        public Object instantiateItem(ViewGroup container, int position) {
            ImageView imgDisplay;

            inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_image_fullscreen, container,
                    false);

            imgDisplay = (ImageView) viewLayout.findViewById(R.id.image_view_figure);

            Glide
                .with(container.getContext())
                .load(galleryFigures.get(position).getUrl())
                .listener(new GlideLoggingListener<String, GlideDrawable>())
                .into(imgDisplay);

            container.addView(viewLayout);

            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

}
