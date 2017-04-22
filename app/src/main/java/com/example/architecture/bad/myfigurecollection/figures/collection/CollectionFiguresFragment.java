package com.example.architecture.bad.myfigurecollection.figures.collection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.pojo.Mycollection;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link FiguresFragment} subclass.
 */
public abstract class CollectionFiguresFragment extends FiguresFragment {

    OnCollectionFiguresFragmentInteractionListener mListener;
    FigureAdapter figureAdapter;

    CollectionFigureItemListener collectionFigureItemListener = (view, figureItem) -> {

        Data data = figureItem.getData();
        String figureId = data.getId();
        Category category = figureItem.getCategory();
        Mycollection mycollection = figureItem.getMycollection();
        DetailedFigure detailedFigure = new DetailedFigure.Builder().setId(figureId)
                .setName(data.getName())
                .setImageUrlMedium(getString(R.string.figure_big_image_url, figureId))
                .setImageUrlFull(getString(R.string.figure_large_image_url, figureId))
                .setCategory(TextUtils.isEmpty(category.getName()) ? getString(R.string.not_available) : category.getName())
                .setReleaseDate(
                        StringUtils.formatDate(data.getReleaseDate(), getString(R.string.not_available)))
                .setScore(StringUtils.getFractionValue(mycollection.getScore(),
                        getString(R.string.denominator_score_value), getString(R.string.not_available)))
                .setPrice(
                        StringUtils.getCurrencyValue(data.getPrice(), getString(R.string.currency_symbol),
                                getString(R.string.not_available)))
                .setNumber(mycollection.getNumber())
                .setWishability(StringUtils.getFractionValue(mycollection.getWishability(),
                        getString(R.string.denominator_wishability_value), getString(R.string.not_available)))
                .setBarcode(data.getBarcode())
                .build();

        onFragmentInteraction(view, detailedFigure);
    };

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnCollectionFiguresFragmentInteractionListener) {
            mListener = (OnCollectionFiguresFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnCollectionFiguresFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    protected RecyclerView.Adapter createAdapter() {
        figureAdapter = new FigureAdapter(new ArrayList<>(), collectionFigureItemListener);
        return figureAdapter;
    }

    protected void showData(ItemState itemState) {
        figureAdapter.updateData(itemState.getItem());
        showData();
    }

    static class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.ViewHolder> {
        private List<Item> mDataset;
        private CollectionFigureItemListener mFigureItemListener;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            private ImageView imageViewFigure;
            private TextView textViewFigureName;
            private Item figureItem;

            public ViewHolder(View v, final CollectionFigureItemListener figureItemListener) {
                super(v);
                imageViewFigure = (ImageView) v.findViewById(R.id.image_view_figure);
                textViewFigureName = (TextView) v.findViewById(R.id.text_view_figure_name);
                v.setOnClickListener(v1 -> figureItemListener.onCollectionFigureItemClick(imageViewFigure, figureItem));
            }

            public void setItem(Item figureItem) {
                this.figureItem = figureItem;
                Data figureData = figureItem.getData();
                textViewFigureName.setText(figureData.getName());

                final Context context = imageViewFigure.getContext();
                String url = context.getString(R.string.figure_big_image_url, figureData.getId());
                Picasso.with(context)
                        .load(url)
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
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public FigureAdapter(List<Item> myDataset, CollectionFigureItemListener figureItemListener) {
            mDataset = myDataset;
            mFigureItemListener = figureItemListener;
        }

        public void updateData(List<Item> myDataset) {
            mDataset = myDataset;
            notifyDataSetChanged();
        }

        // Create new views (invoked by the layout manager)
        @Override
        public FigureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.layout_card_view_collection_figure, parent, false);

            return new ViewHolder(v, mFigureItemListener);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {
            final Item figureItem = mDataset.get(position);
            holder.setItem(figureItem);
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private interface CollectionFigureItemListener {
        void onCollectionFigureItemClick(View view, Item figureItem);
    }

    public interface OnCollectionFiguresFragmentInteractionListener {
        void onCollectionFiguresFragmentInteraction(View view, DetailedFigure figureItem,
                                                    @ActivityUtils.FragmentType int fragmentType);
    }

}
