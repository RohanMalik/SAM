package com.monkeybusiness.jaaar.Activity;

import android.os.Bundle;
import android.support.design.widget.AppBarLayout;
import android.support.design.widget.CoordinatorLayout;
import android.support.design.widget.NavigationView;
import android.support.design.widget.TabLayout;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.view.PagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.MenuItem;
import android.view.View;
import android.widget.FrameLayout;

import com.monkeybusiness.jaaar.Adapter.AttendancePagerAdapter;
import com.monkeybusiness.jaaar.Fragment.AttendanceFragment;
import com.monkeybusiness.jaaar.Fragment.FriendsActivity;
import com.monkeybusiness.jaaar.Fragment.MyCalendarFragment;
import com.monkeybusiness.jaaar.Fragment.MyClassFragment;
import com.monkeybusiness.jaaar.Fragment.RemarksFragment;
import com.monkeybusiness.jaaar.Fragment.TestListFragment;
import com.monkeybusiness.jaaar.MasterClass;
import com.monkeybusiness.jaaar.R;
import com.monkeybusiness.jaaar.objectClasses.StudentAttdData;

import java.util.ArrayList;
import java.util.Arrays;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by rohanmalik on 28/12/15.
 */
public class LandingPageActivity extends AppCompatActivity {


    @Bind(R.id.dashboard_toolbar)
    Toolbar dashboardToolbar;
    @Bind(R.id.appbar)
    AppBarLayout appbar;
    @Bind(R.id.fragmentContainer)
    FrameLayout fragmentContainer;
    @Bind(R.id.coordinator)
    CoordinatorLayout coordinator;
    @Bind(R.id.navigation_view)
    NavigationView navigationView;
    @Bind(R.id.drawer)

    DrawerLayout drawerLayout;
    public Fragment fragment = null;
    FragmentTransaction fragmentTransaction;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_landing_page);
        ButterKnife.bind(this);

        setupToolBar();
        setupNavDrawer();

        ArrayList<StudentAttdData> studentAttdDatas = new ArrayList<>();

        studentAttdDatas.add(new StudentAttdData("Rohan Malik","10th","1","", Arrays.asList("1", "0", "1", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Jaspreet Arora", "10th", "2", "", Arrays.asList("0", "0", "0", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Ravi Kumar", "10th", "3", "", Arrays.asList("1", "0", "1", "1", "1")));
        studentAttdDatas.add(new StudentAttdData("Salman Khan", "10th", "4", "", Arrays.asList("1", "0", "0", "0", "1")));
        studentAttdDatas.add(new StudentAttdData("Shahrukh Khan", "10th", "5", "", Arrays.asList("1", "0", "0", "0", "0")));

        MasterClass.getInstance().setStudentAttdDatas(studentAttdDatas);

        fragment = new AttendanceFragment();
        fragmentTransaction = getSupportFragmentManager().beginTransaction();
        fragmentTransaction.add(R.id.fragmentContainer, fragment);
        fragmentTransaction.commit();
        getSupportActionBar().setTitle("My Attendance");
    }

    private void setupToolBar() {
        setSupportActionBar(dashboardToolbar);
    }

    private void setupNavDrawer() {

        navigationView.setItemIconTintList(null);

        navigationView.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(MenuItem item) {
                drawerLayout.closeDrawers();

                switch (item.getItemId()) {
                    case R.id.my_attd:
                        fragment = new AttendanceFragment();
                        getSupportActionBar().setTitle("My Attendance");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.my_class:
                        fragment = new FriendsActivity();
                        getSupportActionBar().setTitle("My Class");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;
                    case R.id.my_remarks:
                        fragment = new RemarksFragment();
                        getSupportActionBar().setTitle("My Remarks");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.test:
                        fragment = new TestListFragment();
                        getSupportActionBar().setTitle("My Test");
                        fragmentTransaction = getSupportFragmentManager().beginTransaction();
                        fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                        fragmentTransaction.addToBackStack(null);
                        fragmentTransaction.commit();
                        return true;

                    case R.id.calender:
                                fragment = new MyCalendarFragment();
                                getSupportActionBar().setTitle("My Calendar");
                                fragmentTransaction = getSupportFragmentManager().beginTransaction();
                                fragmentTransaction.replace(R.id.fragmentContainer, fragment);
                                fragmentTransaction.addToBackStack(null);
                                fragmentTransaction.commit();
                        return true;

                    case R.id.notifications:

                        return true;


                    default:
                        return true;
                }
            }
        });

        // Initializing Drawer Layout and ActionBarToggle
        ActionBarDrawerToggle actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, dashboardToolbar, R.string.openDrawer, R.string.closeDrawer) {

            @Override
            public void onDrawerClosed(View drawerView) {
                // Code here will be triggered once the drawer closes as we dont want anything to happen so we leave this blank
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerOpened(View drawerView) {
                // Code here will be triggered once the drawer open as we dont want anything to happen so we leave this blank

                super.onDrawerOpened(drawerView);
            }
        };

        //Setting the actionbarToggle to drawer layout
        drawerLayout.setDrawerListener(actionBarDrawerToggle);

        //calling sync state is necessay or else your hamburger icon wont show up
        actionBarDrawerToggle.syncState();
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement


        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();

//        getSupportFragmentManager().popBackStack();
    }
}
