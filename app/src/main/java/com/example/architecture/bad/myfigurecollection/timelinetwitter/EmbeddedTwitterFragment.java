package com.example.architecture.bad.myfigurecollection.timelinetwitter;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;

import com.example.architecture.bad.myfigurecollection.R;
import com.twitter.sdk.android.tweetui.TweetTimelineListAdapter;
import com.twitter.sdk.android.tweetui.UserTimeline;

/**
 * A placeholder fragment containing a simple view.
 */
public class EmbeddedTwitterFragment extends Fragment {

    public static final String TAG = EmbeddedTwitterFragment.class.getName();

    public EmbeddedTwitterFragment() {
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @return A new instance of fragment EmbeddedTwitterFragment.
     */
    public static EmbeddedTwitterFragment newInstance() {
        return new EmbeddedTwitterFragment();
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_embedded_twitter, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        final UserTimeline userTimeline = new UserTimeline.Builder()
                .screenName(getContext().getString(R.string.mfc_twitter_screename))
                .build();
        final TweetTimelineListAdapter adapter = new TweetTimelineListAdapter.Builder(getContext())
                .setTimeline(userTimeline)
                .build();
        ListView embeddedTwitterTimelineListView = (ListView) view.findViewById(R.id.list_view_twitter_timeline);
        embeddedTwitterTimelineListView.setAdapter(adapter);

    }
}
