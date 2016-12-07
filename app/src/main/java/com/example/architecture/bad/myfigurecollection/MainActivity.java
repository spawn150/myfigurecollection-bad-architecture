package com.example.architecture.bad.myfigurecollection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.ViewFlipper;

import com.ant_robot.mfc.api.pojo.UserProfile;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.bestpictures.PictureOfTheDayFragment;
import com.example.architecture.bad.myfigurecollection.bestpictures.PictureOfTheMonthFragment;
import com.example.architecture.bad.myfigurecollection.bestpictures.PictureOfTheWeekFragment;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figures.FiguresContainerFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.timelinetwitter.EmbeddedTwitterFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;
import com.squareup.picasso.Picasso;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public class MainActivity extends AppCompatActivity
        implements FiguresFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setTheme(R.style.AppTheme);

        setContentView(R.layout.activity_main);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        if (ab != null) {
            ab.setHomeAsUpIndicator(R.drawable.ic_menu);
            ab.setDisplayHomeAsUpEnabled(true);
        }

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.primary_dark);
        final NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        setMyCollectionFragment();

        //TODO Refactor this code when implemented login view
        MFCRequest.INSTANCE.connect("spawn150", "pul78lce", this, new Callback<Boolean>() {
            @Override
            public void success(Boolean aBoolean, Response response) {
                Log.d(TAG, "Login successfully!");
                MFCRequest.INSTANCE.getUserService().getUser("spawn150", new Callback<UserProfile>() {
                    @Override
                    public void success(UserProfile userProfile, Response response) {
                        Log.d(TAG, "Login Data [Name]: " + userProfile.getUser().getName());
                        Log.d(TAG, "Login Data [Pic ]: " + userProfile.getUser().getPicture());

                        if (navigationView != null) {
                            final ImageView imageViewAvatar = (ImageView) navigationView.findViewById(R.id.image_view_avatar);
                            TextView textViewUsername = (TextView) navigationView.findViewById(R.id.text_view_username);
                            textViewUsername.setText(userProfile.getUser().getName());

                            SessionHelper.createSession(MainActivity.this, userProfile.getUser());

                            Context context = MainActivity.this;
                            Picasso.with(context)
                                    .load(context.getString(R.string.avatar_large_image_url, userProfile.getUser().getPicture()))
                                    .resize(360, 360) //TODO create converter from dp to px in CodeUtils
                                    .into(imageViewAvatar, new com.squareup.picasso.Callback() {
                                        @Override
                                        public void onSuccess() {
                                            Bitmap bitmap = ((BitmapDrawable) imageViewAvatar.getDrawable()).getBitmap();
                                            RoundedBitmapDrawable imageDrawable = RoundedBitmapDrawableFactory.create(getResources(), bitmap);
                                            imageDrawable.setCircular(true);
                                            imageDrawable.setCornerRadius(Math.max(bitmap.getWidth(), bitmap.getHeight()) / 2.0f);
                                            imageViewAvatar.setImageDrawable(imageDrawable);
                                        }

                                        @Override
                                        public void onError() {
                                            imageViewAvatar.setImageResource(R.drawable.logo);
                                        }
                                    });
                        }

                    }

                    @Override
                    public void failure(RetrofitError error) {

                    }
                });
            }

            @Override
            public void failure(RetrofitError error) {
                Log.e(TAG, "Login error!");
            }
        });

    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                // Open the navigation drawer when the home icon is selected from the toolbar.
                mDrawerLayout.openDrawer(GravityCompat.START);
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.settings_navigation_menu_item:
                                Log.d(TAG, "Settings menu tapped!");
                                break;
                            case R.id.mfc_navigation_menu_item:
                                Log.d(TAG, "MFC menu tapped!");
                                setMyCollectionFragment();
                                break;
                            case R.id.pod_navigation_menu_item:
                                Log.d(TAG, "POD menu tapped!");
                                setPODFragment();
                                break;
                            case R.id.pow_navigation_menu_item:
                                Log.d(TAG, "POW menu tapped!");
                                setPOWFragment();
                                break;
                            case R.id.pom_navigation_menu_item:
                                Log.d(TAG, "POM menu tapped!");
                                setPOMFragment();
                                break;
                            case R.id.twitter_navigation_menu_item:
                                Log.d(TAG, "Twitter menu tapped!");
                                setTwitterFragment();
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(true);
                        mDrawerLayout.closeDrawers();
                        return true;
                    }
                });
    }

    private void setMyCollectionFragment() {
        FiguresContainerFragment figuresContainerFragment = (FiguresContainerFragment) getSupportFragmentManager().findFragmentByTag(FiguresContainerFragment.TAG);
        if (figuresContainerFragment == null) {
            // Create the fragment
            figuresContainerFragment = FiguresContainerFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), figuresContainerFragment, R.id.figures_container, FiguresContainerFragment.TAG);
        }
    }

    private void setPODFragment() {
        PictureOfTheDayFragment pictureOfTheDayFragment = (PictureOfTheDayFragment) getSupportFragmentManager().findFragmentByTag(PictureOfTheDayFragment.TAG);
        if (pictureOfTheDayFragment == null) {
            // Create the fragment
            pictureOfTheDayFragment = PictureOfTheDayFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), pictureOfTheDayFragment, R.id.figures_container, PictureOfTheDayFragment.TAG);
        }
    }

    private void setPOWFragment() {
        PictureOfTheWeekFragment pictureOfTheWeekFragment = (PictureOfTheWeekFragment) getSupportFragmentManager().findFragmentByTag(PictureOfTheWeekFragment.TAG);
        if (pictureOfTheWeekFragment == null) {
            // Create the fragment
            pictureOfTheWeekFragment = PictureOfTheWeekFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), pictureOfTheWeekFragment, R.id.figures_container, PictureOfTheWeekFragment.TAG);
        }
    }

    private void setPOMFragment() {
        PictureOfTheMonthFragment pictureOfTheMonthFragment = (PictureOfTheMonthFragment) getSupportFragmentManager().findFragmentByTag(PictureOfTheMonthFragment.TAG);
        if (pictureOfTheMonthFragment == null) {
            // Create the fragment
            pictureOfTheMonthFragment = PictureOfTheMonthFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), pictureOfTheMonthFragment, R.id.figures_container, PictureOfTheMonthFragment.TAG);
        }
    }

    private void setTwitterFragment() {
        EmbeddedTwitterFragment embeddedTwitterFragment = (EmbeddedTwitterFragment) getSupportFragmentManager().findFragmentByTag(EmbeddedTwitterFragment.TAG);
        if (embeddedTwitterFragment == null) {
            // Create the fragment
            embeddedTwitterFragment = EmbeddedTwitterFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), embeddedTwitterFragment, R.id.figures_container, EmbeddedTwitterFragment.TAG);
        }
    }

    @Override
    public void onFragmentInteraction(View view, DetailedFigure figureItem,
                                      @FragmentType int fragmentType) {
        Log.d(TAG, "Figure Item: " + figureItem.toString());
        ActivityUtils.startItemFigureDetailActivity(this, figureItem, view, fragmentType);
    }
}
