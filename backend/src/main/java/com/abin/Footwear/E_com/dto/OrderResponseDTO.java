package com.abin.Footwear.E_com.dto;


import java.time.LocalDate;

public class OrderResponseDTO {
    private Long orderId;
    private String productTitle;
    private String userName;
    private int sizeValue;
    private String status;
    private LocalDate date;
    private double price;
    private String image;
    private Long productId;
    private int quantity;


    public OrderResponseDTO(Long orderId, String productTitle, String userName, int sizeValue, String status, LocalDate date, double price, String image, Long productId, int quantity) {
        this.orderId = orderId;
        this.productTitle = productTitle;
        this.userName = userName;
        this.sizeValue = sizeValue;
        this.status = status;
        this.date = date;
        this.price = price;
        this.image = image;
        this.productId = productId;
        this.quantity = quantity;
    }

    public Long getOrderId() {
        return orderId;
    }

    public void setOrderId(Long orderId) {
        this.orderId = orderId;
    }

    public String getProductTitle() {
        return productTitle;
    }

    public void setProductTitle(String productTitle) {
        this.productTitle = productTitle;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public int getSizeValue() {
        return sizeValue;
    }

    public void setSizeValue(int sizeValue) {
        this.sizeValue = sizeValue;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public Long getProductId() {
        return productId;
    }

    public void setProductId(Long productId) {
        this.productId = productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}