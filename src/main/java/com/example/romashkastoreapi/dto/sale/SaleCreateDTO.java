package com.example.romashkastoreapi.dto.sale;

import com.example.romashkastoreapi.model.ProductEntity;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;

public class SaleCreateDTO {
    @NotBlank
    @Size(max = 255)
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Min(1)
    private int quantity;

    @DecimalMin("0.0")
    private BigDecimal purchasePrice;

    public @NotBlank @Size(max = 255) String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(@NotBlank @Size(max = 255) String documentName) {
        this.documentName = documentName;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public @DecimalMin("0.0") BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(@DecimalMin("0.0") BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }

    @Min(1)
    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(@Min(1) int quantity) {
        this.quantity = quantity;
    }
}
