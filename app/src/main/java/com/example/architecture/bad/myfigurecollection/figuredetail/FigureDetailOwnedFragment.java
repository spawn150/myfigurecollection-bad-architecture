package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailOwnedFragment extends FigureDetailFragment {


    public FigureDetailOwnedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureDetailOwnedFragment.
     */
    public static FigureDetailOwnedFragment newInstance(ItemFigureDetail itemFigureDetail) {
        FigureDetailOwnedFragment fragment = new FigureDetailOwnedFragment();
        fragment.setArguments(createBundle(itemFigureDetail));
        return fragment;
    }

    @Override
    protected void setExtraViews(View view) {
        view.findViewById(R.id.view_group_number_of_items).setVisibility(View.VISIBLE);
        view.findViewById(R.id.view_group_score).setVisibility(View.VISIBLE);
        TextView textViewDetailScore = (TextView) view.findViewById(R.id.text_view_detail_score);
        TextView textViewDetailNumber = (TextView) view.findViewById(R.id.text_view_detail_number);
        textViewDetailScore.setText(StringUtils.getStringValue(itemFigureDetail.getScore(), getString(R.string.not_available)));
        textViewDetailNumber.setText(StringUtils.getStringValue(itemFigureDetail.getNumber(), getString(R.string.not_available)));
    }

}
