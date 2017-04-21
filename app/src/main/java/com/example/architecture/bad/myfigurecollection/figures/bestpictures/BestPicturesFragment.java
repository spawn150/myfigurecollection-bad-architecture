package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Picture;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.settings.SettingsActivity;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.Constants;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.RequestCreator;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link FiguresFragment} subclass.
 */
public abstract class BestPicturesFragment extends FiguresFragment {


    public static final String TAG = LatestPicturesFragment.class.getName();
    OnBestPicturesFragmentInteractionListener mListener;
    private PictureAdapter pictureAdapter;

    PictureItemListener mPictureItemListener = new PictureItemListener() {
        @Override
        public void onPictureItemClick(View view, Picture picture) {
            Category category = picture.getCategory();

            DetailedFigure detailedFigure = new DetailedFigure.Builder().setId(picture.getId())
                    .setName(picture.getTitle())
                    .setImageUrlMedium(picture.getMedium())
                    .setImageUrlFull(picture.getFull())
                    .setCategory(TextUtils.isEmpty(category.getName()) ? getString(R.string.not_available) : category.getName())
                    .setAuthor(picture.getAuthor())
                    .setReleaseDate(
                            StringUtils.formatDate(picture.getDate(), getString(R.string.not_available)))
                    .setWidthResolution(picture.getResolution().getWidth())
                    .setHeightResolution(picture.getResolution().getHeight())
                    .setSize(StringUtils.getFileSize(Long.valueOf(picture.getSize())))
                    .setHits(picture.getHits())
                    .build();

            onFragmentInteraction(view, detailedFigure);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnBestPicturesFragmentInteractionListener) {
            mListener = (OnBestPicturesFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnBestPicturesFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        pictureAdapter = new PictureAdapter(new ArrayList<Picture>(), mPictureItemListener);
        return pictureAdapter;
    }

    protected void showData(List<Picture> pictures) {
        pictureAdapter.updateData(pictures);
        showData();
    }

    static class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
        private List<Picture> mDataset;
        private PictureItemListener mPictureItemListener;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        static class ViewHolder extends RecyclerView.ViewHolder {
            private Picture mPicture;
            private ImageView imageViewFigure;
            private TextView textViewFigureName;

            ViewHolder(View v, final PictureItemListener pictureItemListener) {
                super(v);
                imageViewFigure = (ImageView) v.findViewById(R.id.image_view_figure);
                textViewFigureName = (TextView) v.findViewById(R.id.text_view_figure_name);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (!isContentObfuscated(v.getContext())) {
                            pictureItemListener.onPictureItemClick(imageViewFigure, mPicture);
                        }
                    }
                });
            }

            public void setPicture(Picture picture) {
                this.mPicture = picture;

                textViewFigureName.setText(picture.getTitle());

                final Context context = imageViewFigure.getContext();

                Picasso picasso = Picasso.with(context);
                RequestCreator requestCreator;
                Callback callback;
                if (isContentObfuscated(context)) {

                    //nsfw content obfuscated
                    requestCreator = picasso.load(R.drawable.ic_tsuko_bn).error(R.drawable.ic_tsuko_bn);
                    callback = new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) imageViewFigure.getDrawable()).getBitmap();
                            double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                            double newWidth = (CodeUtils.getScreenWidth(context) / 2) * ratio;
                            imageViewFigure.getLayoutParams().height = (int) newWidth;
                            imageViewFigure.requestLayout();
                        }

                        @Override
                        public void onError() {
                        }
                    };

                } else {

                    //in case it is nsfw, the content is not obfuscated
                    requestCreator = picasso.load(picture.getMedium()).placeholder(R.drawable.placeholder);
                    callback = new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) imageViewFigure.getDrawable()).getBitmap();
                            double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                            double newWidth = (CodeUtils.getScreenWidth(context) / 2) * ratio;
                            imageViewFigure.getLayoutParams().height = (int) newWidth;
                            imageViewFigure.requestLayout();
                        }

                        @Override
                        public void onError() {

                            Picasso.with(context)
                                    .load(mPicture.getFull())
                                    .placeholder(R.drawable.placeholder)
                                    .into(imageViewFigure, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Bitmap bitmap = ((BitmapDrawable) imageViewFigure.getDrawable()).getBitmap();
                                            double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                                            double newWidth = (CodeUtils.getScreenWidth(context) / 2) * ratio;
                                            imageViewFigure.getLayoutParams().height = (int) newWidth;
                                            imageViewFigure.requestLayout();
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });

                        }
                    };
                }
                requestCreator.into(imageViewFigure, callback);
            }

            private boolean isContentObfuscated(Context context) {
                return !PreferenceManager.getDefaultSharedPreferences(context).getBoolean(SettingsActivity.KEY_PREF_NSFW_CONTENT_ENABLED, false) &&
                        !mPicture.getNsfw().equals(Constants.SAFE_CONTENT);
            }

        }

        // Provide a suitable constructor (depends on the kind of dataset)
        PictureAdapter(List<Picture> myDataset, PictureItemListener pictureItemListener) {
            mDataset = myDataset;
            mPictureItemListener = pictureItemListener;
        }

        void updateData(List<Picture> myDataset) {
            mDataset = myDataset;
            notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card_view_collection_figure, parent, false);

            return new ViewHolder(v, mPictureItemListener);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            holder.setPicture(mDataset.get(position));
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private interface PictureItemListener {
        void onPictureItemClick(View view, Picture picture);
    }

    public interface OnBestPicturesFragmentInteractionListener {
        void onBestPicturesFragmentInteraction(View view, DetailedFigure figureItem,
                                               @ActivityUtils.FragmentType int fragmentType);
    }
}
