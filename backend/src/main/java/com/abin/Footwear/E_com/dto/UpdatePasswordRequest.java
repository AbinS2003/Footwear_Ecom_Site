package com.abin.Footwear.E_com.dto;

public class UpdatePasswordRequest {

    private long userId;
    private String currentPassword;
    private String newPassword;

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public String getCurrentPassword() {
        return currentPassword;
    }

    public void setCurrentPassword(String currentPassword) {
        this.currentPassword = currentPassword;
    }

    public String getNewPassword() {
        return newPassword;
    }
    public void setNewPassowrd(String newPassowrd) {
        this.newPassword = newPassword;
    }
}
