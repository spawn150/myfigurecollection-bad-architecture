package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.view.View;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.FigureDetail;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

/**
 * Created by spawn on 29/06/16.
 */
public class FigureDetailOrderedFragment extends FigureDetailFragment {

    public FigureDetailOrderedFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FigureDetailOrderedFragment.
     */
    public static FigureDetailOrderedFragment newInstance(FigureDetail figureDetail) {
        FigureDetailOrderedFragment fragment = new FigureDetailOrderedFragment();
        fragment.setArguments(createBundle(figureDetail));
        return fragment;
    }

    @Override
    protected void setExtraViews(View view) {
        view.findViewById(R.id.view_group_number_of_items).setVisibility(View.VISIBLE);
        TextView textViewDetailNumber = (TextView) view.findViewById(R.id.text_view_detail_number);
        textViewDetailNumber.setText(StringUtils.getStringValue(figureDetail.getNumber(), getString(R.string.not_available)));
    }
}
