package com.example.my1.data.model;

import com.google.gson.annotations.SerializedName;

public class AuthModel {
    @SerializedName("id")
    int id;

    @SerializedName("token")
    String token;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public AuthModel(int id, String token) {
        this.id = id;
        this.token = token;
    }
}
