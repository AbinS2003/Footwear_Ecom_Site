package com.abin.Footwear.E_com.dto.admin_dto;

import org.springframework.web.multipart.MultipartFile;

public class AddBrandRequest {

    private String name;
    private MultipartFile image;

    public AddBrandRequest(String name, MultipartFile image) {
        this.name = name;
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public MultipartFile getImage() {
        return image;
    }

    public void setImage(MultipartFile image) {
        this.image = image;
    }
}
