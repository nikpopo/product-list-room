package com.example.room;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

@Database(entities = {Product.class, Category.class}, version = 1)
public abstract class ProductsDB extends RoomDatabase {
    public abstract ProductDao productDao();
    public abstract CategoryDao categoryDao();

    private static final String DB_NAME = "products.db";
    private static volatile ProductsDB INSTANCE = null;

    public static ProductsDB getDatabase(final Context context) {
        if (INSTANCE == null) {
            synchronized (ProductsDB.class) {
                if (INSTANCE == null) {
                    INSTANCE = Room.databaseBuilder(context.getApplicationContext(),
                                    ProductsDB.class, DB_NAME)
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return INSTANCE;
    }
}

