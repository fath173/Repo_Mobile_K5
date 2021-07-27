package com.fathor.pondokkopi.Model.Cart;

import java.util.ArrayList;
import java.util.List;

public class Checkout {
    private Integer id_user;
    private Integer ongkir;
    private Integer total;
    private String keterangan;
    private String alamat_kirim;
    private List<CheckoutDetail> dataVariasi = new ArrayList<>();

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public Integer getOngkir() {
        return ongkir;
    }

    public void setOngkir(Integer ongkir) {
        this.ongkir = ongkir;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlamat_kirim() {
        return alamat_kirim;
    }

    public void setAlamat_kirim(String alamat_kirim) {
        this.alamat_kirim = alamat_kirim;
    }

    public List<CheckoutDetail> getDataVariasi() {
        return dataVariasi;
    }

    public void setDataVariasi(List<CheckoutDetail> dataVariasi) {
        this.dataVariasi = dataVariasi;
    }
}

