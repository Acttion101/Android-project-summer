package com.example.my1;



import com.example.my1.data.model.AuthModel;
import com.example.my1.data.model.CartModel;
import com.example.my1.data.model.PostModel;
import com.example.my1.data.model.ProductListModel;
import com.example.my1.data.model.UniversityModel;
import com.example.my1.data.model.UserModel;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface APIInterface {
    String BASE_URL = "https://fakestoreapi.com" ;
    String REGISTER_BASE_URL = "https://reqres.in";

    @FormUrlEncoded
    @POST("api/login")
    Call<AuthModel>postAuth(@Field("email") String email,
                            @Field("password") String password);
    @FormUrlEncoded
    @POST("api/register")
    Call<AuthModel>postRegister(@Field("email") String email,
                                @Field("password") String password);

    @GET("/posts/{id}")
    Call<PostModel> getPostById(@Path("id")int id);

    @GET("/products")
    Call<List<ProductListModel>> getAllProduct();

    @GET("/search?")
    Call<List<UniversityModel>> getAllUniversity(@Query("country") String country);

//    @FormUrlEncoded
//    @POST("/api/login")
//    Call<AuthModel> postAuth(@Field("email") String email, @Field("password") String password);

    @POST("/api/login")
    Call<AuthModel> postAcuth(@Body  UserModel userModel );
    @GET("/products/categories")
    Call<List<String>> getProductCategory();


    @GET("/products/{id}")
    Call<ProductListModel> getPostByIdProduct(@Path("id")int id);

    @POST("/carts")
    Call<CartModel> postCart(@Body CartModel cartModel);




}
