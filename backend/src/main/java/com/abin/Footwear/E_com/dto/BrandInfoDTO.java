package com.abin.Footwear.E_com.dto;

public class BrandInfoDTO {

    public BrandInfoDTO(String name, String image) {
        this.name = name;
        this.image = image;
    }

    public String name;
    public String image;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
