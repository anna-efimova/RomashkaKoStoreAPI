package com.example.romashkastoreapi.dto.product;

import com.example.romashkastoreapi.model.ProductEntity;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;


public class ProductDTO {

    private Long id;
    private String name;
    private String description;
    private BigDecimal price;
    private boolean inStock;

    public ProductDTO() {
    }

    public ProductDTO(ProductEntity productEntity) {
        this.id = productEntity.getId();
        this.name = productEntity.getName();
        this.price = productEntity.getPrice();
        this.description = productEntity.getDescription();
        this.inStock = productEntity.isInStock();
    }

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
