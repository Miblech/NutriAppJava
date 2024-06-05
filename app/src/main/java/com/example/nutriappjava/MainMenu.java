package com.example.nutriappjava;

import static com.example.nutriappjava.R.*;



import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.example.nutriappjava.fragments.AboutFragment;
import com.example.nutriappjava.fragments.AddLogsFragment;
import com.example.nutriappjava.fragments.FoodFragment;
import com.example.nutriappjava.fragments.HomeFragment;
import com.example.nutriappjava.fragments.LogsFragment;
import com.example.nutriappjava.fragments.ProfileFragment;
import com.example.nutriappjava.fragments.SettingsFragment;
import com.google.android.material.navigation.NavigationView;

public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(layout.activity_main_menu);

        Toolbar toolbar = findViewById(id.toolbar);
        drawerLayout = findViewById(id.drawer_layout);
        NavigationView navigationView = findViewById(id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, string.open_nav, string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        String username = sharedPreferences.getString("username", "username");
        String email = sharedPreferences.getString("email", "email");

        TextView usernameTextView = headerView.findViewById(R.id.header_username);
        TextView emailTextView = headerView.findViewById(R.id.header_email);
        Log.d("MainMenu", "Found usernameTextView: " + (usernameTextView!= null? "Yes" : "No"));
        Log.d("MainMenu", "Found emailTextView: " + (emailTextView!= null? "Yes" : "No"));

        if (usernameTextView!= null) {
            Log.d("MainMenu", "Setting username: " + username);
            usernameTextView.setText(username);
        } else {
            Log.e("MainMenu", "usernameTextView is null");
        }

        if (emailTextView!= null) {
            Log.d("MainMenu", "Setting email: " + email);
            emailTextView.setText(email);
        } else {
            Log.e("MainMenu", "emailTextView is null");
        }

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment(DatabaseHelper.getInstance(this))).commit();
            navigationView.setCheckedItem(id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        if (item.getItemId() == id.nav_home) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new HomeFragment(DatabaseHelper.getInstance(this))).commit();
        } else if (item.getItemId() == id.nav_profile) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new ProfileFragment()).commit();
        } else if (item.getItemId() == id.nav_food) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new FoodFragment()).commit();
        } else if (item.getItemId() == id.nav_logs) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new LogsFragment()).commit();
        } else if (item.getItemId() == id.nav_add_logs) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new AddLogsFragment()).commit();
        } else if (item.getItemId() == id.nav_settings) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new SettingsFragment()).commit();
        } else if (item.getItemId() == id.nav_info) {
            getSupportFragmentManager().beginTransaction().replace(id.fragment_container, new AboutFragment()).commit();
        } else if (item.getItemId() == id.nav_logout) {
            Intent intent = new Intent(MainMenu.this, Login.class);
            startActivity(intent);
            finish();
        }
        drawerLayout.closeDrawer((NavigationView) findViewById(id.nav_view));
        return true;
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }
}
