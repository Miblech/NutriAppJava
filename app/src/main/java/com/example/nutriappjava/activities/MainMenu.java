package com.example.nutriappjava.activities;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.lifecycle.ViewModelProvider;
import com.example.nutriappjava.R;
import com.example.nutriappjava.fragments.AboutFragment;
import com.example.nutriappjava.fragments.FoodFragment;
import com.example.nutriappjava.fragments.FoodSearchLibraryFragment;
import com.example.nutriappjava.fragments.SearchFoodFragment;
import com.example.nutriappjava.fragments.HomeFragment;
import com.example.nutriappjava.fragments.LogsFragment;
import com.example.nutriappjava.fragments.ProfileFragment;
import com.example.nutriappjava.fragments.SettingsFragment;
import com.example.nutriappjava.viewmodels.FoodViewModel;
import com.google.android.material.navigation.NavigationView;

@SuppressWarnings("deprecation")
public class MainMenu extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    private DrawerLayout drawerLayout;
    private TextView usernameTextView;
    private TextView emailTextView;
    private SharedPreferences sharedPreferences;
    private FoodViewModel foodViewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        Toolbar toolbar = findViewById(R.id.toolbar);
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        View headerView = navigationView.getHeaderView(0);
        navigationView.setNavigationItemSelectedListener(this);

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar, R.string.open_nav, R.string.close_nav);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);

        usernameTextView = headerView.findViewById(R.id.header_username);
        emailTextView = headerView.findViewById(R.id.header_email);

        updateHeader();

        foodViewModel = new ViewModelProvider(this).get(FoodViewModel.class);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        Fragment selectedFragment = null;

        int itemId = item.getItemId();
        if (itemId == R.id.nav_home) {
            selectedFragment = new HomeFragment();
        } else if (itemId == R.id.nav_profile) {
            selectedFragment = new ProfileFragment();
        } else if (itemId == R.id.nav_food) {
            selectedFragment = new FoodFragment();
        } else if (itemId == R.id.nav_logs) {
            selectedFragment = new LogsFragment();
        } else if (itemId == R.id.nav_settings) {
            selectedFragment = new SettingsFragment();
        } else if (itemId == R.id.nav_info) {
            selectedFragment = new AboutFragment();
        } else if (itemId == R.id.nav_logout) {
            handleLogout();
            return true;
        } else if (itemId == R.id.nav_search_food) {
            selectedFragment = new FoodSearchLibraryFragment();
        } else if (itemId == R.id.nav_list_all_food) {
            foodViewModel.loadFoods();
            selectedFragment = new FoodFragment();
        } else if (itemId == R.id.nav_sort_by_protein) {
            foodViewModel.loadFoodsSortedBy("protein");
            selectedFragment = new FoodFragment();
        } else if (itemId == R.id.nav_sort_by_carbohydrate) {
            foodViewModel.loadFoodsSortedBy("carbohydrate");
            selectedFragment = new FoodFragment();
        } else if (itemId == R.id.nav_sort_by_fat) {
            foodViewModel.loadFoodsSortedBy("fat");
            selectedFragment = new FoodFragment();
        } else if (itemId == R.id.nav_categories) {
            foodViewModel.loadCategories();
            selectedFragment = new FoodFragment();
        }

        if (selectedFragment != null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container, selectedFragment).addToBackStack(null).commit();
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

    private void handleLogout() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserDetails", MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.clear();
        editor.apply();

        Intent intent = new Intent(MainMenu.this, Login.class);
        startActivity(intent);
        finish();
    }

    private void updateHeader() {
        String username = sharedPreferences.getString("username", "username");
        String email = sharedPreferences.getString("email", "email");

        usernameTextView.setText(username);
        emailTextView.setText(email);
    }

    @Override
    public void onBackPressed() {
        super.onBackPressed();
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            FragmentManager fragmentManager = getSupportFragmentManager();
            if (fragmentManager.getBackStackEntryCount() > 1) {
                fragmentManager.popBackStack();
            } else {
                moveTaskToBack(true);
            }
        }
    }
}