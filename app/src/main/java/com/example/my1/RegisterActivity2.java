package com.example.my1;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.example.my1.data.model.RegisterModel;
import com.example.my1.databinding.ActivityRegister2Binding;
import com.google.gson.Gson;

import java.util.ArrayList;

public class RegisterActivity2 extends AppCompatActivity {
    private RegisterModel registerModel ;
    public void  onBackPressed(){
        super.onBackPressed();

        startActivity(new Intent(RegisterActivity2.this, RegisterActivity.class));
    }
    private ActivityRegister2Binding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {

        super.onCreate(savedInstanceState);
        binding = ActivityRegister2Binding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        binding.btnSave.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                //create shared
                SharedPreferences mPrefs = getSharedPreferences("MyShared",MODE_PRIVATE);

                //to save -get shared prefer to edit
                SharedPreferences.Editor prefsEditor = mPrefs.edit();

                //convert MyObject to json
                Gson gson = new Gson();
                String json = gson.toJson(registerModel);

                //put json to keep in shared
                prefsEditor.putString("MyObject",json);
                prefsEditor.apply();

                startActivity(new Intent(RegisterActivity2.this, LoginActivity.class));
                finish();
            }
        });

        Intent intent = getIntent();
        ArrayList<String> hobbyList =intent.getStringArrayListExtra("hobbyList");

        StringBuilder result = new StringBuilder();
        result.append("Hooby: ");
        if(hobbyList != null && hobbyList.size() != 0){

                for(int i=0 ; i<hobbyList.size(); i++){
                    result.append(hobbyList.get(i));

            }
            binding.texthobby.setText(result);

        }


        boolean isFemale = intent.getBooleanExtra("isFemale",false);
        if(isFemale) {
            binding.textgender.setText("Gender:Female");
        }else {
            binding.textgender.setText("Gender:Male");
        }
        if (intent != null){

            String image = intent.getStringExtra("imageUri");
            Uri imageUri = Uri.parse(image);
            binding.showImg.setImageURI(imageUri);

            String title = intent.getStringExtra("title");
            binding.textView2.setText(title);

            if (binding.btnSwitch.isChecked()){
                binding.llContact.setVisibility(View.VISIBLE);
            }else {
                binding.llContact.setVisibility(View.GONE);
            }

            registerModel = (RegisterModel) intent.getSerializableExtra("registration");
            if(registerModel != null){
                String user = registerModel.getUsername() + " " + registerModel.getPassword();
                String name = registerModel.getName();
                String age = registerModel.getAge();
                String bd = registerModel.getBirthday();
                String phone = registerModel.getPhone();
                String ad = registerModel.getAddress();
                String email = registerModel.getEmail();

                binding.textView1.setText(user);
                binding.textView3.setText(name);
                binding.textView4.setText(age);
                binding.textView5.setText(bd);
                binding.textView6.setText(phone);
                binding.textView7.setText(ad);
                binding.textView8.setText(email);

            }
        }




    }


}