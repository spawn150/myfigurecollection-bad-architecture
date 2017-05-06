package com.example.architecture.bad.myfigurecollection.figuregallery;

import android.content.Context;
import android.graphics.Point;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.GalleryFigure;
import com.example.architecture.bad.myfigurecollection.download.DownloadService;
import com.example.architecture.bad.myfigurecollection.util.BitmapResizeTransformation;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.ProgressImageViewTarget;
import com.example.architecture.bad.myfigurecollection.views.TouchImageView;
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
    private int currentItemSelected;
    private int mPage = 1;
    protected int mMaxNumPages;
    protected boolean loading;

    private ViewPager.OnPageChangeListener onPageChangeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
            Log.d(TAG, "onPageScrolled");
        }

        @Override
        public void onPageSelected(int position) {
            Log.d(TAG, "onPageSelected");
            currentItemSelected = position;
            galleryListener.onFigureChanged(++position, gallerySize);

            int ITEMS_OFFSET = 10;
            if (!loading && position + ITEMS_OFFSET > galleryPager.getAdapter().getCount()) {
                int nextPage = ++mPage;
                if (nextPage <= mMaxNumPages) {
                    Log.v(TAG, "Load new gallery data!");
                    loading = true;
                    loadGallery(nextPage);
                }
            }
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
        //setRetainInstance(true);

        setHasOptionsMenu(true);
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

        loadGallery(mPage);
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.menu_figure_detail, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId()) {
            case R.id.action_download:
                downloadImage();
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();

        galleryPager.removeOnPageChangeListener(onPageChangeListener);
    }

    protected abstract void loadGallery(int page);

    private void downloadImage() {
        FullScreenImageAdapter fullScreenImageAdapter = (FullScreenImageAdapter) galleryPager.getAdapter();
        if (fullScreenImageAdapter != null) {
            GalleryFigure figureToDownload = fullScreenImageAdapter.getItemByPosition(currentItemSelected);
            if (figureToDownload != null) {
                String imageUrl = figureToDownload.getUrl();
                String id = figureToDownload.getId();
                if (!TextUtils.isEmpty(id) && !TextUtils.isEmpty(imageUrl))
                    DownloadService.startActionDownload(getContext(), id, imageUrl);
            }
        }
    }

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
            TouchImageView imgDisplay;

            inflater = (LayoutInflater) container.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            View viewLayout = inflater.inflate(R.layout.layout_image_fullscreen, container,
                    false);

            GalleryFigure galleryFigure = galleryFigures.get(position);

            imgDisplay = (TouchImageView) viewLayout.findViewById(R.id.touch_image_view_figure);
            ProgressBar progressBar = (ProgressBar) viewLayout.findViewById(R.id.gallery_progressbar);

            ProgressImageViewTarget target = new ProgressImageViewTarget(imgDisplay, progressBar);
            //Fix Picasso WeakReference issue - http://stackoverflow.com/questions/24180805/onbitmaploaded-of-target-object-not-called-on-first-load
            imgDisplay.setTag(target);

            Point screenSize = CodeUtils.getScreenSize(container.getContext());
            Picasso
                    .with(container.getContext())
                    .load(galleryFigure.getUrl())
                    .error(R.drawable.ic_tsuko_bn)
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

        public GalleryFigure getItemByPosition(int position) {
            return galleryFigures.get(position);
        }

        public void addGalleryFigures(List<GalleryFigure> newGalleryFigures) {
            this.galleryFigures.addAll(newGalleryFigures);
            notifyDataSetChanged();
        }
    }

    public interface OnGalleryListener {
        void onFigureChanged(int position, int total);
    }

}
