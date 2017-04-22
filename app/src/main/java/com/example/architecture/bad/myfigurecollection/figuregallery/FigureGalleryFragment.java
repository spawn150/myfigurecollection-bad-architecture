package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.content.Context;
import android.graphics.Point;
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
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.GalleryFigure;
import com.example.architecture.bad.myfigurecollection.util.BitmapResizeTransformation;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.ProgressImageViewTarget;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class FigureGalleryFragment extends Fragment {

    protected static final String TAG = FigureGalleryFragment.class.getSimpleName();
    protected static final String ARG_FIGURE_ID = "figureid";

    protected String figureId;
    protected int gallerySize;
    protected ViewPager galleryPager;
    protected OnGalleryListener galleryListener;

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

        loadGallery();
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        galleryPager.removeOnPageChangeListener(onPageChangeListener);
    }

    protected abstract void loadGallery();

    protected static class FullScreenImageAdapter extends PagerAdapter {

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
            ProgressBar progressBar = (ProgressBar) viewLayout.findViewById(R.id.gallery_progressbar);

            ProgressImageViewTarget target = new ProgressImageViewTarget(imgDisplay, progressBar);
            //Fix Picasso WeakReference issue - http://stackoverflow.com/questions/24180805/onbitmaploaded-of-target-object-not-called-on-first-load
            imgDisplay.setTag(target);

            Point screenSize = CodeUtils.getScreenSize(container.getContext());
            Picasso
                    .with(container.getContext())
                    .load(galleryFigure.getUrl())
                    .transform(new BitmapResizeTransformation(screenSize.x, screenSize.y))
                    .into(target);

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
