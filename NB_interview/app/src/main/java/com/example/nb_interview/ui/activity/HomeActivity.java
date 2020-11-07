package com.example.nb_interview.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.DefaultItemAnimator;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import androidx.swiperefreshlayout.widget.SwipeRefreshLayout;
import androidx.work.ExistingPeriodicWorkPolicy;
import androidx.work.PeriodicWorkRequest;
import androidx.work.WorkManager;

import android.app.Dialog;
import android.content.Intent;
import android.graphics.Canvas;
import android.graphics.Color;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.widget.Toolbar;

import com.example.nb_interview.R;
import com.example.nb_interview.model.Products;
import com.example.nb_interview.ui.adapter.ProductAdapter;
import com.example.nb_interview.utils.AlertType;
import com.example.nb_interview.utils.InternetManager;
import com.example.nb_interview.utils.Utils;
import com.example.nb_interview.viewmodel.HomeViewModel;
import com.example.nb_interview.worker.RemoveItemWorker;
import com.facebook.shimmer.ShimmerFrameLayout;
import com.google.android.material.navigation.NavigationView;

import java.util.List;
import java.util.concurrent.TimeUnit;

import it.xabaras.android.recyclerview.swipedecorator.RecyclerViewSwipeDecorator;

public class HomeActivity extends AppCompatActivity implements  NavigationView.OnNavigationItemSelectedListener, SwipeRefreshLayout.OnRefreshListener {
    private static final String TAG = "HomeActivity";
    private LinearLayout ll_empty;
    private RecyclerView recyclerView;
    private ShimmerFrameLayout shimmerFrameLayout;
    private HomeViewModel viewModel;
    private TextView lbl_username;
    private InternetManager internetManager;
    public ProductAdapter adapter;
    private SwipeRefreshLayout homeSwipeContainer;
    private Toolbar toolbar;
    private DrawerLayout drawer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        initUi();

        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawer.addDrawerListener(toggle);
        toggle.syncState();
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);


        initConfig();

        initAdapter();

        shimmerFrameLayout.setVisibility(View.VISIBLE);
        shimmerFrameLayout.startShimmer();

        viewModel.username.observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                lbl_username.setText(s + " !");
            }
        });

        viewModel.getUsername();

        viewModel.isLoading.observe(this, new Observer<Boolean>() {
            @Override
            public void onChanged(Boolean aBoolean) {
                if (aBoolean) {
                    shimmerFrameLayout.setVisibility(View.VISIBLE);
                    shimmerFrameLayout.startShimmer();
                } else {
                    shimmerFrameLayout.setVisibility(View.GONE);
                    shimmerFrameLayout.stopShimmer();
                }
            }
        });

        //Get all the item
        viewModel.productList.observe(this, new Observer<List<Products>>() {
            @Override
            public void onChanged(List<Products> productList) {

                Log.e("RemoveItemWorker", "onChanged: " );

                shimmerFrameLayout.setVisibility(View.GONE);
                shimmerFrameLayout.stopShimmer();
                if (productList == null) {
                    recyclerView.setVisibility(View.GONE);
                    ll_empty.setVisibility(View.VISIBLE);
                } else {
                    if (productList.size() > 0) {
                        adapter.setData(productList);

                        recyclerView.setVisibility(View.VISIBLE);
                        ll_empty.setVisibility(View.GONE);


                    } else {
                        recyclerView.setVisibility(View.GONE);
                        ll_empty.setVisibility(View.VISIBLE);
                    }
                }

            }
        });

        loadData();

        setWork();

        // Call back for swipe delete
        ItemTouchHelper.SimpleCallback callback = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT) {
            @Override
            public boolean onMove(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, RecyclerView.ViewHolder target) {
                return false;
            }

            @Override
            public int getSwipeDirs(RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder) {
                return super.getSwipeDirs(recyclerView, viewHolder);
            }

            @Override
            public void onSwiped(RecyclerView.ViewHolder viewHolder, int direction) {
                try {
                    final int position = viewHolder.getAdapterPosition();
                    Products products = adapter.getProduct(position);

                    //Remove item from local
                    viewModel.removeItem(products);

                    //Remove item from the list
                    adapter.removeItem(position);


                } catch (Exception e) {
                    e.printStackTrace();
                }
            }

            @Override
            public void onChildDraw(Canvas c, RecyclerView recyclerView, RecyclerView.ViewHolder viewHolder, float dX, float dY, int actionState, boolean isCurrentlyActive) {
                new RecyclerViewSwipeDecorator.Builder(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive)
                        .addSwipeLeftBackgroundColor(ContextCompat.getColor(HomeActivity.this, R.color.colorRed))
                        // .addSwipeLeftActionIcon(R.drawable.fav4)
                        .addSwipeLeftLabel("Delete")
                        .setSwipeLeftLabelColor(Color.WHITE)
                        .create()
                        .decorate();
                super.onChildDraw(c, recyclerView, viewHolder, dX, dY, actionState, isCurrentlyActive);
            }
        };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(callback);
        itemTouchHelper.attachToRecyclerView(recyclerView);


    }

    /*
    Method to start the work, which will delete one item every x minutes
     */
    private void setWork() {
        PeriodicWorkRequest periodicWorkRequest = new PeriodicWorkRequest.Builder(
                RemoveItemWorker.class, 15, TimeUnit.MINUTES,
                15, // flex interval - worker will run somewhen within this period of time, but at the end of repeating interval
                TimeUnit.MINUTES
        ).build();
        WorkManager.getInstance(this).enqueueUniquePeriodicWork("Send Data",  ExistingPeriodicWorkPolicy.KEEP,periodicWorkRequest);
    }

    /*
    Method which initialize all the Ui Related to home activity
     */
    private void initUi(){
        ll_empty = findViewById(R.id.ll_empty);
        recyclerView = findViewById(R.id.rv_product);
        shimmerFrameLayout = findViewById(R.id.shimmerLayout);
        lbl_username = findViewById(R.id.lbl_username);
        homeSwipeContainer = findViewById(R.id.homeSwipeContainer);

        toolbar = findViewById(R.id.toolbar);
        toolbar.setTitle(getString(R.string.app_name));
        drawer = findViewById(R.id.drawer_layout);

        setSupportActionBar(toolbar);
    }

    /*
    Method to initialize all the configurations in home activity
     */
    private void initConfig(){
        viewModel = new ViewModelProvider(this).get(HomeViewModel.class);
        internetManager = new InternetManager();
        adapter = new ProductAdapter(this);
        homeSwipeContainer.setOnRefreshListener(this);
    }

    /*
    Method to initialize the adapter
     */
    private void initAdapter(){
        LinearLayoutManager linearLayoutManager2 = new LinearLayoutManager(getApplicationContext(), RecyclerView.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager2);
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        recyclerView.setAdapter(adapter);
    }

    /*
    Method to load data from server or local. if no internet it will load from local db
     */
    private void loadData() {
        if (internetManager.isAvailable(this)) {
            Utils.showAlert(this, getString(R.string.loading_from_server), AlertType.SUCCESS);
            viewModel.loadFromServer(this);
        } else {
            Utils.showAlert(this, getString(R.string.loading_from_local), AlertType.WARNING);
            viewModel.loadFromLocal(this);
        }
    }

    @Override
    public void onRefresh() {
        loadData();
        homeSwipeContainer.setRefreshing(false);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.nav_home:

                break;
            case R.id.nav_aboutUs:
                finish();
                startActivity(new Intent(HomeActivity.this, AboutUsActivity.class));
                break;
            case R.id.nav_logout:
                viewModel.logout();
                finish();
                startActivity(new Intent(HomeActivity.this, LoginActivity.class));
                break;
        }

        drawer.closeDrawer(GravityCompat.START);
        return true;
    }
}