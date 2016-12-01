package com.example.architecture.bad.myfigurecollection.figures;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import android.view.View;
import com.ant_robot.mfc.api.pojo.ItemList;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiguresOrderedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiguresOrderedFragment extends FiguresFragment {

    public FiguresOrderedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FiguresOwnedFragment.
     */
    public static FiguresOrderedFragment newInstance() {
        FiguresOrderedFragment fragment = new FiguresOrderedFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadCollection() {
        MFCRequest.INSTANCE.getCollectionService().getOrdered("spawn150"/*"STARlock"*//*"climbatize"*/, new Callback<ItemList>() {
            @Override
            public void success(ItemList itemList, Response response) {
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getOrdered();
                figureAdapter.updateData(itemState.getItem());
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e("MFC", error.getLocalizedMessage());
            }
        });

    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onFragmentInteraction(view, detailedFigure, ActivityUtils.ORDERED_FRAGMENT);
    }

}
