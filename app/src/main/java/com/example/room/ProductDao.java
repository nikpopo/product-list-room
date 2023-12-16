package com.example.room;

import androidx.room.*;

import java.util.List;

@Dao
public interface ProductDao {
    @Query("SELECT name FROM products WHERE category_id = :categoryId")
    List<String> getProductsByCategory(int categoryId);
}


