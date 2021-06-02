package com.example.my1.data.model;

import com.google.gson.annotations.SerializedName;

public class UserModel {
    @SerializedName("email")
    String email ;

    public String getEmail() {
        return email;
    }

    public UserModel(String email, String password) {
        this.email = email;
        this.password = password;
    }

    public String getPassword() {
        return password;
    }

    @SerializedName("password")
    String password;

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
