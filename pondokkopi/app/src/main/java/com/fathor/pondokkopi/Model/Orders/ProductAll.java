package com.fathor.pondokkopi.Model.Orders;

public class ProductAll {

    private String id;
    private String gambar_mobile;
    private String nama_produk;
    private String nama_variasi;
    private Integer berat;
    private Integer harga;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getGambar_mobile() {
        return gambar_mobile;
    }

    public void setGambar_mobile(String gambar_mobile) {
        this.gambar_mobile = gambar_mobile;
    }

    public String getNama_produk() {
        return nama_produk;
    }

    public void setNama_produk(String nama_produk) {
        this.nama_produk = nama_produk;
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
}
