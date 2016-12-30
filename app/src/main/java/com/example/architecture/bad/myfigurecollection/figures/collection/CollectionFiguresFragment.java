package com.example.architecture.bad.myfigurecollection.figures.collection;

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
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.pojo.Mycollection;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link FiguresFragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link CollectionFiguresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class CollectionFiguresFragment extends FiguresFragment {

    OnFragmentInteractionListener mListener;
    FigureAdapter figureAdapter;

    CollectionFigureItemListener collectionFigureItemListener = new CollectionFigureItemListener() {
        @Override
        public void onCollectionFigureItemClick(View view, Item figureItem) {

            Data data = figureItem.getData();
            Category category = figureItem.getCategory();
            Mycollection mycollection = figureItem.getMycollection();
            DetailedFigure detailedFigure = new DetailedFigure.Builder().setId(data.getId())
                    .setName(data.getName())
                    .setCategory(category.getName())
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

    protected RecyclerView.Adapter createAdapter() {
        figureAdapter = new FigureAdapter(new ArrayList<Item>(), collectionFigureItemListener);
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
            // each data item is just a string in this case
            public ImageView imageViewFigure;
            public TextView textViewFigureName;

            public ViewHolder(View v) {
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

            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(final ViewHolder holder, int position) {

            final Item figureItem = mDataset.get(position);

            Data figureData = figureItem.getData();
            final Context context = holder.imageViewFigure.getContext();

            String url = context.getString(R.string.figure_big_image_url, figureData.getId());

            Picasso.with(context)
                    .load(url)
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

            holder.textViewFigureName.setText(figureData.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFigureItemListener.onCollectionFigureItemClick(holder.imageViewFigure, figureItem);
                }
            });
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

}
