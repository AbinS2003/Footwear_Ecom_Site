package com.abin.Footwear.E_com.dto;

public class UpdateUserProfileRequest {

    private Long userId;
    private String name;
    private String address;
    private String phone;

    public UpdateUserProfileRequest() {}

    public UpdateUserProfileRequest(Long userId, String name, String address, String phone) {
        this.userId = userId;
        this.name = name;
        this.address = address;
        this.phone = phone;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
}
