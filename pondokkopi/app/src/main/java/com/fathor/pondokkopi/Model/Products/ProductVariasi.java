package com.fathor.pondokkopi.Model.Products;

public class ProductVariasi {
    Integer id_variasi;
    String nama_variasi;
    Integer berat;
    Integer harga;
    Integer stok;

    public Integer getId_variasi() {
        return id_variasi;
    }

    public void setId_variasi(Integer id_variasi) {
        this.id_variasi = id_variasi;
    }

    public String getNama_variasi() {
        return nama_variasi;
    }

    public void setNama_variasi(String nama_variasi) {
        this.nama_variasi = nama_variasi;
    }

    public Integer getBerat() {
        return berat;
    }

    public void setBerat(Integer berat) {
        this.berat = berat;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getStok() {
        return stok;
    }

    public void setStok(Integer stok) {
        this.stok = stok;
    }
}
