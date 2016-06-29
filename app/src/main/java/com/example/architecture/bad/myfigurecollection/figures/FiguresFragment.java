package com.example.architecture.bad.myfigurecollection.figures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.Mycollection;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.GlideLoggingListener;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiguresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class FiguresFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    OnFragmentInteractionListener mListener;

    FigureAdapter figureAdapter;

    FigureItemListener figureItemListener = new FigureItemListener() {
        @Override
        public void onFigureItemClick(Item figureItem) {

            Data data = figureItem.getData();
            Category category = figureItem.getCategory();
            Mycollection mycollection = figureItem.getMycollection();
            ItemFigureDetail figureDetail = new ItemFigureDetail.Builder()
                    .setId(data.getId())
                    .setName(data.getName())
                    .setCategory(category.getName())
                    .setReleaseDate(StringUtils.formatDate(data.getReleaseDate(), getString(R.string.not_available)))
                    .setScore(StringUtils.getFractionValue(mycollection.getScore(), getString(R.string.denominator_score_value), getString(R.string.not_available)) )
                    .setPrice(StringUtils.getCurrencyValue(data.getPrice(), getString(R.string.currency_symbol), getString(R.string.not_available)))
                    .setNumber(mycollection.getNumber())
                    .setWishability(StringUtils.getFractionValue(mycollection.getWishability(), getString(R.string.denominator_wishability_value), getString(R.string.not_available)))
                    .setBarcode(data.getBarcode())
                    .build();

            onFragmentInteraction(figureDetail);
        }
    };

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {

        }
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
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
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

        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_collection_figures);
        //performance optimization
        //recyclerView.setHasFixedSize(true);
        figureAdapter = new FigureAdapter(new ArrayList<Item>(), figureItemListener);
        recyclerView.setAdapter(figureAdapter);
        GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);

        loadCollection();
    }

    protected abstract void loadCollection();

    protected abstract void onFragmentInteraction(ItemFigureDetail itemFigureDetail);

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
        void onFragmentInteraction(ItemFigureDetail figureItem, @ActivityUtils.FragmentType int fragmentType);
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
        public FigureAdapter.ViewHolder onCreateViewHolder(ViewGroup parent,
                                                           int viewType) {
            // create a new view
            View v = LayoutInflater.from(parent.getContext())
                    .inflate(R.layout.card_view_collection_figure, parent, false);

            return new ViewHolder(v);
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            final Item figureItem = mDataset.get(position);
            Category category = figureItem.getCategory();
            category.getName();

            Data figureData = figureItem.getData();
            figureData.getId();
            figureData.getName();
            figureData.getReleaseDate();

            String url = holder.imageViewFigure.getContext().getString(R.string.figure_big_image_url, figureData.getId());

            Glide
                .with(holder.imageViewFigure.getContext())
                .load(url)
                .override(256, 320)
                .centerCrop()
                .placeholder(R.drawable.placeholder)
                .listener(new GlideLoggingListener<String, GlideDrawable>())
                .into(holder.imageViewFigure);

            holder.textViewFigureName.setText(figureData.getName());
            holder.itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    mFigureItemListener.onFigureItemClick(figureItem);
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

        void onFigureItemClick(Item figureItem);

    }

}
