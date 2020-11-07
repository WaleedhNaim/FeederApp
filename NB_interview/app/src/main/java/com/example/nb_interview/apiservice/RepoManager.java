package com.example.nb_interview.apiservice;


import com.example.nb_interview.repository.ProductRepo;

public class RepoManager {

    private RepoManager() {
    }

    //Dummy api
    private static final String BASE_URL = "https://5fa398f5f10026001618d9c7.mockapi.io/";

    public static ProductRepo getProductRepo() {
        return RetrofitClient.getClient(BASE_URL).create(ProductRepo.class);
    }


}
