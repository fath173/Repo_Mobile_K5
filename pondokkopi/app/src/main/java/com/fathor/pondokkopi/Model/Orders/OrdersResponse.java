 package com.fathor.pondokkopi.Model.Orders;

import java.util.List;

public class OrdersResponse {
    private String message;
    private List<OrdersAll> allOrders;
    private List<OrdersRincian> rincianOrder;
    private Integer subTotal;
    private Integer testi;
    private String status;

    private List<ProductAll> allproduct;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getTesti() {
        return testi;
    }

    public void setTesti(Integer testi) {
        this.testi = testi;
    }

    public List<OrdersAll> getAllOrders() {
        return allOrders;
    }

    public void setAllOrders(List<OrdersAll> allOrders) {
        this.allOrders = allOrders;
    }

    public List<OrdersRincian> getRincianOrder() {
        return rincianOrder;
    }

    public void setRincianOrder(List<OrdersRincian> rincianOrder) {
        this.rincianOrder = rincianOrder;
    }

    public Integer getSubTotal() {
        return subTotal;
    }

    public void setSubTotal(Integer subTotal) {
        this.subTotal = subTotal;
    }

    public List<ProductAll> getAllproduct() {
        return allproduct;
    }

    public void setAllproduct(List<ProductAll> allproduct) {
        this.allproduct = allproduct;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
