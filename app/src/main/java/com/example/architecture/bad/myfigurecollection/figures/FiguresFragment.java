package com.example.architecture.bad.myfigurecollection.figures;

import android.content.Context;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.ItemList;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.pojo.PictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.util.GlideLoggingListener;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;
/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiguresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link FiguresFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiguresFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private FigureAdapter figureAdapter;

    public FiguresFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FiguresFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static FiguresFragment newInstance() {
        FiguresFragment fragment = new FiguresFragment();
        Bundle args = new Bundle();
        fragment.setArguments(args);
        return fragment;
    }

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

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
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

        RecyclerView recyclerView = (RecyclerView)view.findViewById(R.id.recycle_view_collection_figures);
        //performance optimization
        recyclerView.setHasFixedSize(true);
        figureAdapter = new FigureAdapter(new ArrayList<Item>());
        recyclerView.setAdapter(figureAdapter);
        //recyclerView.setAdapter(new FigureAdapter(new int[]{R.drawable.pod_test, R.drawable.pod2_test, R.drawable.pod3_test, R.drawable.pod4_test}));
        //recyclerView.setLayoutManager(new LinearLayoutManager(view.getContext()));
        /*GridLayoutManager gridLayoutManager = new GridLayoutManager(view.getContext(), 2, GridLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(gridLayoutManager);
        */

        StaggeredGridLayoutManager staggeredGridLayoutManager = new StaggeredGridLayoutManager(2, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_NONE);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);


        MFCRequest.INSTANCE.getCollectionService().getCollection("spawn150"/*"STARlock"*//*"climbatize"*/, new Callback<ItemList>() {
            @Override
            public void success(ItemList itemList, Response response) {
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getOwned();
                figureAdapter.updateData(itemState.getItem());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("MFC", error.getLocalizedMessage());
            }
        });

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
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    private static class FigureAdapter extends RecyclerView.Adapter<FigureAdapter.ViewHolder> {
        private List<Item> mDataset;

        // Provide a reference to the views for each data item
        // Complex data items may need more than one view per item, and
        // you provide access to all the views for a data item in a view holder
        public static class ViewHolder extends RecyclerView.ViewHolder {
            // each data item is just a string in this case
            public ImageView imageViewFigure;
            public TextView textViewFigureName;
            public ViewHolder(View v) {
                super(v);
                imageViewFigure = (ImageView)v.findViewById(R.id.image_view_figure);
                textViewFigureName = (TextView)v.findViewById(R.id.text_view_figure_name);
            }
        }

        // Provide a suitable constructor (depends on the kind of dataset)
        public FigureAdapter(List<Item> myDataset) {
            mDataset = myDataset;
        }

        public void updateData(List<Item> myDataset){
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

            ViewHolder vh = new ViewHolder(v);
            return vh;
        }

        // Replace the contents of a view (invoked by the layout manager)
        @Override
        public void onBindViewHolder(ViewHolder holder, int position) {

            Item figureItem = mDataset.get(position);
            Category category = figureItem.getCategory();
            category.getName();

            Data figureData = figureItem.getData();
            figureData.getId();
            figureData.getName();
            figureData.getReleaseDate();

            String url = holder.imageViewFigure.getContext().getString(R.string.figure_large_image_url, figureData.getId());

            Glide
                .with(holder.imageViewFigure.getContext())
                .load(url)
                .fitCenter()
                .placeholder(R.drawable.placeholder)
                .listener(new GlideLoggingListener<String, GlideDrawable>())
                .into(holder.imageViewFigure);

            holder.textViewFigureName.setText(figureData.getName());

        }

        // Return the size of your dataset (invoked by the layout manager)
        @Override
        public int getItemCount() {
            return mDataset.size();
        }
    }


}
