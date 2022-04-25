package com.example.agrocare;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.MenuItem;
import com.example.agrocare.ui.HelpFragment;
import com.example.agrocare.ui.WeatherFragment;
import com.example.agrocare.ui.area.MapsFragment;
import com.example.agrocare.ui.detection.DiseaseDetectionFragment;
import com.example.agrocare.ui.home.HomeFragment;
import com.example.agrocare.ui.profile.ProfileFragment;
import com.google.android.material.navigation.NavigationView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.appcompat.app.AppCompatActivity;

import java.util.Objects;

public class MainActivity extends AppCompatActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private Toolbar toolbar;
    FragmentManager fragmentManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        setToolbar();
        initViews(savedInstanceState);
        initComponentsNavHeader();
    }

    private void setToolbar() {
        toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        Objects.requireNonNull(getSupportActionBar()).setTitle(0);
    }

    @SuppressLint("NonConstantResourceId")
    private void initViews(Bundle savedInstanceState) {
        /**
         * Menu Bottom Navigation Drawer
         * */
//        animatedBottomBar = findViewById(R.id.navigation);

        if (savedInstanceState == null) {
//            animatedBottomBar.selectTabById(R.id.nav_menu_home, true);
            fragmentManager = getSupportFragmentManager();
            HomeFragment homeFragment = new HomeFragment();
            fragmentManager.beginTransaction().replace(R.id.fragment_container, homeFragment)
                    .commit();
        }

//        animatedBottomBar.setOnTabSelectListener((lastIndex, lastTab, newIndex, newTab) -> {
//            Fragment fragment = null;
//            switch (newTab.getId()) {
//                case R.id.nav_menu_home:
//                    fragment = new HomeFragment();
//                    break;
//                case R.id.nav_menu_wishlist:
//                    fragment = new FavoriteFragment();
//                    break;
//                case R.id.nav_menu_signin:
//                    fragment = new ProfileFragment();
//                    break;
//            }
//
//            if (fragment != null) {
//                fragmentManager = getSupportFragmentManager();
//                fragmentManager.beginTransaction().replace(R.id.fragment_container, fragment)
//                        .commit();
//            } else {
//                Log.e(TAG, "Error in creating Fragment");
//            }
//        });

        /**
         * Menu Navigation Drawer
         **/
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.setDrawerIndicatorEnabled(false);
        toggle.setToolbarNavigationClickListener(view -> drawer.openDrawer(GravityCompat.START));
        toggle.setHomeAsUpIndicator(R.drawable.ic_drawer);
        toggle.syncState();
    }

    private void initComponentsNavHeader(){
        NavigationView navigationView = findViewById(R.id.nav_view);

        fragmentManager = getSupportFragmentManager();
//        navigationView.setItemIconTintList(null); //disable tint on each icon to use color icon svg
        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @SuppressLint("NonConstantResourceId")
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.nav_home:
                        Fragment newFragment = new HomeFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                    case R.id.nav_diseases:
                        newFragment = new DiseaseDetectionFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                    case R.id.nav_weather:
                        newFragment = new WeatherFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                    case R.id.nav_area_calc:
                        newFragment = new MapsFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                    case R.id.nav_profile:
                        newFragment = new ProfileFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                    case R.id.nav_help:
                        newFragment = new HelpFragment();
                        fragmentManager.beginTransaction().replace(R.id.fragment_container, newFragment).addToBackStack(null).commit();
                        break;
                }

                DrawerLayout drawer = findViewById(R.id.drawer_layout);
                drawer.closeDrawer(GravityCompat.START);
                return true;
            }

        });
    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer = findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}