package com.example.architecture.bad.myfigurecollection.timelinetwitter;

import android.app.ActionBar;
import android.app.ListActivity;
import android.os.Bundle;
import android.support.v4.widget.SwipeRefreshLayout;
import android.widget.Toast;
import android.widget.Toolbar;

import com.example.architecture.bad.myfigurecollection.R;
import com.twitter.sdk.android.core.Callback;
import com.twitter.sdk.android.core.Result;
import com.twitter.sdk.android.core.TwitterException;
import com.twitter.sdk.android.core.models.Tweet;
import com.twitter.sdk.android.tweetui.TimelineResult;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

public class TwitterTimelineActivity extends ListActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_twitter_timeline);

        setTitle(getString(R.string.menu_title_twitter));

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setActionBar(toolbar);

        ActionBar ab = getActionBar();
        if (ab != null) {
            ab.setDisplayHomeAsUpEnabled(true);
        }

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(getString(R.string.mfc_twitter_screename))
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(this)
                .setTimeline(userTimeline)
                .build();
        getListView().setEmptyView(findViewById(android.R.id.empty));
        setListAdapter(adapter);

        final SwipeRefreshLayout swipeRefreshLayout = (SwipeRefreshLayout) findViewById(R.id.twitter_swipe_layout);
        swipeRefreshLayout.setColorSchemeResources(R.color.accent);
        swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                swipeRefreshLayout.setRefreshing(true);
                adapter.refresh(new Callback<TimelineResult<Tweet>>() {
                    @Override
                    public void success(Result<TimelineResult<Tweet>> result) {
                        swipeRefreshLayout.setRefreshing(false);
                    }

                    @Override
                    public void failure(TwitterException exception) {
                        swipeRefreshLayout.setRefreshing(false);
                        Toast.makeText(TwitterTimelineActivity.this, "Error loading tweets...", Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

    @Override
    public boolean onNavigateUp() {
        finishAfterTransition();
        return true;
    }

}
