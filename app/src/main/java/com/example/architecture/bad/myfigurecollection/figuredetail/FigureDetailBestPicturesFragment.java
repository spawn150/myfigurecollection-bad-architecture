package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailBestPicturesFragment extends FigureDetailFragment {

    public FigureDetailBestPicturesFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureDetailBestPicturesFragment.
     */
    public static FigureDetailBestPicturesFragment newInstance(DetailedFigure detailedFigure) {
        FigureDetailBestPicturesFragment fragment = new FigureDetailBestPicturesFragment();
        fragment.setArguments(createBundle(detailedFigure));
        return fragment;
    }

    @Override
    protected void setExtraViews(View view) {
        view.findViewById(R.id.view_group_author).setVisibility(View.VISIBLE);
        view.findViewById(R.id.view_group_image_size).setVisibility(View.VISIBLE);
        view.findViewById(R.id.view_group_price).setVisibility(View.GONE);
        view.findViewById(R.id.view_group_barcode).setVisibility(View.GONE);

        TextView textViewAuthor = (TextView) view.findViewById(R.id.text_view_detail_author);
        textViewAuthor.setText(detailedFigure.getAuthor());
        TextView textViewSize = (TextView) view.findViewById(R.id.text_view_size);
        textViewSize.setText(detailedFigure.getSize());

        if(!TextUtils.isEmpty(detailedFigure.getHits())) {
            view.findViewById(R.id.view_group_hits).setVisibility(View.VISIBLE);
            TextView textViewHits = (TextView) view.findViewById(R.id.text_view_hits);
            textViewHits.setText(detailedFigure.getHits());
        }
        if (!TextUtils.isEmpty(detailedFigure.getWidthResolution()) && !TextUtils.isEmpty(detailedFigure.getHeightResolution())) {
            view.findViewById(R.id.view_group_resolution).setVisibility(View.VISIBLE);
            TextView textViewResolution = (TextView) view.findViewById(R.id.text_view_resolution);
            textViewResolution.setText(view.getContext().getString(R.string.image_resolution_pattern, detailedFigure.getWidthResolution(), detailedFigure.getHeightResolution()));
        }
    }
}
