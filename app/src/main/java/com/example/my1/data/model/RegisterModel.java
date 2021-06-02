package com.example.my1.data.model;

import java.io.Serializable;
import java.util.ArrayList;

public class RegisterModel implements Serializable {
    public RegisterModel(String username, String password, String name, String age, String birthday, String phone, String address, String email, boolean gender, ArrayList<String> hobby, String img) {
        this.username = username;
        this.password = password;
        this.name = name;
        this.age = age;
        this.birthday = birthday;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.gender = gender;
        this.hobby = hobby;
        this.img = img;
    }

    String username;
    String password;
    String name;
    String age;
    String birthday;
    String phone;
    String address;
    String email;
    boolean gender;
    ArrayList<String> hobby;
    String img;

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean getGender() {
        return gender;
    }

    public void setGender(Boolean gender) {
        this.gender = gender;
    }

    public ArrayList<String> getHobby() {
        return hobby;
    }

    public void setHobby(ArrayList hobby) {
        this.hobby = hobby;
    }

    public String getImg() {
        return img;
    }

    public void setImg(String img) {
        this.img = img;
    }
}
