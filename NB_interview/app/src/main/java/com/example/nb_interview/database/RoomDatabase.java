package com.example.nb_interview.database;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;

import com.example.nb_interview.database.dao.UserDao;
import com.example.nb_interview.database.dao.ProductDao;
import com.example.nb_interview.database.entity.User;
import com.example.nb_interview.database.entity.LocalProduct;


@Database(entities = {LocalProduct.class, User.class}, exportSchema = false, version = 2)
public abstract class RoomDatabase extends androidx.room.RoomDatabase {
    private static RoomDatabase instance;
    private static final String DB_Name = "waleedhApp";

    public static synchronized RoomDatabase getInstance(Context context) {
        if (instance == null) {
            instance = Room.databaseBuilder(context.getApplicationContext(), RoomDatabase.class, DB_Name)
                    .fallbackToDestructiveMigration()
                    .allowMainThreadQueries()
                    .build();
        }
        return instance;
    }


    public abstract ProductDao productDao();
    public abstract UserDao userDao();




}
