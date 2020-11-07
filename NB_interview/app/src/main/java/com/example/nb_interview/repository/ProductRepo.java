package com.example.nb_interview.repository;

import com.example.nb_interview.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ProductRepo {
    @GET("api/v1/products")
    Call<List<Products>> getProducts();
}
