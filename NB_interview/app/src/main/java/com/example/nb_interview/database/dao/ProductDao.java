package com.example.nb_interview.database.dao;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;

import com.example.nb_interview.database.entity.LocalProduct;
import com.example.nb_interview.model.Products;

import java.util.List;

@Dao
public interface ProductDao {


    @Insert
    void saveAll(List<LocalProduct> localProductList);

    @Query("delete from LocalProduct where id=:id")
    void deleteItem(String id);

    @Query("select * from LocalProduct")
    LiveData<List<LocalProduct>> getAllProducts();

    @Query("delete from LocalProduct where primaryKey=(select max(primaryKey) from localproduct)")
    void deleteLastItem();

    @Query("delete from LocalProduct")
    void clearAll();
}
