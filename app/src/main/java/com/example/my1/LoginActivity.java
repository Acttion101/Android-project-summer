package com.example.my1;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.example.my1.data.model.AuthModel;
import com.example.my1.data.model.RegisterModel;
import com.example.my1.data.model.UserModel;
import com.example.my1.databinding.ActivityLoginBinding;
import com.google.gson.Gson;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private ActivityLoginBinding binding;


    private boolean getSharedPreference(String username, String password) {
        SharedPreferences mPrefs = getSharedPreferences("MyShared", MODE_PRIVATE);

        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        RegisterModel obj = gson.fromJson(json, RegisterModel.class);

        boolean isLoggedIn = false;
        if (obj != null) {
            if (username.equals(obj.getUsername()) && password.equals(obj.getPassword())) {
                isLoggedIn = true;
            }
        }
        return isLoggedIn;
    }
    private void getApi(String username , String password) {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(APIInterface.REGISTER_BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIInterface service = retrofit.create(APIInterface.class);
//        Call<AuthModel> call = service.postAuth(username, password);
        Call<AuthModel> call = service.postAcuth(new UserModel(username,password));
        call.enqueue(new Callback<AuthModel>() {
            @Override
            public void onResponse(Call<AuthModel> call, Response<AuthModel> response) {

                if (response.code() == 200 && response.body() != null) {

                    String token = response.body().getToken();
                    int id = 4;
                    SharedPreferences mPrefs = getSharedPreferences("shareToken", MODE_PRIVATE);
                    SharedPreferences.Editor prefsEditor = mPrefs.edit();
                    prefsEditor.putString("token", token);
                    prefsEditor.putInt("id", id);
                    prefsEditor.apply();

                    Intent intent = new Intent(LoginActivity.this, MainActivity.class);
                    startActivity(intent);
                }
            }

            @Override
            public void onFailure(Call<AuthModel> call, Throwable t) {
                Toast.makeText(LoginActivity.this, "username or password Error", Toast.LENGTH_SHORT)
                        .show();
            }
        });
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {


        super.onCreate(savedInstanceState);
        binding = ActivityLoginBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.etUsername.setText("eve.holt@reqres.in");
        binding.etPassword.setText("1234");

        binding.btnLogin.setOnClickListener(new View.OnClickListener() {


            @Override
            public void onClick(View v) {
                String username = binding.etUsername.getText().toString();
                String password = binding.etPassword.getText().toString();
                getApi(username,password);


//                if (username.equalsIgnoreCase("")) {
//                    binding.etUsername.setHint("EnterName ");
//                    Toast.makeText(LoginActivity.this, "username or password Error", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                if (username.equalsIgnoreCase("")) {
//                    binding.etPassword.setHint("EnterName ");
//                    Toast.makeText(LoginActivity.this, "username or password Error", Toast.LENGTH_SHORT)
//                            .show();
//                }
//                if (getSharedPreference(username, password)) {
//                    startActivity(new Intent(LoginActivity.this, MainActivity.class));
//                } else {
//                    AlertDialog dialog = new AlertDialog.Builder(LoginActivity.this).setTitle("Login")
//                            .setMessage("Incorrect username or password")
//                            .setPositiveButton("OK", ((dialog1, which) -> dialog1.dismiss())).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
//                                @Override
//                                public void onClick(DialogInterface dialog, int which) {
//                                    dialog.dismiss();
//                                }
//                            }).create();
//                    dialog.show();
//                }

            }


        });

        binding.textRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(LoginActivity.this, RegisterActivity.class));
                finish();
            }
        });


    }
}