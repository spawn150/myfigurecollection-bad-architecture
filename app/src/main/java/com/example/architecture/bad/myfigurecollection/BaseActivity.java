package com.example.architecture.bad.myfigurecollection;

import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.GravityCompat;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;

import com.ant_robot.mfc.api.pojo.Category;
import com.ant_robot.mfc.api.pojo.Data;
import com.ant_robot.mfc.api.pojo.Item;
import com.ant_robot.mfc.api.pojo.Mycollection;
import com.example.architecture.bad.myfigurecollection.data.ItemFigureDetail;
import com.example.architecture.bad.myfigurecollection.figures.FiguresActivity;
import com.example.architecture.bad.myfigurecollection.figures.FiguresFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresOrderedFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresOwnedFragment;
import com.example.architecture.bad.myfigurecollection.figures.FiguresWishedFragment;
import com.example.architecture.bad.myfigurecollection.util.ActivityUtils;

import java.util.ArrayList;
import java.util.List;

public abstract class BaseActivity extends AppCompatActivity implements FiguresFragment.OnFragmentInteractionListener {

    private static final String TAG = BaseActivity.class.getSimpleName();
    private DrawerLayout mDrawerLayout;
    private TabLayout tabLayout;
    private ViewPager viewPager;

    int[] tabSelectedIcons = {R.drawable.ic_owned_full_24px, R.drawable.ic_wished_full_24px, R.drawable.ic_ordered_full_24px};
    int[] tabUnselectedIcons = {R.drawable.ic_owned_empty_24px, R.drawable.ic_wished_empty_24px, R.drawable.ic_ordered_empty_24px};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_figures);

        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        ActionBar ab = getSupportActionBar();
        ab.setHomeAsUpIndicator(R.drawable.ic_menu);
        ab.setDisplayHomeAsUpEnabled(true);

        // Set up the navigation drawer.
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        mDrawerLayout.setStatusBarBackground(R.color.colorPrimaryDark);
        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        if (navigationView != null) {
            setupDrawerContent(navigationView);
        }

        viewPager = (ViewPager) findViewById(R.id.viewpager);
        setupViewPager(viewPager);

        tabLayout = (TabLayout) findViewById(R.id.tabs);
        tabLayout.setupWithViewPager(viewPager);

        tabLayout.setOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
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

        tabLayout.getTabAt(0).setIcon(R.drawable.ic_owned_full_24px);
        tabLayout.getTabAt(1).setIcon(R.drawable.ic_wished_empty_24px);
        tabLayout.getTabAt(2).setIcon(R.drawable.ic_ordered_empty_24px);

    }

    private void setupViewPager(ViewPager viewPager) {
        ViewPagerAdapter adapter = new ViewPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(FiguresOwnedFragment.newInstance(), "Owned");
        adapter.addFragment(FiguresWishedFragment.newInstance(), "Wished");
        adapter.addFragment(FiguresOrderedFragment.newInstance(), "Ordered");
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
                    public boolean onNavigationItemSelected(MenuItem menuItem) {
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

    class ViewPagerAdapter extends FragmentPagerAdapter {
        private final List<Fragment> mFragmentList = new ArrayList<>();
        private final List<String> mFragmentTitleList = new ArrayList<>();

        public ViewPagerAdapter(FragmentManager manager) {
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

        public void addFragment(Fragment fragment, String title) {
            mFragmentList.add(fragment);
            mFragmentTitleList.add(title);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            // return null to display only the icon
            return null;
        }
    }

    @Override
    public void onFragmentInteraction(Item figureItem) {

        Log.d(TAG, "Figure Item: " + figureItem.toString());

        Data data = figureItem.getData();
        Category category = figureItem.getCategory();
        Mycollection mycollection = figureItem.getMycollection();

        ActivityUtils.startItemFigureDetailActivity(BaseActivity.this, new ItemFigureDetail(
                data.getId(),
                data.getName(),
                data.getReleaseDate() != null ? data.getReleaseDate().toString() : "",
                mycollection.getScore(),
                mycollection.getNumber(),
                category.getName()));
    }

}
