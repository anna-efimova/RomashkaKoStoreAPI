package com.example.romashkastoreapi.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import jdk.jfr.Enabled;

import java.math.BigDecimal;

@Table(name = "sale")
@Entity
public class SaleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Size(max = 255)
    private String documentName;

    @ManyToOne
    @JoinColumn(name = "product_id", nullable = false)
    private ProductEntity product;

    @Min(1)
    private int quantity;

    @DecimalMin("0.0")
    private BigDecimal purchasePrice;


    public @NotNull @Size(max = 255) String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(@NotNull @Size(max = 255) String documentName) {
        this.documentName = documentName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
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
