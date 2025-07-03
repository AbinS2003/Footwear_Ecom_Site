package com.abin.Footwear.E_com.dto;


public class CartItemDto {
    private Long cartId;
    private Long productId;
    private String title;
    private String image;
    private double price;
    private int size; // selected size value
    private int quantity;

    // Constructors
    public CartItemDto() {}

    public CartItemDto(Long cartId, Long productId, String title, String image, double price, int size, int quantity) {
        this.cartId = cartId;
        this.productId = productId;
        this.title = title;
        this.image = image;
        this.price = price;
        this.size = size;
        this.quantity = quantity;
    }

    // Getters and Setters
    public Long getCartId() { return cartId; }
    public void setCartId(Long cartId) { this.cartId = cartId; }

    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }

    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }

    public String getImage() { return image; }
    public void setImage(String image) { this.image = image; }

    public double getPrice() { return price; }
    public void setPrice(double price) { this.price = price; }

    public int getSize() { return size; }
    public void setSize(int size) { this.size = size; }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
