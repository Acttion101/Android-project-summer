package com.example.my1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.my1.databinding.ActivityMain3Binding;
import com.example.my1.databinding.ActivityRegister2Binding;
import com.example.my1.databinding.ActivityRegisterBinding;

public class MainActivity3 extends AppCompatActivity {
    @NonNull ActivityMain3Binding binding;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main3);
        binding = ActivityMain3Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);
        binding.febWeb.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity3.this,WebViewActivity.class));
            }
        });
    }
}