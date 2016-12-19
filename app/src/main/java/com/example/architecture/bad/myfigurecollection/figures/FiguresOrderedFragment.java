package com.example.architecture.bad.myfigurecollection.figures;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;

import com.ant_robot.mfc.api.pojo.ItemList;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
        return new FiguresOrderedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadCollection() {
        Call<ItemList> call = MFCRequest.getInstance().getCollectionService().getOrdered(SessionHelper.getUserName(getContext()));
        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                ItemList itemList = response.body();
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getOrdered();
                showData(itemState);
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.e("MFC", "Error on loading Ordered items.", t);
                if(getActivity() != null) {
                    showError(getActivity().getString(R.string.message_error_loading_ordered_items));
                }
            }
        });
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onFragmentInteraction(view, detailedFigure, ActivityUtils.ORDERED_FRAGMENT);
    }

}
