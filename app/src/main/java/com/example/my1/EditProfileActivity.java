package com.example.my1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;

import com.example.my1.data.model.RegisterModel;
import com.example.my1.databinding.ActivityEditProfileBinding;
import com.example.my1.databinding.ActivityRegisterBinding;
import com.google.gson.Gson;

public class EditProfileActivity extends AppCompatActivity {
    ActivityEditProfileBinding binding;
    ActivityRegisterBinding includeBinding;


    private RegisterModel getSharedPreference() {
        SharedPreferences mPrefs = getSharedPreferences("MyShared", MODE_PRIVATE);
        Gson gson = new Gson();
        String json = mPrefs.getString("MyObject", "");
        RegisterModel obj = gson.fromJson(json, RegisterModel.class);
        return obj;
    }



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityEditProfileBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        includeBinding = binding.layout;

        RegisterModel obj = getSharedPreference();
        if (obj != null) {
            includeBinding.inputusername.setText(obj.getUsername());
            includeBinding.inputPassword.setText(obj.getPassword());
            includeBinding.inputNiackname.setText(obj.getName());
            includeBinding.inputage.setText(obj.getAge());
            includeBinding.inputnumber.setText(obj.getPhone());
            includeBinding.inputbd.setText(obj.getBirthday());
            includeBinding.inputemail.setText(obj.getEmail());
            includeBinding.inputad.setText(obj.getAddress());
        }
        includeBinding.btngo.setText("Update");
        includeBinding.inputemail.setEnabled(false);
        includeBinding.inputusername.setEnabled(false);
        includeBinding.inputPassword.setEnabled(false);

    }
}