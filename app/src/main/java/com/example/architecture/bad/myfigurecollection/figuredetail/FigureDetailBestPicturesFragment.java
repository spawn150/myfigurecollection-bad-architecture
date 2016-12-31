package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

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
        view.findViewById(R.id.view_group_price).setVisibility(View.GONE);
        view.findViewById(R.id.view_group_barcode).setVisibility(View.GONE);
        TextView textViewAuthor = (TextView) view.findViewById(R.id.text_view_detail_author);
        textViewAuthor.setText(detailedFigure.getAuthor());
    }
}
