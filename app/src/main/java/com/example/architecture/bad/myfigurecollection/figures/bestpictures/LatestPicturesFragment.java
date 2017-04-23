package com.example.architecture.bad.myfigurecollection.figures.bestpictures;

import android.util.Log;
import android.view.View;

import com.ant_robot.mfc.api.pojo.Picture;
import com.ant_robot.mfc.api.pojo.PictureGallery;
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
public class LatestPicturesFragment extends BestPicturesFragment {


    public LatestPicturesFragment() {
    }

    public static LatestPicturesFragment newInstance() {
        return new LatestPicturesFragment();
    }

    @Override
    protected void loadCollection(int page) {

        Call<PictureGallery> call = MFCRequest.getInstance().getGalleryService().getLatestPictures(page);
        call.enqueue(new retrofit2.Callback<PictureGallery>() {
            @Override
            public void onResponse(Call<PictureGallery> call, Response<PictureGallery> response) {
                List<Picture> pictures = response.body().getGallery().getPicture();
                if (pictures != null && !pictures.isEmpty()) {
                    showData(pictures);
                } else {
                    showError();
                }
            }

            @Override
            public void onFailure(Call<PictureGallery> call, Throwable t) {
                Log.e("MFC", "Error on loading Latest Pictures items.", t);
                if (getActivity() != null) {
                    showError();
                }
            }
        });
    }

    private void showError(){
        String title = String.format(getActivity().getString(R.string.title_error_loading_pictures), getActivity().getString(R.string.latest_items_value));
        showError(title);
    }

    @Override
    protected void onFragmentInteraction(View view, DetailedFigure detailedFigure) {
        mListener.onBestPicturesFragmentInteraction(view, detailedFigure, ActivityUtils.BEST_PICTURES);
    }

}
