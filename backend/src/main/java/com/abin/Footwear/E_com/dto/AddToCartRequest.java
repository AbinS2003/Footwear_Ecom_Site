package com.abin.Footwear.E_com.dto;

public class AddToCartRequest {

    private long userId;
    private long productId;
    private long sizeId;
    private int quantity;

    public AddToCartRequest() {
    }

    public AddToCartRequest(long userId, long productId, long sizeId, int quantity) {
        this.userId = userId;
        this.productId = productId;
        this.sizeId = sizeId;
        this.quantity = quantity;
    }

    public long getUserId() {
        return userId;
    }

    public void setUserId(long userId) {
        this.userId = userId;
    }

    public long getProductId() {
        return productId;
    }

    public void setProductId(long productId) {
        this.productId = productId;
    }

    public long getSizeId() {
        return sizeId;
    }

    public void setSizeId(long sizeId) {
        this.sizeId = sizeId;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
