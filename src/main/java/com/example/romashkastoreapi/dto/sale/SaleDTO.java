package com.example.romashkastoreapi.dto.sale;

import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SaleEntity;

import java.math.BigDecimal;

public class SaleDTO {

    private Long id;
    private String documentName;
    private ProductEntity product;
    private int quantity;
    private BigDecimal purchasePrice;

    public SaleDTO(SaleEntity saleEntity) {
        this.id = saleEntity.getId();
        this.documentName = saleEntity.getDocumentName();
        this.product = saleEntity.getProduct();
        this.quantity = saleEntity.getQuantity();
        this.purchasePrice = saleEntity.getPurchasePrice();
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDocumentName() {
        return documentName;
    }

    public void setDocumentName(String documentName) {
        this.documentName = documentName;
    }

    public ProductEntity getProduct() {
        return product;
    }

    public void setProduct(ProductEntity product) {
        this.product = product;
    }

    public int getQuantity() {
        return quantity;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public BigDecimal getPurchasePrice() {
        return purchasePrice;
    }

    public void setPurchasePrice(BigDecimal purchasePrice) {
        this.purchasePrice = purchasePrice;
    }
}
