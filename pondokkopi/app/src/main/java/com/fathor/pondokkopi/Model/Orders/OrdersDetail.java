package com.fathor.pondokkopi.Model.Orders;

public class OrdersDetail {
    private Integer id;
    private Integer id_pesanan;
    private Integer id_variasi;
    private Integer qty;
    private Integer subtotal;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getId_pesanan() {
        return id_pesanan;
    }

    public void setId_pesanan(Integer id_pesanan) {
        this.id_pesanan = id_pesanan;
    }

    public Integer getId_variasi() {
        return id_variasi;
    }

    public void setId_variasi(Integer id_variasi) {
        this.id_variasi = id_variasi;
    }

    public Integer getQty() {
        return qty;
    }

    public void setQty(Integer qty) {
        this.qty = qty;
    }

    public Integer getSubtotal() {
        return subtotal;
    }

    public void setSubtotal(Integer subtotal) {
        this.subtotal = subtotal;
    }
}
