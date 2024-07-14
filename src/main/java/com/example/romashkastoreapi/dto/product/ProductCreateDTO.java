package com.example.romashkastoreapi.dto.product;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Size;
import jakarta.validation.constraints.NotNull;

import java.math.BigDecimal;

public class ProductCreateDTO {
    @NotNull
    @Size(max = 255)
    private String name;

    @Size(max = 4096)
    private String description;

    @DecimalMin("0.0")
    private BigDecimal price = BigDecimal.ZERO;

    private boolean inStock = false;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public boolean isInStock() {
        return inStock;
    }

    public void setInStock(boolean inStock) {
        this.inStock = inStock;
    }
}
