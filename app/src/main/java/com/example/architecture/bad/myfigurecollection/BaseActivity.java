package com.example.architecture.bad.myfigurecollection;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ant_robot.mfc.api.pojo.UserProfile;
import com.ant_robot.mfc.api.request.MFCRequest;
import com.example.architecture.bad.myfigurecollection.data.DetailedFigure;
import com.example.architecture.bad.myfigurecollection.figures.FiguresActivity;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresOrderedFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresOwnedFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresWishedFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils.FragmentType;
import com.example.architecture.bad.myfigurecollection.util.CodeUtils;
import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import org.w3c.dom.Text;

import java.util.ArrayList;
import java.util.List;

import retrofit.Callback;
import retrofit.RetrofitError;
import retrofit.client.Response;

public abstract class BaseActivity extends AppCompatActivity
        implements FiguresFragment.OnFragmentInteractionListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private ViewPager viewPager;

    int[] tabSelectedIcons = {
            R.drawable.ic_owned_full_24px, R.drawable.ic_ordered_full_24px,
            R.drawable.ic_wished_full_24px,
    };
    int[] tabUnselectedIcons = {
            R.drawable.ic_owned_empty_24px, R.drawable.ic_ordered_empty_24px,
            R.drawable.ic_wished_empty_24px
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_figures);

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

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

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

                            Context context = BaseActivity.this;
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

        TabLayout tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                tab.setIcon(tabSelectedIcons[tab.getPosition()]);
                viewPager.setCurrentItem(tab.getPosition());
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {
                tab.setIcon(tabUnselectedIcons[tab.getPosition()]);
            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        tabLayout.getTabAt(0).setIcon(tabSelectedIcons[0]);
        tabLayout.getTabAt(1).setIcon(tabUnselectedIcons[1]);
        tabLayout.getTabAt(2).setIcon(tabUnselectedIcons[2]);
    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FiguresOwnedFragment.newInstance());
        adapter.addFragment(FiguresOrderedFragment.newInstance());
        adapter.addFragment(FiguresWishedFragment.newInstance());
        viewPager.setAdapter(adapter);
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

    protected abstract void setFragment(int contentFrameId);

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
                                ActivityUtils.startActivityWithNewTask(BaseActivity.this, FiguresActivity.class);
                                break;
                            case R.id.pod_navigation_menu_item:
                                Log.d(TAG, "POD menu tapped!");
                                //ActivityUtils.startActivityWithNewTask(BaseActivity.this, BestPicturesActivity.class);
                                break;
                            case R.id.pow_navigation_menu_item:
                                Log.d(TAG, "POW menu tapped!");
                                //ActivityUtils.startActivityWithNewTask(BaseActivity.this, BestPicturesActivity.class);
                                break;
                            case R.id.pom_navigation_menu_item:
                                Log.d(TAG, "POM menu tapped!");
                                //ActivityUtils.startActivityWithNewTask(BaseActivity.this, BestPicturesActivity.class);
                                break;
                            case R.id.twitter_navigation_menu_item:
                                Log.d(TAG, "Twitter menu tapped!");
                                ActivityUtils.startActivityTwitter(BaseActivity.this);
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

    private class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();

        ViewPagerAdapter(FragmentManager manager) {
            super(manager);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragmentList.get(position);
        }

        @Override
        public int getCount() {
            return mFragmentList.size();
        }

        void addFragment(Fragment fragment) {
            mFragmentList.add(fragment);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return null to display only the icon
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(View view, DetailedFigure figureItem,
                                      @FragmentType int fragmentType) {

        Log.d(TAG, "Figure Item: " + figureItem.toString());

        ActivityUtils.startItemFigureDetailActivity(this, figureItem, view, fragmentType);
    }
}
