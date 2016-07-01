package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public abstract class FigureDetailFragment extends Fragment {

    private static final String ARG_FIGURE_DETAIL_PARAM = "figuredetailparam";

    protected DetailedFigure detailedFigure;

    static Bundle createBundle(DetailedFigure detailedFigure){
        Bundle args = new Bundle();
        args.putParcelable(ARG_FIGURE_DETAIL_PARAM, detailedFigure);
        return args;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            detailedFigure = getArguments().getParcelable(ARG_FIGURE_DETAIL_PARAM);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_figure_detail, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        setupViews(view);
    }

    private void setupViews(View view){
        TextView textViewDetailFigureName = (TextView) view.findViewById(R.id.text_view_detail_figure_name);
        TextView textViewDetailCategory = (TextView) view.findViewById(R.id.text_view_detail_category);
        TextView textViewDetailReleaseDate = (TextView) view.findViewById(R.id.text_view_detail_release_date);
        TextView textViewDetailPrice = (TextView) view.findViewById(R.id.text_view_detail_price);
        TextView textViewDetailBarcode = (TextView) view.findViewById(R.id.text_view_detail_barcode);
        textViewDetailFigureName.setText(StringUtils.extractStringAfterSeparatorRepeatedNTimes(detailedFigure.getName(), '-', 2));
        textViewDetailCategory.setText(detailedFigure.getCategory());
        textViewDetailReleaseDate.setText(detailedFigure.getReleaseDate());
        textViewDetailPrice.setText(StringUtils.getStringValue(detailedFigure.getPrice(), getString(R.string.not_available)));
        textViewDetailBarcode.setText(StringUtils.getStringValue(detailedFigure.getBarcode(), getString(R.string.not_available)));

        setExtraViews(view);
    }

    protected abstract void setExtraViews(View view);
}
