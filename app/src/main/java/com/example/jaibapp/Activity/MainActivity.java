package com.example.jaibapp.Activity;


import android.content.Intent;
import android.os.Bundle;

import android.support.v4.app.Fragment;
import android.support.design.widget.NavigationView;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import com.example.jaibapp.Accounts.Fragments.AccountSourceFragment;
import com.example.jaibapp.Budget.Fragments.BudgetFragment;
import com.example.jaibapp.CategoryIncomeExpense.Fragment.CategoryIncomeExpenseFragment;
import com.example.jaibapp.Dashboard.Dashboard;
import com.example.jaibapp.R;
import com.example.jaibapp.Utilities.FragmentCommunicator;
import com.google.firebase.auth.FirebaseAuth;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener, FragmentCommunicator {
    private FirebaseAuth mAuth;
    private Toolbar toolbar;
    TextView profileName;
    TextView profileEmail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mAuth = FirebaseAuth.getInstance();


         toolbar = findViewById(R.id.app_bar_main_toolbar);
        setSupportActionBar(toolbar);


        getSupportFragmentManager().beginTransaction()
                .add(R.id.main_fragment,new Dashboard())
                .addToBackStack(null)
                .commit();

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawer, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);

        toggle.syncState();

        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        View navHeader = navigationView.getHeaderView(0);
        profileName = navHeader.findViewById(R.id.nav_header_main_profile_name);
        profileEmail = navHeader.findViewById(R.id.nav_header_main_profile_email);
        String email = mAuth.getCurrentUser().getEmail();

        profileEmail.setText(email);

        profileName.setText(mAuth.getCurrentUser().getDisplayName().toString());

    }

    @Override
    public void onBackPressed() {
        DrawerLayout drawer =  findViewById(R.id.activity_main_drawer_layout);
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);

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

    @SuppressWarnings("StatementWithEmptyBody")
    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        // Handle navigation view item clicks here.
        int id = item.getItemId();

        if (id == R.id.nav_dashboard) {
            changeFragment(new Dashboard());
        }else if (id == R.id.nav_budget) {

            changeFragment(new BudgetFragment());
        }else if (id == R.id.nav_category) {
            changeFragment(new CategoryIncomeExpenseFragment());
        }else if (id == R.id.nav_account) {
            changeFragment(new AccountSourceFragment());
        }else if (id == R.id.nav_friends) {

        }else if (id == R.id.nav_signout) {
            mAuth.signOut();
            Intent intent = new Intent(this, SigninActivity.class);
            startActivity(intent);
        }

        DrawerLayout drawer = findViewById(R.id.activity_main_drawer_layout);
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    void changeFragment(Fragment fragment)
    {
        getSupportFragmentManager()
                .beginTransaction()
                .replace(R.id.main_fragment,fragment)
                .addToBackStack(null).commit();
    }


    @Override
    public void ChangeFragmentCallback(Fragment fragment) {
        changeFragment(fragment);
    }
}
