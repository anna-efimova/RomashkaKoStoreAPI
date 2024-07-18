package com.example.romashkastoreapi.dto.supply;

import com.example.romashkastoreapi.model.ProductEntity;
import com.example.romashkastoreapi.model.SaleEntity;

public class SupplyDTO {

    private Long id;
    private String documentName;
    private ProductEntity product;
    private int quantity;

    public SupplyDTO(SaleEntity saleEntity) {
        this.id = saleEntity.getId();
        this.documentName = saleEntity.getDocumentName();
        this.product = saleEntity.getProduct();
        this.quantity = saleEntity.getQuantity();
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
}
