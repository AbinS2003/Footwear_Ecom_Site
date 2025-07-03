package com.abin.Footwear.E_com.dto;

public class CategoryImageDTO {

    private String category;
    private String image;

    public CategoryImageDTO(String category, String image) {
        this.category = category;
        this.image = image;
    }

    public String getCategory() {
        return category;
    }

    public String getImage() {
        return image;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
