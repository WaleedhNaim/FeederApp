package com.example.nb_interview.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.example.nb_interview.apiservice.RepoManager;
import com.example.nb_interview.database.entity.User;
import com.example.nb_interview.database.entity.LocalProduct;
import com.example.nb_interview.model.Products;
import com.example.nb_interview.repository.ProductRepo;

import java.util.ArrayList;
import java.util.List;
import java.util.logging.Handler;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends BaseViewModel {
    private static final String TAG = "HomeViewModel";
    public MutableLiveData<String> username = new MutableLiveData<>();
    public MutableLiveData<List<Products>> productList = new MutableLiveData<>();
    private ProductRepo productRepo;

    public HomeViewModel(@NonNull Application application) {
        super(application);
        productRepo = RepoManager.getProductRepo();
    }

    /*
    Method to get the logged in user's username
     */
    public void getUsername() {
        User user = roomDatabase.userDao().getCurrentUser();
        if (user != null) {
            username.postValue(user.getUserName());
        }
    }

    /*
    Method which will to fetch the data from server
     */
    public void loadFromServer(LifecycleOwner lifecycleOwner) {
        isLoading.postValue(true);
        productRepo.getProducts().enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {

                isLoading.postValue(false);

                if (response.isSuccessful()) {
                    if (response.body() != null) {
                        //save locally
                        saveDataInLocally(response.body(), lifecycleOwner);
                    }
                }
            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                isLoading.postValue(false);
                onError.postValue(t.getLocalizedMessage());
            }
        });

    }

    /*
    Method to save the fetched data in the local database
     */
    public void saveDataInLocally(List<Products> productList, LifecycleOwner lifecycleOwner) {
        roomDatabase.productDao().clearAll();
        List<LocalProduct> localProducts = new ArrayList<>();
        for (Products products : productList) {
            localProducts.add(new LocalProduct(products));
        }
        roomDatabase.productDao().saveAll(localProducts);

        loadFromLocal(lifecycleOwner);
    }

    /*
    Method to fetch data from local database
     */
    public void loadFromLocal(LifecycleOwner lifecycleOwner) {
        Log.e(TAG, "loadFromLocal: ");
        List<Products> products = new ArrayList<>();

        roomDatabase.productDao().getAllProducts().observe(lifecycleOwner, new Observer<List<LocalProduct>>() {
            @Override
            public void onChanged(List<LocalProduct> localProducts) {
                products.clear();
                Log.e(TAG, "onChanged: " + localProducts.size());
                for (LocalProduct localProduct : localProducts) {
                    products.add(localProduct.getProducts());
                }

                productList.postValue(products);
            }
        });
    }

    /*
    Method to remove the selected item in the database by its id
     */
    public void removeItem(Products products){
        roomDatabase.productDao().deleteItem(products.getId());
    }

    /*
    Method to clear the table, after logout it clicked
     */
    public void logout(){
        roomDatabase.clearAllTables();
    }

}
