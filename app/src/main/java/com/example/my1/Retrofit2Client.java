package com.example.my1;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.my1.data.model.PostModel;
import com.example.my1.databinding.ActivityRetrofit2ClientBinding;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class Retrofit2Client extends AppCompatActivity {
    private ActivityRetrofit2ClientBinding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRetrofit2ClientBinding.inflate((getLayoutInflater()));
        View view = binding.getRoot();
        setContentView(view);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://jsonplaceholder.typicode.com")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIInterface service = retrofit.create(APIInterface.class);
        Call<PostModel> call = service.getPostById(1);

        call.enqueue(new Callback<PostModel>() {
            @Override
            public void onResponse(Call<PostModel> call, Response<PostModel> response) {
                Toast.makeText(Retrofit2Client.this,"STATUS: "+
                        response.code(),Toast.LENGTH_SHORT).show();

                PostModel body = response.body();
                if (body != null ){
                    String result = " GET LIST\n\nTitle:" + body.getTitle() + "\n"
                            + "Body : " + body.getBody() + "\n"
                            + "UserID : "  + body.getUserId();
                    binding.txtResult.setText(result);
                }
            }

            @Override
            public void onFailure(Call<PostModel> call, Throwable t) {
                Toast.makeText(Retrofit2Client.this,"STATUS : Failure",
                        Toast.LENGTH_SHORT).show();
            }
        });

    }
}