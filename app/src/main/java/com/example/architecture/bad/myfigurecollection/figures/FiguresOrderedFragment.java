package com.example.architecture.bad.myfigurecollection.figures;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;

import com.ant_robot.mfc.api.pojo.ItemList;
import com.ant_robot.mfc.api.pojo.ItemState;
import com.ant_robot.mfc.api.request.MFCRequest;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

/**
 * A simple {@link Fragment} subclass.
 * Use the {@link FiguresOrderedFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class FiguresOrderedFragment extends FiguresFragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;


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
    protected void loadCollection() {
        MFCRequest.INSTANCE.getCollectionService().getOrdered("STARlock"/*"spawn150"*//*"climbatize"*/, new Callback<ItemList>() {
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


}
