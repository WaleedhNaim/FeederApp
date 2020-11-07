package com.example.nb_interview.database.dao;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nb_interview.database.entity.User;

@Dao
public interface UserDao {

    @Insert
    void save(User user);

    @Query("select * from User")
    User getCurrentUser();

}
