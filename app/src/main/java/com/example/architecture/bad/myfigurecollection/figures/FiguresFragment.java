package com.example.architecture.bad.myfigurecollection.figures;

import android.content.Context;
import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link FiguresFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 */
public abstract class FiguresFragment extends Fragment {

    protected static final int LOADING = 0;
    protected static final int SUCCESS = 1;
    protected static final int ERROR = 2;

    @IntDef({LOADING, SUCCESS, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    private static final int LAYOUT_COLUMNS = 2;
    private ViewFlipper viewFlipper;
    private TextView textViewErrorMessage;
    private TextView textViewErrorTitle;
    OnFragmentInteractionListener mListener;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_figures, container, false);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
            throw new RuntimeException(
                    context.toString() + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper_figures);
        textViewErrorTitle = (TextView) view.findViewById(R.id.text_view_error_title);
        textViewErrorMessage = (TextView) view.findViewById(R.id.text_view_error_message);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_collection_figures);
        //performance optimization
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(createAdapter());

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(LAYOUT_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        loadCollection();
    }

    protected abstract void loadCollection();
    protected abstract RecyclerView.Adapter createAdapter();

    protected abstract void onFragmentInteraction(View view, DetailedFigure detailedFigure);


    protected void showError(String title) {
        showError(title, "");
    }

    protected void showError(String title, String message) {
        textViewErrorTitle.setText(title);
        textViewErrorMessage.setText(message);
        setViewState(ERROR);
    }

    protected void setViewState(@ViewState int viewState) {
        viewFlipper.setDisplayedChild(viewState);
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p/>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        void onFragmentInteraction(View view, DetailedFigure figureItem,
                                   @ActivityUtils.FragmentType int fragmentType);
    }

}
