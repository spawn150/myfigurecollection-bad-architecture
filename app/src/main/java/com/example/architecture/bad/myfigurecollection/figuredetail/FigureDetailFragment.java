package com.example.architecture.bad.myfigurecollection.figuredetail;

import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.util.GlideLoggingListener;
import com.example.architecture.bad.myfigurecollection.util.StringUtils;

/**
 * A placeholder fragment containing a simple view.
 */
public class FigureDetailFragment extends Fragment {

    private static final String ARG_FIGURE_DETAIL_PARAM = "figuredetailparam";

    private ItemFigureDetail itemFigureDetail;

    public FigureDetailFragment() {
        //Required empty constructor by Android framework
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment FiguresOwnedFragment.
     */
    public static FigureDetailFragment newInstance(ItemFigureDetail itemFigureDetail) {
        FigureDetailFragment fragment = new FigureDetailFragment();
        Bundle args = new Bundle();
        args.putParcelable(ARG_FIGURE_DETAIL_PARAM, itemFigureDetail);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (getArguments() != null) {
            itemFigureDetail = getArguments().getParcelable(ARG_FIGURE_DETAIL_PARAM);
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
        TextView textViewDetailScore = (TextView) view.findViewById(R.id.text_view_detail_score);
        TextView textViewDetailNumber = (TextView) view.findViewById(R.id.text_view_detail_number);
        textViewDetailFigureName.setText(StringUtils.extractStringAfterSeparatorRepeatedNTimes(itemFigureDetail.getName(), '-', 2));
        textViewDetailCategory.setText(itemFigureDetail.getCategory());
        textViewDetailReleaseDate.setText(itemFigureDetail.getReleaseDate());
        textViewDetailScore.setText(itemFigureDetail.getScore());
        textViewDetailNumber.setText(itemFigureDetail.getNumber());

    }
}
