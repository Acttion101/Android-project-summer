package com.example.my1;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Toast;

import com.example.my1.databinding.ActivityRegister2Binding;
import com.example.my1.databinding.ActivityRegisterBinding;

import java.util.ArrayList;

public class ListViewActivity extends AppCompatActivity {

    private com.example.my1.databinding.ActivityListViewBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = com.example.my1.databinding.ActivityListViewBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        ArrayList<String> list = new ArrayList<>();
        for (int i = 0; i < 20; i ++){
            list.add("listview" + i );
        }

        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,list);
        binding.listView.setAdapter(adapter);
        binding.listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"on Click : "+ list.get(position),Toast.LENGTH_SHORT).show();
            }
        });
        binding.listView.setOnItemLongClickListener(new AdapterView.OnItemLongClickListener() {
            @Override
            public boolean onItemLongClick(AdapterView<?> parent, View view, int position, long id) {
                Toast.makeText(ListViewActivity.this,"Long Click : "+ list.get(position),Toast.LENGTH_SHORT).show();
                return false;
            }
        });
    }
}