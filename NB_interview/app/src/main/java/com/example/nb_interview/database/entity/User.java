package com.example.nb_interview.database.entity;

import androidx.room.Entity;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import com.example.nb_interview.database.typeconverter.TypeProduct;
import com.example.nb_interview.model.Products;

@Entity
public class User {

    @PrimaryKey(autoGenerate = true)
    private int primaryKey;

    private String userName;

    public int getPrimaryKey() {
        return primaryKey;
    }

    public void setPrimaryKey(int primaryKey) {
        this.primaryKey = primaryKey;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }


}
