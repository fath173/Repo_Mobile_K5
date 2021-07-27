package com.fathor.pondokkopi.Model.Auth;

public class AccountUpdateAddress {

    private String address;
    private Integer province;
    private Integer district;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getProvince() {
        return province;
    }

    public void setProvince(Integer province) {
        this.province = province;
    }

    public Integer getDistrict() {
        return district;
    }

    public void setDistrict(Integer district) {
        this.district = district;
    }
}
