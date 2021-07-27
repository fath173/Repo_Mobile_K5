package com.fathor.pondokkopi.Model.Products;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.io.Serializable;
import java.util.List;

public class Products {

    Integer id;
    String nama_produk;
    String deskripsi;
    String gambar_mobile;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
    }

    public String getDeskripsi() {
        return deskripsi;
    }

    public void setDeskripsi(String deskripsi) {
        this.deskripsi = deskripsi;
    }

    public String getGambar_mobile() {
        return gambar_mobile;
    }

    public void setGambar_mobile(String gambar_mobile) {
        this.gambar_mobile = gambar_mobile;
    }
}
