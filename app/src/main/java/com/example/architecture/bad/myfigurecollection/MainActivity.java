package com.example.architecture.bad.myfigurecollection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.preference.PreferenceManager;
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
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.UserProfile;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.data.SessionUser;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.LatestPicturesFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheDayFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheMonthFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheWeekFragment;
import com.example.architecture.bad.myfigurecollection.figures.collection.CollectionFiguresFragment;
import com.example.architecture.bad.myfigurecollection.figures.collection.FiguresContainerFragment;
import com.example.architecture.bad.myfigurecollection.settings.SettingsActivity;
import com.example.architecture.bad.myfigurecollection.timelinetwitter.EmbeddedTwitterFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;
import com.squareup.picasso.Picasso;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class MainActivity extends AppCompatActivity
        implements CollectionFiguresFragment.OnFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private NavigationView navigationView;

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
        navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        //set default value for settings (just once)
        PreferenceManager.setDefaultValues(this, R.xml.settings, false);

        if (!SessionHelper.isAuthenticated(this)) {
            login();
        } else {
            setupUserProfileData();
        }
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

    private void login() {
        //TODO Remove hardcode user/pwd
        MFCRequest.getInstance().connect("spawn150", "pul78lce", this, new MFCRequest.MFCCallback<Boolean>() {
            @Override
            public void success(Boolean aBoolean) {
                loadUserProfile();
            }

            @Override
            public void error(Throwable throwable) {
                Log.e(TAG, "Error in connect()", throwable);
            }
        });
    }

    private void loadUserProfile() {
        //TODO Remove hardcode user
        Call<UserProfile> call = MFCRequest.getInstance().getUserService().getUser("spawn150");
        call.enqueue(new Callback<UserProfile>() {
            @Override
            public void onResponse(Call<UserProfile> call, Response<UserProfile> response) {
                UserProfile userProfile = response.body();
                Log.d(TAG, "Login Data [Name]: " + userProfile.getUser().getName());
                Log.d(TAG, "Login Data [Pic ]: " + userProfile.getUser().getPicture());

                SessionHelper.createSession(MainActivity.this, userProfile.getUser());
                setupUserProfileData();
            }

            @Override
            public void onFailure(Call<UserProfile> call, Throwable t) {
                Log.e(TAG, "Error in getUser()", t);
            }
        });
    }

    private void setupUserProfileData() {
        if (navigationView != null) {

            //fix bug on library using app:headerLayout="@layout/nav_header" - ref link: http://stackoverflow.com/questions/33199764/android-api-23-change-navigation-view-headerlayout-textview
            View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
            navigationView.addHeaderView(header);

            Context context = MainActivity.this;
            SessionUser sessionUser = SessionHelper.getUserData(context);

            final ImageView imageViewAvatar = (ImageView) header.findViewById(R.id.image_view_avatar);
            TextView textViewUsername = (TextView) header.findViewById(R.id.text_view_username);
            textViewUsername.setText(sessionUser.getName());
            setMyCollectionFragment();

            int imageSize = (int) CodeUtils.convertDpToPixel(120, context);

            Picasso.with(context)
                    .load(context.getString(R.string.avatar_large_image_url, sessionUser.getPicture()))
                    .resize(imageSize, imageSize)
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
                            imageViewAvatar.setImageResource(R.drawable.ic_tsuko_color);
                        }
                    });

        }
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                new NavigationView.OnNavigationItemSelectedListener() {
                    @Override
                    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
                        switch (menuItem.getItemId()) {
                            case R.id.mfc_navigation_menu_item:
                                Log.d(TAG, "MFC menu tapped!");
                                setMyCollectionFragment();
                                break;
                            case R.id.pod_navigation_menu_item:
                                Log.d(TAG, "POD menu tapped!");
                                setPODFragment();
                                break;
                            case R.id.latest_pictures_navigation_menu_item:
                                setLatestPicturesFragment();
                                break;
                            /*
                            case R.id.pow_navigation_menu_item:
                                Log.d(TAG, "POW menu tapped!");
                                setPOWFragment();
                                break;
                            case R.id.pom_navigation_menu_item:
                                Log.d(TAG, "POM menu tapped!");
                                setPOMFragment();
                                break;
                            */
                            case R.id.twitter_navigation_menu_item:
                                Log.d(TAG, "Twitter menu tapped!");
                                setTwitterFragment();
                                break;
                            case R.id.settings_navigation_menu_item:
                                Log.d(TAG, "Settings menu tapped!");
                                openSettings();
                                break;
                            case R.id.login_logout_menu_item:
                                Log.d(TAG, "Settings menu tapped!");
                                break;
                            default:
                                break;
                        }
                        // Close the navigation drawer when an item is selected.
                        menuItem.setChecked(menuItem.getItemId() != R.id.settings_navigation_menu_item && menuItem.getItemId() != R.id.login_logout_menu_item);
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
            setTitle(getString(R.string.menu_title_mfc));
        }
    }

    private void setLatestPicturesFragment() {
        LatestPicturesFragment latestPicturesFragment = (LatestPicturesFragment) getSupportFragmentManager().findFragmentByTag(LatestPicturesFragment.TAG);
        if (latestPicturesFragment == null) {
            // Create the fragment
            latestPicturesFragment = LatestPicturesFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), latestPicturesFragment, R.id.figures_container, LatestPicturesFragment.TAG);
            setTitle(getString(R.string.menu_title_latest_pictures));
        }
    }

    private void setPODFragment() {
        PictureOfTheDayFragment pictureOfTheDayFragment = (PictureOfTheDayFragment) getSupportFragmentManager().findFragmentByTag(PictureOfTheDayFragment.TAG);
        if (pictureOfTheDayFragment == null) {
            // Create the fragment
            pictureOfTheDayFragment = PictureOfTheDayFragment.newInstance();
            ActivityUtils.replaceFragmentToActivity(getSupportFragmentManager(), pictureOfTheDayFragment, R.id.figures_container, PictureOfTheDayFragment.TAG);
            setTitle(getString(R.string.menu_title_pod));
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
            setTitle(getString(R.string.menu_title_twitter));
        }
    }

    private void openSettings() {
        ActivityUtils.startActivityInSameTask(this, SettingsActivity.class);
    }

    @Override
    public void onFragmentInteraction(View view, DetailedFigure figureItem,
                                      @FragmentType int fragmentType) {
        Log.d(TAG, "Figure Item: " + figureItem.toString());
        ActivityUtils.startItemFigureDetailActivity(this, figureItem, view, fragmentType);
    }
}
