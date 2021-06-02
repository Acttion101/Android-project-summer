package com.example.my1;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.CheckBox;
import android.widget.RadioGroup;
import android.widget.Toast;

import com.example.my1.data.CircleTransform;
import com.example.my1.data.model.RegisterModel;
import com.example.my1.data.model.UniversityModel;
import com.example.my1.databinding.ActivityRegisterBinding;
import com.squareup.picasso.Picasso;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RegisterActivity extends AppCompatActivity {
    private @NonNull
    ActivityRegisterBinding binding;
    boolean isFemale;
    ArrayList<String> hobbyList = new ArrayList<>();
    Uri imageUri;
    String title;
    ArrayList<String> universityList = new ArrayList<>();

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 100 && resultCode == RESULT_OK && data != null) {

            imageUri = data.getData();
            Picasso.get()
                    .load(imageUri)
                    .error(R.drawable.icon_background)
                    .transform(new CircleTransform())
                    .into(binding.imgProfile);
        }
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = ActivityRegisterBinding.inflate(getLayoutInflater());
        View view = binding.getRoot();
        setContentView(view);

        getUniversityList();
        binding.inputusername.setText("eve.holt@reqres.in");
        binding.inputemail.setText("eve.holt@reqres.in");

        ArrayList<String>list = new ArrayList<>();
        list.add("Miss.");
        list.add("Mrs.");
        list.add("Mr.");
        list.add("Other");
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, android.R.layout.simple_dropdown_item_1line,list);
        binding.spinner.setAdapter(adapter);
        binding.spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {

            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                title = list.get(position);
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        binding.btnSelectImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent gallery = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.INTERNAL_CONTENT_URI);
                startActivityForResult(gallery, 100);

            }
        });
        View.OnClickListener onClickListener = new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                CheckBox checkBox = (CheckBox) v;
                if (checkBox.isChecked()) {
                    hobbyList.add(checkBox.getText().toString());
                } else {
                    hobbyList.remove(checkBox.getText().toString());
                }
            }
        };
        binding.checkBox1.setOnClickListener(onClickListener);
        binding.checkBox2.setOnClickListener(onClickListener);
        binding.checkBox3.setOnClickListener(onClickListener);

        binding.radiogender.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.radio_female:
                        isFemale = true;
                        break;
                    case R.id.radio_male:
                        isFemale = false;
                        break;
                }
            }
        });
        binding.inputemail.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {

            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {

            }

            @Override
            public void afterTextChanged(Editable s) {
                if (!isEmailValid(s.toString())) {
                    binding.inputemail.setError("Email format not match");
                }
            }

            private boolean isEmailValid(String email) {
                String expression = "^[\\w\\.-]+@([\\w\\-]+\\.)+[A-Z]{2,4}$";
                Pattern pattern = Pattern.compile(expression, Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(email);
                return matcher.matches();
            }


        });
        binding.btngo.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                String username = binding.inputusername.getText().toString();
                String password = binding.inputPassword.getText().toString();
                String nickname = binding.inputNiackname.getText().toString();
                String age = binding.inputage.getText().toString();
                String birthday = binding.inputbd.getText().toString();
                String phonenumber = binding.inputnumber.getText().toString();
                String address = binding.inputad.getText().toString();
                String email = binding.inputemail.getText().toString();





                if (username.equalsIgnoreCase("")) {
                    binding.inputusername.setHint("EnterName ");
                    binding.inputusername.setError("EnterName");
                    if (password.equalsIgnoreCase("")) {
                        binding.inputPassword.setHint("EnterName ");
                        binding.inputPassword.setError("EnterName");
                        if (email.equalsIgnoreCase("")) {
                            binding.inputemail.setHint("Enter email");
                            binding.inputemail.setError("Enter Email");
                        }
                    }


                }
                if (!username.equalsIgnoreCase("") && !password.equalsIgnoreCase("") && !email.equalsIgnoreCase("")) {
                    RegisterModel registerModel = new RegisterModel(username, password,nickname,age,birthday,phonenumber,address,email,isFemale,hobbyList,imageUri.toString());
                    Intent intent = new Intent(RegisterActivity.this, RegisterActivity2.class);
                    intent.putExtra("isFemale", isFemale);
                    intent.putExtra("title", title);
                    intent.putExtra("imageUri", imageUri.toString());
                    intent.putStringArrayListExtra("hobbyList", hobbyList);
                    intent.putExtra("registration", (Serializable) registerModel);
                    startActivity(intent);
                    finish();
                }


            }

        });

        }
        private void getUniversityList(){
            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("http://universities.hipolabs.com")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            APIInterface service = retrofit.create(APIInterface.class);
            Call<List<UniversityModel>> call = service.getAllUniversity("Thailand");
            call.enqueue(new Callback<List<UniversityModel>>() {
                @Override
                public void onResponse(Call<List<UniversityModel>> call, Response<List<UniversityModel>> response) {
                    List<UniversityModel> body = response.body();
                    for (UniversityModel obj : body){
                        if (obj != null){
                            universityList.add(obj.getName());
                        }
                    }

                    ArrayAdapter<String> adapter = new ArrayAdapter<>(RegisterActivity.this,
                            android.R.layout.simple_dropdown_item_1line, universityList);

                    binding.spUniversity.setAdapter(adapter);
                }

                @Override
                public void onFailure(Call<List<UniversityModel>> call, Throwable t) {
                    Toast.makeText(RegisterActivity.this, "STATUS : failure",
                            Toast.LENGTH_SHORT).show();

                }
            });

        }
}