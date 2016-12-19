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
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiguresWishedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiguresWishedFragment extends FiguresFragment {

    public FiguresWishedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FiguresOwnedFragment.
     */
    public static FiguresWishedFragment newInstance() {
        return new FiguresWishedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadCollection() {

        Call<ItemList> call = MFCRequest.getInstance().getCollectionService().getWished(SessionHelper.getUserName(getContext()));
        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                ItemList itemList = response.body();
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getWished();
                showData(itemState);
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.e("MFC", "Error on loading Wished items.", t);
            }
        });
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onFragmentInteraction(view, detailedFigure, ActivityUtils.WISHED_FRAGMENT);
    }
}
