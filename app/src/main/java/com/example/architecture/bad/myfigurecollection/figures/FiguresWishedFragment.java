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
    FiguresWishedFragment fragment = new FiguresWishedFragment();
    Bundle args = new Bundle();
    fragment.setArguments(args);
    return fragment;
  }

  @Override public void onCreate(Bundle savedInstanceState) {
    super.onCreate(savedInstanceState);
    if (getArguments() != null) {
    }
  }

  @Override protected void loadCollection() {
    MFCRequest.INSTANCE.getCollectionService()
        .getWished("spawn150"/*"STARlock"*//*"climbatize"*/, new Callback<ItemList>() {
          @Override public void success(ItemList itemList, Response response) {
            Log.d("MFC", itemList.toString());
            ItemState itemState = itemList.getCollection().getWished();
            figureAdapter.updateData(itemState.getItem());
          }

          @Override public void failure(RetrofitError error) {
            Log.e("MFC", error.getLocalizedMessage());
          }
        });
  }

  @Override protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
    mListener.onFragmentInteraction(view, detailedFigure, ActivityUtils.WISHED_FRAGMENT);
  }
}
