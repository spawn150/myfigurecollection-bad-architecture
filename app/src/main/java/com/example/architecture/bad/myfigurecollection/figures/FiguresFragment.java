package com.example.architecture.bad.myfigurecollection.figures;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewSwitcher;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.pojo.Mycollection;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiguresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class FiguresFragment extends Fragment {

    private static final int LAYOUT_COLUMNS = 2;

    private ViewSwitcher viewSwitcher;
    OnFragmentInteractionListener mListener;
    FigureAdapter figureAdapter;

    FigureItemListener figureItemListener = new FigureItemListener() {
        @Override
        public void onFigureItemClick(View view, Item figureItem) {

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
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_figures, container, false);
    }

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewSwitcher = (ViewSwitcher) view.findViewById(R.id.view_switcher_figures);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_collection_figures);
        //performance optimization
        recyclerView.setHasFixedSize(true);
        figureAdapter = new FigureAdapter(new ArrayList<Item>(), figureItemListener);
        recyclerView.setAdapter(figureAdapter);

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(LAYOUT_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        loadCollection();
    }

    protected abstract void loadCollection();

    protected abstract void onFragmentInteraction(View view, DetailedFigure detailedFigure);

    protected void showData(ItemState itemState) {
        viewSwitcher.showNext();
        figureAdapter.updateData(itemState.getItem());
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view, DetailedFigure figureItem,
                                   @ActivityUtils.FragmentType int fragmentType);
    }

    static class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.ViewHolder> {
        private List<Item> mDataset;
        private FigureItemListener mFigureItemListener;

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
        public FigureAdapter(List<Item> myDataset, FigureItemListener figureItemListener) {
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
            Category category = figureItem.getCategory();
            category.getName();

            Data figureData = figureItem.getData();
            figureData.getId();
            figureData.getName();
            figureData.getReleaseDate();

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
                    mFigureItemListener.onFigureItemClick(holder.imageViewFigure, figureItem);
                }
            });
        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }

    private interface FigureItemListener {
        void onFigureItemClick(View view, Item figureItem);
    }
}
