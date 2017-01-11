package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.GalleryFigure;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class FigureGalleryFragment extends Fragment {

    private static final String ARG_FIGURE_ID = "figureid";
    private static final String TAG = FigureGalleryFragment.class.getSimpleName();

    private String figureId;
    private int gallerySize;
    private ViewPager galleryPager;
    private OnGalleryListener galleryListener;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrolled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected");
            galleryListener.onFigureChanged(++position, gallerySize);
        }

        @Override
        public void onPageScrollStateChanged(int state) {
            Log.d(TAG, "onPageScrollStateChanged");
        }
    };

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
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            galleryListener = (OnGalleryListener) context;
        } catch (Exception e) {
            Log.e(TAG, "onAttach - Activity must implement OnGalleryListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();

        galleryListener = null;
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

        galleryPager = (ViewPager) view.findViewById(R.id.pager_gallery);
        galleryPager.addOnPageChangeListener(onPageChangeListener);

        Call<PictureGallery> call = MFCRequest.getInstance().getGalleryService().getGalleryForItem(figureId, 0);
        call.enqueue(new Callback<PictureGallery>() {
            @Override
            public void onResponse(Call<PictureGallery> call, Response<PictureGallery> response) {

                if (getActivity() != null && isAdded()) {
                    List<GalleryFigure> galleryFigures = new ArrayList<>();
                    galleryFigures.add(new GalleryFigure(figureId, "", "", getString(R.string.figure_big_image_url, figureId)));

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
                    gallerySize = galleryFigures.size();
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

    @Override
    public void onDestroy() {
        super.onDestroy();

        galleryPager.removeOnPageChangeListener(onPageChangeListener);
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

            GalleryFigure galleryFigure = galleryFigures.get(position);

            imgDisplay = (ImageView) viewLayout.findViewById(R.id.image_view_figure);

            Picasso
                    .with(container.getContext())
                    .load(galleryFigure.getUrl())
                    .into(imgDisplay);

            ((TextView) viewLayout.findViewById(R.id.text_view_gallery_username)).setText(galleryFigure.getAuthor());
            ((TextView) viewLayout.findViewById(R.id.text_view_gallery_date)).setText(galleryFigure.getDate());

            container.addView(viewLayout);

            return viewLayout;
        }

        @Override
        public void destroyItem(ViewGroup container, int position, Object object) {
            container.removeView((View) object);
        }
    }

    public interface OnGalleryListener {
        void onFigureChanged(int position, int total);
    }

}
