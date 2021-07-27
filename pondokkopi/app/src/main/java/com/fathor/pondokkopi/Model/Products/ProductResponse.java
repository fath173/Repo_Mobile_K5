package com.fathor.pondokkopi.Model.Products;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ProductResponse {
    private String message;
    private List<Products> data;


    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Products> getData() {
        return data;
    }

    public void setData(List<Products> data) {
        this.data = data;
    }
}
