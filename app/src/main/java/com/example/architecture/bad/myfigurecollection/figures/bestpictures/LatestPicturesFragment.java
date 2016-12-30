package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.support.v7.widget.RecyclerView;
import android.view.View;

import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;

/**
 * A placeholder fragment containing a simple view.
 */
public class LatestPicturesFragment extends FiguresFragment {

    public static final String TAG = LatestPicturesFragment.class.getName();

    public LatestPicturesFragment() {
    }

    public static LatestPicturesFragment newInstance() {
        return new LatestPicturesFragment();
    }

    @Override
    protected void loadCollection() {
        /*
        Call<BestPictureGallery> call = MFCRequest.getInstance().getBestPicturesService().getPicturesOfTheDay(0);
        call.enqueue(new Callback<BestPictureGallery>() {
            @Override
            public void onResponse(Call<BestPictureGallery> call, Response<BestPictureGallery> response) {
                List<Picture> pictures = response.body().getGallery().getPictures()

            }

            @Override
            public void onFailure(Call<BestPictureGallery> call, Throwable t) {
                Log.e("MFC", "Error on loading Best Pictures items.", t);
                if (getActivity() != null) {
                    String title = String.format(getActivity().getString(R.string.title_error_loading_items), getActivity().getString(R.string.potd_items_value));
                    showError(title);
                }
            }
        }

        new Callback<ItemList>() {
            @Override
            public void onResponse(Call<ItemList> call, Response<ItemList> response) {
                ItemList itemList = response.body();
                Log.d("MFC", itemList.toString());
                ItemState itemState = itemList.getCollection().getOrdered();
                if (itemState.getItem() != null && !itemState.getItem().isEmpty()) {
                    showData(itemState);
                } else {
                    String title = String.format(getActivity().getString(R.string.title_error_no_items_in_list), getActivity().getString(R.string.ordered_items_value));
                    String message = getActivity().getString(R.string.message_error_no_ordered_items_in_list);
                    showError(title, message);
                }
            }

            @Override
            public void onFailure(Call<ItemList> call, Throwable t) {
                Log.e("MFC", "Error on loading Ordered items.", t);
                if (getActivity() != null) {
                    String title = String.format(getActivity().getString(R.string.title_error_loading_items), getActivity().getString(R.string.ordered_items_value));
                    showError(title);
                }
            }
        });
        */
    }

    @Override
    protected RecyclerView.Adapter createAdapter() {
        return null;
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {

    }

}
