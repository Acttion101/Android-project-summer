package com.example.my1.data.model;

import com.google.gson.annotations.SerializedName;

import java.io.Serializable;

public class ProductListModel implements Serializable {
    @SerializedName("id")
    private int id;

    @SerializedName("title")
    private String title;

    @SerializedName("price")
    private double price;

    @SerializedName("description")
    private String description;

    @SerializedName("category")
    private String category;

    @SerializedName("image")
    private String image;

    public int getId(){return id;}
    public void setId(int id) {
        this.id = id;
    }

    public String getTitle(){return title;}
    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice(){return price;}

    public String getDescription(){return description;}

    public String getCategory(){return  category;}

    public String getImage(){return  image;}

    public ProductListModel(int id ,String title,int price,String description,String category,String image){
        this.id = id ;
        this.title = title;
        this.price =price;
        this.description =description;
        this.category=category;
        this.image =image;


    }




}
