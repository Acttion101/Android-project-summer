package com.example.my1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;


import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.ActivityRecyclerBinding;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RecyclerActivity extends AppCompatActivity {
    private ActivityRecyclerBinding binding;
    RecyclerAdapter recyclerAdapter ;
    List<ProductListModel> body;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRecyclerBinding.inflate((getLayoutInflater()));
        View view = binding.getRoot();
        setContentView(view);
        body = new ArrayList<>();
        setUpAdapter();
        getProductList();

        binding.btnViewcart.setOnClickListener(new View.OnClickListener(){

            @Override
            public void onClick(View v) {
                List<ProductListModel> selectProduct = recyclerAdapter.getAddToCardList();
                if(selectProduct != null){
                    Intent intent = new Intent(RecyclerActivity.this, CartActivity.class);
                    intent.putExtra("list", (Serializable) selectProduct);
                    startActivity(intent);
                    finish();
                }
            }
        });

    }

    @Override
    protected void onResume(){
        super.onResume();
        Intent intent = getIntent();
        if(intent != null){
            List<ProductListModel> currentList = (List<ProductListModel>) intent.getSerializableExtra("list");
            if (currentList != null){
                recyclerAdapter.updateCart(currentList);

            }
        }
    }
    private void setUpAdapter(){
        recyclerAdapter = new RecyclerAdapter (body);
        binding.recyclerview.setAdapter(recyclerAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(
                RecyclerActivity.this, LinearLayoutManager.VERTICAL,false));

        recyclerAdapter.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();

                recyclerAdapter.addToCart(position);
                Toast.makeText(RecyclerActivity.this,"Recycler Click:" + body.get(position),Toast.LENGTH_SHORT).show();
            }
        });
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
                    recyclerAdapter.updateList(body);
                }
            }

            @Override
            public void onFailure(Call<List<ProductListModel>> call, Throwable t) {
                Toast.makeText(RecyclerActivity.this, "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}