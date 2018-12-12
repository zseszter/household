package com.zsubori.household.activities;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.design.widget.BottomNavigationView;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.ActionBar;
import android.view.View;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;

import com.zsubori.household.R;
import com.zsubori.household.fragment.ChatFragment;
import com.zsubori.household.fragment.DashboardFragment;
import com.zsubori.household.fragment.EventFragment;

public class MainActivity extends AppCompatActivity
        //implements NavigationView.OnNavigationItemSelectedListener
        implements BottomNavigationView.OnNavigationItemReselectedListener {

    ActionBar actionbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.app_bar_posts);

        final Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Home");
        toolbar.setBackgroundColor(getResources().getColor(R.color.homeColor));

        actionbar = getSupportActionBar();
        BottomNavigationView navigation = (BottomNavigationView) findViewById(R.id.navigation);
        navigation.setSelectedItemId(R.id.nav_dashboard);
        navigation.setOnNavigationItemReselectedListener(this);

        navigation.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                int id = item.getItemId();

                if (id == R.id.nav_dashboard) {
                    toolbar.setTitle("Home");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.homeColor));
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DashboardFragment()).commit();
                } else if (id == R.id.nav_todos) {
                    toolbar.setTitle("Chores");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.todoColor));
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TodoFragment()).commit();
                } else if (id == R.id.nav_events) {
                    toolbar.setTitle("Events");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.eventColor));
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EventFragment()).commit();
                } else if (id == R.id.nav_lunch) {
                    toolbar.setTitle("Menus");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.lunchColor));
                } else if (id == R.id.nav_chat) {
                    toolbar.setTitle("Chat");
                    toolbar.setBackgroundColor(getResources().getColor(R.color.postColor));
                    getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new ChatFragment()).commit();
                }
                return true;
            }
        });

        getFragmentManager().beginTransaction().add(R.id.fragmentContainer, new DashboardFragment()).commit();
    }

/*
    @Override
    public void onBackPressed() {
        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
*/

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.posts, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

/*    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DashboardFragment()).commit();
        } else if (id == R.id.nav_todos) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TodoFragment()).commit();

        } else if (id == R.id.nav_events) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EventFragment()).commit();

        } else if (id == R.id.nav_lunch) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }

        DrawerLayout drawer = (DrawerLayout) findViewById(R.id.drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }*/

    @Override
    public void onNavigationItemReselected(@NonNull MenuItem item) {
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new DashboardFragment()).commit();
        } else if (id == R.id.nav_todos) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new TodoFragment()).commit();

        } else if (id == R.id.nav_events) {
            getFragmentManager().beginTransaction().replace(R.id.fragmentContainer, new EventFragment()).commit();

        } else if (id == R.id.nav_lunch) {

        }
    }
}
