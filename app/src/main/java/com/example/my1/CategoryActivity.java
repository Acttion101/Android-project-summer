package com.example.my1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Toast;

import com.example.my1.data.adapter.CategoryAdapter;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.ActivityCategoryBinding;
import com.google.android.material.navigation.NavigationView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CategoryActivity extends AppCompatActivity {

    private ActivityCategoryBinding binding;
    CategoryAdapter categoryAdapter ;
    List<ProductListModel> body;
    List<String> categories;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCategoryBinding.inflate((getLayoutInflater()));
        View view = binding.getRoot();
        setContentView(view);

        body = new ArrayList<>();
        getProductList();
//        binding.toolbar.setOnMenuItemClickListener(new Toolbar.OnMenuItemClickListener() {
//            @Override
//            public boolean onMenuItemClick(MenuItem item) {
//                switch (item.getItemId()){
//                    case R.id.menu1:
//                        categoryAdapter.sort("asc");
//                        return true;
//                    case R.id.menu2:
//                        categoryAdapter.sort("desc");
//                        return true;
//                    default:
//                        return false;
//                }
//            }
//        });

    }

    private void  getProductList(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();


        APIInterface service = retrofit.create(APIInterface.class);
        Call<List<ProductListModel>> call = service.getAllProduct();
        call.enqueue(new Callback<List<ProductListModel>>() {
            @Override
            public void onResponse(Call<List<ProductListModel>> call, Response<List<ProductListModel>> response) {

                body = response.body();
                if(body != null && body.size() !=0) {
                    categoryAdapter = new CategoryAdapter(body);
                    binding.recyclerview.setAdapter(categoryAdapter);
                    binding.recyclerview.setLayoutManager(new LinearLayoutManager(
                            CategoryActivity.this, LinearLayoutManager.VERTICAL,false));

                }
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {
                Toast.makeText(CategoryActivity.this, "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });


        APIInterface services = retrofit.create(APIInterface.class);
        Call<List<String>> call2 = services.getProductCategory();
        call2.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call2, Response<List<String>> response) {
                categories = response.body();
                if (categories != null && categories.size() != 0) {
                    Menu menu = binding.navigation.getMenu();
                    for (String category : categories){
                        menu.add(category);
                    }
                    menu.add("Filter all Category");


                    binding.toolbar.setNavigationOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(binding.drawerLayout.isDrawerOpen(GravityCompat.START)){
                                binding.drawerLayout.closeDrawer(GravityCompat.START);
                            }else{
                                binding.drawerLayout.openDrawer(GravityCompat.START);
                            }
                        }
                    });

                    binding.navigation.setNavigationItemSelectedListener(new NavigationView.OnNavigationItemSelectedListener() {
                        @Override
                        public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                            categoryAdapter.getFilter().filter(item.getTitle());
                            binding.drawerLayout.closeDrawer(GravityCompat.START);
                            return true;
                        }
                    });
                }
            }

            @Override
            public void onFailure(Call<List<String>> call2, Throwable t) {
                Toast.makeText(CategoryActivity.this, "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}