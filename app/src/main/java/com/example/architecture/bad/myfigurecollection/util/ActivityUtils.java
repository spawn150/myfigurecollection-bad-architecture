/*
 * Copyright 2016, The Android Open Source Project
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */

package com.example.architecture.bad.myfigurecollection.util;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.support.annotation.IntDef;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;

import android.view.View;

import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figuredetail.FigureDetailActivity;
import com.example.architecture.bad.myfigurecollection.figuregallery.FigureGalleryActivity;

import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;

/**
 * This provides methods to help Activities load their UI.
 */
public class ActivityUtils {

    public static final String ARG_FIGURE_DETAIL = "figuredetailargument";
    public static final String ARG_FRAGMENT_TYPE = "fragmenttype";
    public static final String ARG_FIGURE_ID = "figureid";

    public static final int OWNED_FRAGMENT = 0;
    public static final int WISHED_FRAGMENT = 1;
    public static final int ORDERED_FRAGMENT = 2;
    public static final int BEST_PICTURES = 3;

    @IntDef({OWNED_FRAGMENT, WISHED_FRAGMENT, ORDERED_FRAGMENT, BEST_PICTURES})
    @Retention(RetentionPolicy.SOURCE)
    public @interface FragmentType {
    }

    /**
     * The {@code fragment} is added to the container view with id {@code frameId}. The operation is
     * performed by the {@code fragmentManager}.
     */
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId) {
        addFragmentToActivity(fragmentManager, fragment, frameId, null);
    }
    public static void addFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.add(frameId, fragment, tag);
        transaction.commit();
    }

    public static void replaceFragmentToActivity(@NonNull FragmentManager fragmentManager,
                                             @NonNull Fragment fragment, int frameId, String tag) {
        FragmentTransaction transaction = fragmentManager.beginTransaction();
        transaction.replace(frameId, fragment, tag);
        transaction.commit();
    }

    public static void startActivityWithNewTask(@NonNull Context context, @NonNull Class className) {
        Intent intent = new Intent(context, className);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK
                | Intent.FLAG_ACTIVITY_CLEAR_TASK);
        context.startActivity(intent);
    }

    public static void startActivityInSameTask(@NonNull Context context, @NonNull Class className) {
        Intent intent = new Intent(context, className);
        context.startActivity(intent);
    }

    public static void startItemFigureDetailActivity(@NonNull Activity activity, DetailedFigure detailedFigure, View view, @FragmentType int fragmentType) {
        Intent intent = new Intent(activity, FigureDetailActivity.class);
        intent.putExtra(ARG_FIGURE_DETAIL, detailedFigure);
        intent.putExtra(ARG_FRAGMENT_TYPE, fragmentType);

        ActivityOptionsCompat options = ActivityOptionsCompat.
                makeSceneTransitionAnimation(activity, view, "detail");
        activity.startActivity(intent, options.toBundle());
    }

    public static void startItemFigureGalleryActivity(@NonNull Context context, @NonNull String figureId) {
        Intent intent = new Intent(context, FigureGalleryActivity.class);
        intent.putExtra(ARG_FIGURE_ID, figureId);
        context.startActivity(intent);
    }

}
