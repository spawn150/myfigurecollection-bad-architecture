package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.util.Log;
import android.view.View;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PotdPictureGallery;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

import java.util.List;

import retrofit2.Call;
import retrofit2.Response;

/**
 * A placeholder fragment containing a simple view.
 */
public class PictureOfTheDayFragment extends BestPicturesFragment {

    public static final String TAG = PictureOfTheDayFragment.class.getName();

    public PictureOfTheDayFragment() {
    }

    public static PictureOfTheDayFragment newInstance() {
        return new PictureOfTheDayFragment();
    }

    @Override
    protected void loadCollection(int page) {

        Call<PotdPictureGallery> call = MFCRequest.getInstance().getBestPicturesService().getPicturesOfTheDay(page);
        call.enqueue(new retrofit2.Callback<PotdPictureGallery>() {
            @Override
            public void onResponse(Call<PotdPictureGallery> call, Response<PotdPictureGallery> response) {
                List<Picture> pictures = response.body().getGallery().getPictures();
                if (pictures != null && !pictures.isEmpty()) {
                    showData(pictures);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<PotdPictureGallery> call, Throwable t) {
                Log.e("MFC", "Error on loading Best Pictures items.", t);
                if (getActivity() != null) {
                    showError();
                }
            }
        });
    }

    private void showError() {
        String title = String.format(getActivity().getString(R.string.title_error_loading_pictures), getActivity().getString(R.string.potd_items_value));
        showError(title);
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onBestPicturesFragmentInteraction(view, detailedFigure, ActivityUtils.BEST_PICTURES);
    }


}
