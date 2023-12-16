package com.example.room;

import androidx.room.*;

import java.util.List;

@Dao
public interface CategoryDao {
    @Query("SELECT name FROM categories")
    List<String> getAllCategoryNames();

    @Query("SELECT id FROM categories WHERE name = :categoryName")
    int getCategoryIdByName(String categoryName);
}

