package com.fathor.pondokkopi.Model.Cart;

public class CartData {

    private Integer id_variasi;
    private String gambar;
    private String namaProduk;
    private String namaVariasi;
    private Integer berat;
    private Integer jumlah;
    private Integer harga;
    private Integer subtotal;

    public CartData(Integer id_variasi, String gambar, String namaProduk, String namaVariasi, Integer berat, Integer jumlah, Integer harga, Integer subtotal) {
        this.id_variasi = id_variasi;
        this.gambar = gambar;
        this.namaProduk = namaProduk;
        this.namaVariasi = namaVariasi;
        this.berat = berat;
        this.jumlah = jumlah;
        this.harga = harga;
        this.subtotal = subtotal;
    }

    public Integer getId_variasi() {
        return id_variasi;
    }

    public void setId_variasi(Integer id_variasi) {
        this.id_variasi = id_variasi;
    }

    public String getGambar() {
        return gambar;
    }

    public void setGambar(String gambar) {
        this.gambar = gambar;
    }

    public String getNamaProduk() {
        return namaProduk;
    }

    public void setNamaProduk(String namaProduk) {
        this.namaProduk = namaProduk;
    }

    public String getNamaVariasi() {
        return namaVariasi;
    }

    public void setNamaVariasi(String namaVariasi) {
        this.namaVariasi = namaVariasi;
    }

    public Integer getBerat() {
        return berat;
    }

    public void setBerat(Integer berat) {
        this.berat = berat;
    }

    public Integer getJumlah() {
        return jumlah;
    }

    public void setJumlah(Integer jumlah) {
        this.jumlah = jumlah;
    }

    public Integer getHarga() {
        return harga;
    }

    public void setHarga(Integer harga) {
        this.harga = harga;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
