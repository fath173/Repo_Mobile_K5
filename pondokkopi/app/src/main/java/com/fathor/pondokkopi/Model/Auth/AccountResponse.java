package com.fathor.pondokkopi.Model.Auth;

import java.util.List;

public class AccountResponse {
    private String message;
    private List<AccountData> user;
    private List<DataProvinsi> provinsi;
    private List<DataDistrik> distrik;

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<AccountData> getUser() {
        return user;
    }

    public void setUser(List<AccountData> user) {
        this.user = user;
    }

    public List<DataProvinsi> getProvinsi() {
        return provinsi;
    }

    public void setProvinsi(List<DataProvinsi> provinsi) {
        this.provinsi = provinsi;
    }

    public List<DataDistrik> getDistrik() {
        return distrik;
    }

    public void setDistrik(List<DataDistrik> distrik) {
        this.distrik = distrik;
    }
}
