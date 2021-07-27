package com.fathor.pondokkopi.Model.Products;

import java.util.List;

public class ProductDetailResponse {
    private String message;
    private Integer totalStok;
    private List<ProductVariasi> variasi;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTotalStok() {
        return totalStok;
    }

    public void setTotalStok(Integer totalStok) {
        this.totalStok = totalStok;
    }

    public List<ProductVariasi> getVariasi() {
        return variasi;
    }

    public void setVariasi(List<ProductVariasi> variasi) {
        this.variasi = variasi;
    }
}

