package com.example.room;

import android.annotation.SuppressLint;
import android.os.AsyncTask;
import android.os.Bundle;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import androidx.appcompat.app.AppCompatActivity;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class MainActivity extends AppCompatActivity {
    private ProductsDB db;
    private ListView listViewProducts, listViewCategories;
    private ArrayAdapter<String> adapterProducts, adapterCategories;
    private ExecutorService executorService = Executors.newSingleThreadExecutor();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        DBHelperWithLoader dbHelper = new DBHelperWithLoader(this);
        db = ProductsDB.getDatabase(this);

        listViewProducts = findViewById(R.id.listViewProducts);
        listViewCategories = findViewById(R.id.listViewCategories);

        loadCategories();
        listViewCategories.setOnItemClickListener((parent, view, position, id) -> {
            String selectedCategory = adapterCategories.getItem(position);
            loadProducts(selectedCategory);
        });
    }

    @SuppressLint("StaticFieldLeak")
    private void loadCategories() {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                return db.categoryDao().getAllCategoryNames();
            }

            @Override
            protected void onPostExecute(List<String> categories) {
                adapterCategories = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, categories);
                listViewCategories.setAdapter(adapterCategories);
            }
        }.execute();
    }

    @SuppressLint("StaticFieldLeak")
    private void loadProducts(final String categoryName) {
        new AsyncTask<Void, Void, List<String>>() {
            @Override
            protected List<String> doInBackground(Void... voids) {
                int categoryId = db.categoryDao().getCategoryIdByName(categoryName);
                return db.productDao().getProductsByCategory(categoryId);
            }

            @Override
            protected void onPostExecute(List<String> products) {
                adapterProducts = new ArrayAdapter<>(MainActivity.this, android.R.layout.simple_list_item_1, products);
                listViewProducts.setAdapter(adapterProducts);
            }
        }.execute();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        executorService.shutdown();
    }
}
