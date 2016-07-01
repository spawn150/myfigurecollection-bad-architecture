package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.FigureDetail;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailWishedFragment extends FigureDetailFragment {


    public FigureDetailWishedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureDetailWishedFragment.
     */
    public static FigureDetailWishedFragment newInstance(FigureDetail figureDetail) {
        FigureDetailWishedFragment fragment = new FigureDetailWishedFragment();
        fragment.setArguments(createBundle(figureDetail));
        return fragment;
    }

    @Override
    protected void setExtraViews(View view) {
        view.findViewById(R.id.view_group_wishability).setVisibility(View.VISIBLE);
        TextView textViewDetailWishability = (TextView) view.findViewById(R.id.text_view_detail_wishability);
        textViewDetailWishability.setText(StringUtils.getStringValue(figureDetail.getWishability(), getString(R.string.not_available)));
    }

}
