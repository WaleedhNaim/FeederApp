package com.example.nb_interview.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.nb_interview.database.typeconverter.TypeProduct;
import com.example.nb_interview.model.Products;

@Entity
public class LocalProduct {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    @TypeConverters(TypeProduct.class)
    private Products products;

    private String id;

    public Products getProducts() {
        return products;
    }

    public void setProducts(Products products) {
        this.products = products;
    }

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public LocalProduct(Products products) {
        this.products = products;
        this.id = products.getId();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
