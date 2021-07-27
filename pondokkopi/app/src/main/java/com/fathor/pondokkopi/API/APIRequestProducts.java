package com.fathor.pondokkopi.API;

import com.fathor.pondokkopi.Model.Products.ProductDetailResponse;
import com.fathor.pondokkopi.Model.Products.ProductResponse;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface APIRequestProducts {
    @GET("api/products")
    Call<ProductResponse> getProducts();
    @GET("api/products/{id}")
    Call<ProductDetailResponse> getProductsDetails(@Path("id") int id);
}
