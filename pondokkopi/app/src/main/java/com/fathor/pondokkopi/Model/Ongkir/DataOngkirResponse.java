package com.fathor.pondokkopi.Model.Ongkir;

import java.util.List;

public class DataOngkirResponse {
    private String message;
    private List<DataOngkir> dataongkir;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<DataOngkir> getDataongkir() {
        return dataongkir;
    }

    public void setDataongkir(List<DataOngkir> dataongkir) {
        this.dataongkir = dataongkir;
    }
}
