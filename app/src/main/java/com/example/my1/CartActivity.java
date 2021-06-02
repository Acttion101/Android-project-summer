package com.example.my1;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.my1.data.adapter.RecyclerAdapter2;
import com.example.my1.data.model.AuthModel;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.databinding.ActivityCartBinding;

import java.io.Serializable;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CartActivity extends AppCompatActivity {
    List<ProductListModel> productModel;
    private ActivityCartBinding binding;
    private RecyclerAdapter2 recyclerAdapter;

    private  void setRecyclerView(List<ProductListModel> list){
        RecyclerAdapter2 recyclerAdapter = new RecyclerAdapter2(list);
        binding.recyclerview.setAdapter(recyclerAdapter);
        binding.recyclerview.setLayoutManager(new LinearLayoutManager(this, LinearLayoutManager.VERTICAL,false));
        this.recyclerAdapter = recyclerAdapter;
        recyclerAdapter.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                RecyclerView.ViewHolder viewHolder = (RecyclerView.ViewHolder) v.getTag();
                int position = viewHolder.getAdapterPosition();
                recyclerAdapter.removeFromCart(position);
            }
        });

    }

    @Override
    public void onBackPressed() {

        List<ProductListModel> selectProduct = recyclerAdapter.getAddToCartList();
        Intent intent = new Intent(CartActivity.this,RecyclerActivity.class);
        intent.putExtra("list",(Serializable) selectProduct);
        startActivity(intent);
        finish();

    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityCartBinding.inflate((getLayoutInflater()));
        View view = binding.getRoot();
        setContentView(view);

        Intent intent = getIntent();
        if (intent !=  null){
            productModel = (List<ProductListModel>) intent.getSerializableExtra("list");
            if(productModel != null){
                setRecyclerView(productModel);

            }
        }
    }

    private void  getCartList(String userrname ,String password){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(APIInterface.REGISTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<AuthModel> call = service.postAuth(userrname, password);
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {
                if (response.code() == 200 && response.body() != null){
                    String token = response.body().getToken();
                    SharedPreferences mPrefs = getSharedPreferences("MySharedPreference", MODE_PRIVATE);

                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    int id = 2 ;

                    prefsEditor.putString("MyToken",token);
                    prefsEditor.putInt("MYId",id);
                    prefsEditor.apply();

                    Intent intent = new Intent(CartActivity.this, MainActivity.class);
                    startActivity(intent);
                    finish();
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Toast.makeText(CartActivity.this, "STATUS : failure",
                        Toast.LENGTH_SHORT).show();
            }
        });
    }
}