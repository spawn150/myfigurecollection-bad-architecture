package com.example.architecture.bad.myfigurecollection.figures;

import android.os.Bundle;
import android.support.annotation.IntDef;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.example.architecture.bad.myfigurecollection.R;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * A simple {@link Fragment} subclass.
 */
public abstract class FiguresFragment extends Fragment {

    protected static final int LOADING = 0;
    protected static final int SUCCESS = 1;
    protected static final int ERROR = 2;
    private static final String TAG = FiguresFragment.class.getName();

    @IntDef({LOADING, SUCCESS, ERROR})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    private static final int LAYOUT_COLUMNS = 2;
    private ViewFlipper viewFlipper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private TextView textViewErrorMessage;
    private TextView textViewErrorTitle;

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);
            
            if (dy > 0) {
                Log.d(TAG, "Scrolling down...");
            } else {
                Log.d(TAG, "Scrolling up...");
            }
        }
    };

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
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewFlipper = (ViewFlipper) view.findViewById(R.id.view_flipper_figures);
        textViewErrorTitle = (TextView) view.findViewById(R.id.text_view_error_title);
        textViewErrorMessage = (TextView) view.findViewById(R.id.text_view_error_message);
        RecyclerView recyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_collection_figures);
        recyclerView.addOnScrollListener(scrollListener);
        //performance optimization
        recyclerView.setHasFixedSize(true);
        recyclerView.setAdapter(createAdapter());

        StaggeredGridLayoutManager staggeredGridLayoutManager =
                new StaggeredGridLayoutManager(LAYOUT_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        staggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        recyclerView.setLayoutManager(staggeredGridLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.figures_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            loadCollection();
        });

        loadCollection();
    }

    protected abstract void loadCollection();

    protected abstract RecyclerView.Adapter createAdapter();

    protected abstract void onFragmentInteraction(View view, DetailedFigure detailedFigure);

    protected void showData() {
        setViewState(SUCCESS);
    }

    protected void showError(String title) {
        showError(title, "");
    }

    protected void showError(String title, String message) {
        textViewErrorTitle.setOnClickListener(v -> {
            setViewState(LOADING);
            loadCollection();
        });
        textViewErrorTitle.setText(title);
        textViewErrorMessage.setText(message);
        setViewState(ERROR);
    }

    protected void setViewState(@ViewState int viewState) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewState);
    }

}
