package com.fathor.pondokkopi.Model.Orders;

import java.util.List;

public class OrdersAll {
    private Integer id;
    private Integer id_user;
    private String tgl_pesan;
    private String tgl_terima;
    private Integer ongkir;
    private String status;
    private Integer total;
    private String bukti_bayar;
    private String keterangan;
    private String alasan;
    private String alamat_kirim;

    private List<OrdersDetail> order_detail;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_user() {
        return id_user;
    }

    public void setId_user(Integer id_user) {
        this.id_user = id_user;
    }

    public String getTgl_pesan() {
        return tgl_pesan;
    }

    public void setTgl_pesan(String tgl_pesan) {
        this.tgl_pesan = tgl_pesan;
    }

    public String getTgl_terima() {
        return tgl_terima;
    }

    public void setTgl_terima(String tgl_terima) {
        this.tgl_terima = tgl_terima;
    }

    public Integer getOngkir() {
        return ongkir;
    }

    public void setOngkir(Integer ongkir) {
        this.ongkir = ongkir;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public Integer getTotal() {
        return total;
    }

    public void setTotal(Integer total) {
        this.total = total;
    }

    public String getBukti_bayar() {
        return bukti_bayar;
    }

    public void setBukti_bayar(String bukti_bayar) {
        this.bukti_bayar = bukti_bayar;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public String getAlasan() {
        return alasan;
    }

    public void setAlasan(String alasan) {
        this.alasan = alasan;
    }

    public String getAlamat_kirim() {
        return alamat_kirim;
    }

    public void setAlamat_kirim(String alamat_kirim) {
        this.alamat_kirim = alamat_kirim;
    }

    public List<OrdersDetail> getOrder_detail() {
        return order_detail;
    }

    public void setOrder_detail(List<OrdersDetail> order_detail) {
        this.order_detail = order_detail;
    }
}
