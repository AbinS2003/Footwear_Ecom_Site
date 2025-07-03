package com.abin.Footwear.E_com.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import java.util.List;

@Entity
public class Size {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private int value;

    @ManyToMany(mappedBy = "sizes")
    @JsonIgnore
    private List<Product> products;

    public Size() {}

    public Size(int value) {
        this.value = value;
    }

    public Long getId() {
        return id;
    }

    public int getValue() {
        return value;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setValue(int value) {
        this.value = value;
    }

    public List<Product> getProducts() {
        return products;
    }

    public void setProducts(List<Product> products) {
        this.products = products;
    }
}
