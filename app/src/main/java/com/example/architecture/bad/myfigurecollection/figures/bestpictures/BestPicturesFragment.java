package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Picture;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class BestPicturesFragment extends FiguresFragment {


    public static final String TAG = LatestPicturesFragment.class.getName();
    OnFragmentInteractionListener mListener;
    private PictureAdapter pictureAdapter;

    PictureItemListener mPictureItemListener = new PictureItemListener() {
        @Override
        public void onPictureItemClick(View view, Picture picture) {

            /*
            picture.getAuthor();
            picture.getDate();
            picture.getCategory().getName();
            picture.getTitle();
            picture.getMedium();
            */

            Category category = picture.getCategory();

            DetailedFigure detailedFigure = new DetailedFigure.Builder().setId(picture.getId())
                    .setName(picture.getTitle())
                    .setImageUrl(picture.getFull())
                    .setCategory(category.getName())
                    .setAuthor(picture.getAuthor())
                    .setReleaseDate(
                            StringUtils.formatDate(picture.getDate(), getString(R.string.not_available)))
                    .build();

            onFragmentInteraction(view, detailedFigure);
        }
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener");
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

    protected void showError() {
        String title = String.format(getActivity().getString(R.string.title_error_loading_items), getActivity().getString(R.string.potd_items_value));
        showError(title);
    }

    static class PictureAdapter extends RecyclerView.Adapter<PictureAdapter.ViewHolder> {
        private List<Picture> mDataset;
        private PictureItemListener mPictureItemListener;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            ImageView imageViewFigure;
            TextView textViewFigureName;

            ViewHolder(View v) {
                super(v);
                imageViewFigure = (ImageView) v.findViewById(R.id.image_view_figure);
                textViewFigureName = (TextView) v.findViewById(R.id.text_view_figure_name);
                v.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                    }
                });
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

            return new ViewHolder(v);
        }

        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Context context = holder.imageViewFigure.getContext();
            final Picture picture = mDataset.get(position);

            Picasso.with(context)
                    .load(picture.getMedium())
                    .placeholder(R.drawable.placeholder)
                    .into(holder.imageViewFigure, new Callback() {
                        @Override
                        public void onSuccess() {
                            Bitmap bitmap = ((BitmapDrawable) holder.imageViewFigure.getDrawable()).getBitmap();
                            double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                            double newWidth = (CodeUtils.getScreenWidth(context) / 2) * ratio;
                            holder.imageViewFigure.getLayoutParams().height = (int) newWidth;
                            holder.imageViewFigure.requestLayout();
                        }

                        @Override
                        public void onError() {

                            Picasso.with(context)
                                    .load(picture.getFull())
                                    .placeholder(R.drawable.placeholder)
                                    .into(holder.imageViewFigure, new Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Bitmap bitmap = ((BitmapDrawable) holder.imageViewFigure.getDrawable()).getBitmap();
                                            double ratio = (double) bitmap.getHeight() / (double) bitmap.getWidth();
                                            double newWidth = (CodeUtils.getScreenWidth(context) / 2) * ratio;
                                            holder.imageViewFigure.getLayoutParams().height = (int) newWidth;
                                            holder.imageViewFigure.requestLayout();
                                        }

                                        @Override
                                        public void onError() {

                                        }
                                    });

                        }
                    });

            holder.textViewFigureName.setText(picture.getTitle());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mPictureItemListener.onPictureItemClick(holder.imageViewFigure, picture);
                }
            });

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

}
