package com.example.architecture.bad.myfigurecollection;

import android.app.Application;

import com.squareup.picasso.OkHttpDownloader;
import com.squareup.picasso.Picasso;
import com.twitter.sdk.android.Twitter;
import com.twitter.sdk.android.core.TwitterAuthConfig;

import io.fabric.sdk.android.Fabric;

/**
 * Created by spawn on 04/07/16.
 */
public class App extends Application {
    // Note: Your consumer key and secret should be obfuscated in your source code before shipping.
    private static final String TWITTER_KEY = "0mewf9HNcqyUf45laXwVKbXvS";
    private static final String TWITTER_SECRET = "gjp1efw8FE8nYbTCopFf7fRpZpIaxrsoqrC8diwq7ElamUwBkC";

    @Override
    public void onCreate() {
        super.onCreate();

        TwitterAuthConfig authConfig = new TwitterAuthConfig(TWITTER_KEY, TWITTER_SECRET);
        Fabric.with(this, new Twitter(authConfig));

        //Initialize Picasso library
        Picasso.Builder builder = new Picasso.Builder(this);
        builder.downloader(new OkHttpDownloader(this, Integer.MAX_VALUE));
        Picasso built = builder.build();
        //built.setIndicatorsEnabled(BuildConfig.DEBUG);
        built.setLoggingEnabled(BuildConfig.DEBUG);
        Picasso.setSingletonInstance(built);
    }
}
