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
 * Use the {@link FiguresOwnedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiguresOwnedFragment extends FiguresFragment {

    public FiguresOwnedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FiguresOwnedFragment.
     */
    public static FiguresOwnedFragment newInstance() {
        return new FiguresOwnedFragment();
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    protected void loadCollection() {

        Call<ItemList> call = MFCRequest.getInstance().getCollectionService().getOwned(SessionHelper.getUserName(getContext()));
        call.enqueue(new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                ItemList itemList = response.body();
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getOwned();
                if (itemState.getItem() != null && !itemState.getItem().isEmpty()) {
                    showData(itemState);
                } else {
                    String title = String.format(getActivity().getString(R.string.title_error_no_items_in_list), getActivity().getString(R.string.owned_items_value));
                    String message = String.format(getActivity().getString(R.string.message_error_no_items_in_list), getActivity().getString(R.string.owned_items_value));
                    showError(title, message);
                }

            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.e("MFC", "Error on loading Owned items.", t);
                if(getActivity() != null) {
                    String title = String.format(getActivity().getString(R.string.title_error_loading_items), getActivity().getString(R.string.owned_items_value));
                    showError(title);
                }
            }
        });
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onFragmentInteraction(view, detailedFigure, ActivityUtils.OWNED_FRAGMENT);
    }


}
