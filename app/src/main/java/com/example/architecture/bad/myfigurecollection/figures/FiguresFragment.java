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

    private static final String TAG = FiguresFragment.class.getName();
    private static final int LAYOUT_COLUMNS = 2;
    private static final int ITEMS_OFFSET = 20;

    protected static final int LOADING = 0;
    protected static final int SUCCESS = 1;
    protected static final int MESSAGING = 2;

    @IntDef({LOADING, SUCCESS, MESSAGING})
    @Retention(RetentionPolicy.SOURCE)
    public @interface ViewState {
    }

    private boolean loading;
    private int mPage;
    private ViewFlipper viewFlipper;
    private SwipeRefreshLayout swipeRefreshLayout;
    private RecyclerView mRecyclerView;
    private StaggeredGridLayoutManager mStaggeredGridLayoutManager;
    private TextView textViewErrorMessage;
    private TextView textViewErrorTitle;
    protected int mMaxNumPages;
    private int[] firstVisibleItemPositions = new int[LAYOUT_COLUMNS];

    private RecyclerView.OnScrollListener scrollListener = new RecyclerView.OnScrollListener() {
        @Override
        public void onScrolled(RecyclerView recyclerView, int dx, int dy) {
            super.onScrolled(recyclerView, dx, dy);

            if (dy > 0) {

                int visibleItemCount = mRecyclerView.getChildCount();
                int totalItemCount = mStaggeredGridLayoutManager.getItemCount();
                mStaggeredGridLayoutManager.findFirstVisibleItemPositions(firstVisibleItemPositions);

                int lastItemVisible = visibleItemCount + firstVisibleItemPositions[0];
                Log.v(TAG, "visibleItemCount: " + visibleItemCount);
                Log.v(TAG, "firstVisibleItemPositions: " + firstVisibleItemPositions[LAYOUT_COLUMNS - 1]);
                Log.v(TAG, "lastItemVisible: " + lastItemVisible);
                Log.v(TAG, "totalItemCount: " + totalItemCount);
                if (!loading && lastItemVisible + ITEMS_OFFSET > totalItemCount) {
                    int nextPage = ++mPage;
                    if (nextPage <= mMaxNumPages) {
                        Log.d(TAG, "Must be loaded more items!");
                        loading = true;
                        loadCollection(nextPage);
                    }
                }

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
        mRecyclerView = (RecyclerView) view.findViewById(R.id.recycle_view_collection_figures);
        mRecyclerView.addOnScrollListener(scrollListener);
        //performance optimization
        mRecyclerView.setHasFixedSize(true);
        mRecyclerView.setAdapter(createAdapter());

        mStaggeredGridLayoutManager =
                new StaggeredGridLayoutManager(LAYOUT_COLUMNS, StaggeredGridLayoutManager.VERTICAL);
        mStaggeredGridLayoutManager.setGapStrategy(StaggeredGridLayoutManager.GAP_HANDLING_MOVE_ITEMS_BETWEEN_SPANS);
        mRecyclerView.setLayoutManager(mStaggeredGridLayoutManager);

        swipeRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.figures_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(() -> {
            swipeRefreshLayout.setRefreshing(true);
            loadCollectionFromBeginning();
        });
        loadCollectionFromBeginning();
    }

    private void loadCollectionFromBeginning() {
        mPage = 1;
        resetCollection();
        loadCollection(mPage);
    }

    protected abstract void loadCollection(int page);

    protected abstract void resetCollection();

    protected abstract RecyclerView.Adapter createAdapter();

    protected abstract void onFragmentInteraction(View view, DetailedFigure detailedFigure);

    protected void showHintMessage(String hintMessage) {
        textViewErrorTitle.setVisibility(View.GONE);
        textViewErrorMessage.setText(hintMessage);
        setNotLoadingState();
        setViewState(MESSAGING);
    }

    protected void showData() {
        setNotLoadingState();
        setViewState(SUCCESS);
    }

    protected void showError(String title) {
        showError(title, "");
    }

    protected void showError(String title, String message) {

        if (mRecyclerView.getAdapter().getItemCount() == 0) {
            textViewErrorTitle.setOnClickListener(v -> {
                setViewState(LOADING);
                loadCollectionFromBeginning();
            });
            textViewErrorTitle.setText(title);
            textViewErrorMessage.setText(message);
            setViewState(MESSAGING);
        }
        setNotLoadingState();
    }

    private void setNotLoadingState() {
        loading = false;
    }

    protected void setViewState(@ViewState int viewState) {
        swipeRefreshLayout.setRefreshing(false);
        viewFlipper.setDisplayedChild(viewState);
    }

}
