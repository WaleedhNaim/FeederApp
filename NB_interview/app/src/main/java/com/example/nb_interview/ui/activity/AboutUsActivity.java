package com.example.nb_interview.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.ViewModelProvider;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.Toast;

import com.example.nb_interview.R;
import com.example.nb_interview.ui.adapter.ProductAdapter;
import com.example.nb_interview.utils.InternetManager;
import com.example.nb_interview.viewmodel.HomeViewModel;
import com.google.android.material.navigation.NavigationView;

public class AboutUsActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener{

    private Toolbar toolbar;
    private DrawerLayout drawer;
    private HomeViewModel viewModel;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_about_us);

        initUi();

        initConfig();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);

    }

    /*
   Method which initialize all the Ui Related to about us activity
    */
    private void initUi(){
        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
    }

    /*
    Method to initialize all the configurations in about us activity
     */
    private void initConfig(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:
                finish();
                startActivity(new Intent(AboutUsActivity.this, HomeActivity.class));
                break;
            case R.id.nav_aboutUs:
                  break;
            case R.id.nav_logout:
                viewModel.logout();
                finish();
                startActivity(new Intent(AboutUsActivity.this, LoginActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}