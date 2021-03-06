package com.example.architecture.bad.myfigurecollection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.os.Bundle;
import android.os.PersistableBundle;
import android.preference.PreferenceManager;
import android.support.annotation.IdRes;
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
import android.widget.Toast;

import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.data.figures.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.data.user.SessionUser;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.BestPicturesFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.LatestPicturesFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheDayFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheMonthFragment;
import com.example.architecture.bad.myfigurecollection.figures.bestpictures.PictureOfTheWeekFragment;
import com.example.architecture.bad.myfigurecollection.figures.collection.CollectionFiguresFragment;
import com.example.architecture.bad.myfigurecollection.figures.collection.FiguresContainerFragment;
import com.example.architecture.bad.myfigurecollection.login.LoginActivity;
import com.example.architecture.bad.myfigurecollection.settings.SettingsActivity;
import com.example.architecture.bad.myfigurecollection.timelinetwitter.TwitterTimelineActivity;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.example.architecture.bad.myfigurecollection.util.SessionHelper;
import com.squareup.picasso.Picasso;

public class MainActivity extends AppCompatActivity
        implements CollectionFiguresFragment.OnCollectionFiguresFragmentInteractionListener,
        BestPicturesFragment.OnBestPicturesFragmentInteractionListener {

    private static final String TAG = MainActivity.class.getSimpleName();
    public static final String TITLE_KEY = "TITLE_KEY";
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

        setupUserProfileData(savedInstanceState);
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

    private void setupUserProfileData(Bundle savedInstanceState) {
        if (navigationView != null) {

            //fix bug on library using app:headerLayout="@layout/nav_header" - ref link: http://stackoverflow.com/questions/33199764/android-api-23-change-navigation-view-headerlayout-textview
            View header = LayoutInflater.from(this).inflate(R.layout.nav_header, null);
            navigationView.addHeaderView(header);

            Context context = MainActivity.this;

            if (SessionHelper.isAuthenticated(this)) {

                setupDrawerForUser();
                setHeaderInfo(header, context);

                if(savedInstanceState == null) {
                    setMyCollectionFragment();
                }

            } else {
                setupDrawerForGuest();
                if(savedInstanceState == null) {
                    setPODFragment();
                }
            }

        }
    }

    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);

        if(savedInstanceState != null){
            setTitle(savedInstanceState.getString(TITLE_KEY));
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);

        outState.putString(TITLE_KEY, getTitle().toString());
    }

    private void setHeaderInfo(View header, Context context) {
        SessionUser sessionUser = SessionHelper.getUserData(context);
        final ImageView imageViewAvatar = (ImageView) header.findViewById(R.id.image_view_avatar);
        TextView textViewUsername = (TextView) header.findViewById(R.id.text_view_username);
        textViewUsername.setText(sessionUser.getName());

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
                        imageViewAvatar.setImageResource(R.drawable.ic_tsuko_bn);
                    }
                });
    }

    private void setupDrawerContent(NavigationView navigationView) {
        navigationView.setNavigationItemSelectedListener(
                menuItem -> {
                    setFragmentByMenuItem(menuItem.getItemId());
                    // Close the navigation drawer when an item is selected.
                    mDrawerLayout.closeDrawers();
                    return true;
                });
    }

    private void setFragmentByMenuItem(@IdRes int menuItemId) {
        switch (menuItemId) {
            case R.id.mfc_navigation_menu_item:
                setMyCollectionFragment();
                break;
            case R.id.potd_navigation_menu_item:
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
                openTwitterTimeline();
                break;
            case R.id.settings_navigation_menu_item:
                Log.d(TAG, "Settings menu tapped!");
                openSettings();
                break;
            case R.id.login_menu_item:
                Log.d(TAG, "Settings menu tapped!");
                openLogin();
                break;
            case R.id.logout_menu_item:
                Log.d(TAG, "Settings menu tapped!");
                logout();
                break;
            default:
                break;
        }
        navigationView.setCheckedItem(menuItemId);
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

    private void setupDrawerForGuest() {
        navigationView.getMenu().findItem(R.id.mfc_navigation_menu_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.login_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.logout_menu_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.potd_navigation_menu_item).setChecked(true);
    }

    private void setupDrawerForUser() {
        navigationView.getMenu().findItem(R.id.mfc_navigation_menu_item).setVisible(true);
        navigationView.getMenu().findItem(R.id.login_menu_item).setVisible(false);
        navigationView.getMenu().findItem(R.id.logout_menu_item).setVisible(true);
    }

    private void openTwitterTimeline() {
        ActivityUtils.startActivityInSameTask(this, TwitterTimelineActivity.class);
    }

    private void openSettings() {
        ActivityUtils.startActivityInSameTask(this, SettingsActivity.class);
    }

    private void openLogin() {
        ActivityUtils.startActivityInSameTask(MainActivity.this, LoginActivity.class);
    }

    private void logout() {
        MFCRequest.getInstance().disconnect(MainActivity.this, new MFCRequest.MFCCallback<Boolean>() {
            @Override
            public void success(Boolean aBoolean) {
                SessionHelper.removeSession(MainActivity.this);
                ActivityUtils.startActivityWithNewTask(MainActivity.this, MainActivity.class);
            }

            @Override
            public void error(Throwable throwable) {
                Toast.makeText(MainActivity.this, "Error on logout: " + throwable.getLocalizedMessage(), Toast.LENGTH_SHORT).show();
            }
        });
    }

    @Override
    public void onBestPicturesFragmentInteraction(View view, DetailedFigure figureItem, @FragmentType int fragmentType) {
        Log.d(TAG, "Figure Item: " + figureItem.toString());
        ActivityUtils.startItemBestPictureDetailActivity(this, figureItem, view, fragmentType);
    }

    @Override
    public void onCollectionFiguresFragmentInteraction(View view, DetailedFigure figureItem, @FragmentType int fragmentType) {
        Log.d(TAG, "Figure Item: " + figureItem.toString());
        ActivityUtils.startItemCollectionFigureDetailActivity(this, figureItem, view, fragmentType);
    }
}
